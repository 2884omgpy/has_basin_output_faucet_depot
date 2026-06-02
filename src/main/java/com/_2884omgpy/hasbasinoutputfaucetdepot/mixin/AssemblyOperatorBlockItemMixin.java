package com._2884omgpy.hasbasinoutputfaucetdepot.mixin;

import com._2884omgpy.hasbasinoutputfaucetdepot.block.HasBasinOutputFaucetDepotBlock;
import com._2884omgpy.hasbasinoutputfaucetdepot.block.HasHopperOutputDepotBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AssemblyOperatorBlockItem.class, remap = false)
public class AssemblyOperatorBlockItemMixin
{
    @Inject(method = "operatesOn", at = @At("HEAD"), cancellable = true)
    private void customDepotOperatesOn(LevelReader world, BlockPos pos, BlockState placedOnState, CallbackInfoReturnable<Boolean> cir)
    {
        if (placedOnState.getBlock() instanceof HasBasinOutputFaucetDepotBlock || placedOnState.getBlock() instanceof HasHopperOutputDepotBlock)
        {
            cir.setReturnValue(true);
        }
    }
}
