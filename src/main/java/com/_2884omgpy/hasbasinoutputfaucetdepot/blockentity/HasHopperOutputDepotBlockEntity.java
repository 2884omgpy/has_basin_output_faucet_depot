package com._2884omgpy.hasbasinoutputfaucetdepot.blockentity;

import com._2884omgpy.hasbasinoutputfaucetdepot.block.HasHopperOutputDepotBlock;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlockEntity;
import com._2884omgpy.hasbasinoutputfaucetdepot.mixin.DepotBehaviourAccessor;
import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class HasHopperOutputDepotBlockEntity extends DepotBlockEntity
{
    private final List<ItemStack> spoutputBuffer = new ArrayList<>();

    public HasHopperOutputDepotBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public HasHopperOutputDepotBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntity.HAS_HOPPER_OUTPUT_DEPOT.get(), pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours)
    {
        super.addBehaviours(behaviours);
    }

    @Override
    public void tick()
    {
        super.tick();

        if (level == null || level.isClientSide)
        {
            return;
        }

        DepotBehaviour depotBehaviour = getBehaviour(DepotBehaviour.TYPE);
        if (depotBehaviour == null)
        {
            return;
        }

        //获取目标容器
        IItemHandler targetInventory = getTargetInventory();
        if (targetInventory == null)
        {
            return;
        }

        //获取输出
        ItemStackHandler outputBuffer = ((DepotBehaviourAccessor) depotBehaviour).getProcessingOutputBuffer();
        if (outputBuffer == null)
        {
            return;
        }

        boolean changed = false;
        for (int i = 0; i < outputBuffer.getSlots(); i++)
        {
            ItemStack stack = outputBuffer.getStackInSlot(i);
            if (stack.isEmpty())
            {
                continue;
            }

            //直接尝试插入
            int remainderCount = getIfOutputRemainItemCount(stack, targetInventory);
            if (remainderCount >= 0)
            {   //插入成功，从outputBuffer取出相应数量物品
                outputBuffer.extractItem(i, stack.getCount() - remainderCount, false);
                changed = true;
            }
        }

        if (changed)
        {
            setChanged();
            sendData();
        }
    }

    //获取输出目标方块物品栏
    private IItemHandler getTargetInventory()
    {
        Direction facing = getBlockState().getValue(HasHopperOutputDepotBlock.FACING);
        BlockPos targetPosition = worldPosition.relative(facing);
        BlockEntity targetBlockEntity = level.getBlockEntity(targetPosition);
        if (targetBlockEntity != null)
        {
            return targetBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, facing.getOpposite()).orElse(null);
        }
            else
            {
                return null;
            }
    }

    //检测是否可以输出
    private int getIfOutputRemainItemCount(ItemStack outputBuffer, IItemHandler targetInventory)
    {
        if (!outputBuffer.isEmpty())
        {
            ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, outputBuffer, false);
            if (remainder.getCount() < outputBuffer.getCount())
            {
                return remainder.getCount();
            }
        }
        return -1;
    }

    //使用扳手调整方向
    public void onWrenched(Direction direction)
    {
        BlockState state = getBlockState();
        Direction currentFacing = state.getValue(HasHopperOutputDepotBlock.FACING);
        if (currentFacing == direction)
        {
            switch (currentFacing)
            {
                case UP -> level.setBlockAndUpdate(worldPosition, state.setValue(HasHopperOutputDepotBlock.FACING, Direction.DOWN));
                case DOWN -> level.setBlockAndUpdate(worldPosition, state.setValue(HasHopperOutputDepotBlock.FACING, Direction.UP));
                case EAST -> level.setBlockAndUpdate(worldPosition, state.setValue(HasHopperOutputDepotBlock.FACING, Direction.WEST));
                case WEST -> level.setBlockAndUpdate(worldPosition, state.setValue(HasHopperOutputDepotBlock.FACING, Direction.EAST));
                case NORTH -> level.setBlockAndUpdate(worldPosition, state.setValue(HasHopperOutputDepotBlock.FACING, Direction.SOUTH));
                case SOUTH -> level.setBlockAndUpdate(worldPosition, state.setValue(HasHopperOutputDepotBlock.FACING, Direction.NORTH));
            }
        }
            else
            {
                level.setBlockAndUpdate(worldPosition, state.setValue(HasHopperOutputDepotBlock.FACING, direction));
            }
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket)
    {
        super.write(tag, clientPacket);
        tag.put("Overflow", NBTHelper.writeItemList(spoutputBuffer));
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket)
    {
        super.read(tag, clientPacket);
        spoutputBuffer.clear();
        spoutputBuffer.addAll(NBTHelper.readItemList(tag.getList("Overflow", 10)));
    }
}
