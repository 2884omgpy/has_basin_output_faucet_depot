package com._2884omgpy.hasbasinoutputfaucetdepot.mixin;

import com._2884omgpy.hasbasinoutputfaucetdepot.blockentity.HasBasinOutputFaucetDepotBlockEntity;
import com._2884omgpy.hasbasinoutputfaucetdepot.blockentity.HasHopperOutputDepotBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(value = DepotBehaviour.class, remap = false)
public class DepotBehaviourMixin
{
    @Inject(method = "applyToAllItems", at = @At("HEAD"), cancellable = true)
    private void reset_ApplyToAllItems(float maxDistanceFromCentre, Function<TransportedItemStack, TransportedItemStackHandlerBehaviour.TransportedResult> processFunction, CallbackInfo ci)
    {
        DepotBehaviourAccessor depotBehaviourAccessor = (DepotBehaviourAccessor) this;
        BlockEntityBehaviourAccessor blockEntityBehaviourAccessor = (BlockEntityBehaviourAccessor) this;

        BlockEntity blockEntity = blockEntityBehaviourAccessor.getBlockEntity();
        if (blockEntity instanceof HasBasinOutputFaucetDepotBlockEntity || blockEntity instanceof HasHopperOutputDepotBlockEntity)
        {
            if ((blockEntity instanceof HasBasinOutputFaucetDepotBlockEntity && ((HasBasinOutputFaucetDepotBlockEntity) blockEntity).getHeldItem() != null) || (blockEntity instanceof HasHopperOutputDepotBlockEntity && ((HasHopperOutputDepotBlockEntity) blockEntity).getHeldItem() != null))
            {
                if (depotBehaviourAccessor.getHeldItem() == null)
                {
                    ci.cancel();
                    return;
                }

                if (!(0.5F - depotBehaviourAccessor.getHeldItem().beltPosition > maxDistanceFromCentre))
                {
                    TransportedItemStack transportedItemStack = depotBehaviourAccessor.getHeldItem();
                    ItemStack stackBefore = transportedItemStack.stack.copy();
                    TransportedItemStackHandlerBehaviour.TransportedResult result = (TransportedItemStackHandlerBehaviour.TransportedResult)processFunction.apply(transportedItemStack);
                    if (result != null && !result.didntChangeFrom(stackBefore))
                    {
                        //处理heldItem
                        if (result.hasHeldOutput())
                        {
                            depotBehaviourAccessor.setHeldItem(result.getHeldOutput());
                        }
                            else
                            {
                                depotBehaviourAccessor.setHeldItem(null);
                            }

                        //处理outputBuffer
                        for(TransportedItemStack added : result.getOutputs())
                        {
                            ItemStack remainder = ItemHandlerHelper.insertItemStacked(depotBehaviourAccessor.getProcessingOutputBuffer(), added.stack, false);
                            Vec3 vec = VecHelper.getCenterOf(blockEntityBehaviourAccessor.getBlockEntity().getBlockPos());
                            Containers.dropItemStack(blockEntityBehaviourAccessor.getBlockEntity().getLevel(), vec.x, vec.y + (double)0.5F, vec.z, remainder);
                        }

                        blockEntityBehaviourAccessor.getBlockEntity().notifyUpdate();
                    }
                }
            }
            ci.cancel();
        }
    }
}
