package com._2884omgpy.hasbasinoutputfaucetdepot.blockentity;

import com._2884omgpy.hasbasinoutputfaucetdepot.block.HasBasinOutputFaucetDepotBlock;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlockEntity;
import com._2884omgpy.hasbasinoutputfaucetdepot.mixin.DepotBehaviourAccessor;
import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.inventory.InvManipulationBehaviour;
import net.createmod.catnip.data.IntAttached;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HasBasinOutputFaucetDepotBlockEntity extends DepotBlockEntity
{
    private Direction preferredSpoutput = null;
    private final List<Direction> disabledSpoutputs = new ArrayList<>();

    //物品输出缓冲区（FACING != DOWN时置物台输出暂存于缓冲区等待输出）
    private final List<ItemStack> spoutputBuffer = new ArrayList<>();

    //客户端动画
    public final List<IntAttached<ItemStack>> visualizedOutputItems = Collections.synchronizedList(new ArrayList<>());

    public HasBasinOutputFaucetDepotBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public HasBasinOutputFaucetDepotBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntity.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get(), pos, state);
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

        if (level == null)
        {
            return;
        }

        if (!level.isClientSide)
        {
            BlockState state = getBlockState();
            Direction facing = state.getValue(HasBasinOutputFaucetDepotBlock.FACING);

            //FACING != DOWN时自动输出产物
            if (facing != Direction.DOWN)
            {
                DepotBehaviour depotBehaviour = getBehaviour(DepotBehaviour.TYPE);
                if (depotBehaviour != null)
                {
                    //将机器产生的成品转移到自己的缓冲区
                    ItemStackHandler outputBuffer = ((DepotBehaviourAccessor) depotBehaviour).getProcessingOutputBuffer();

                    if (outputBuffer != null)
                    {
                        for (int i = 0; i < outputBuffer.getSlots(); i++)
                        {
                            ItemStack extracted = outputBuffer.extractItem(i, 64, false);
                            if (!extracted.isEmpty())
                            {
                                spoutputBuffer.add(extracted);
                                setChanged();
                            }
                        }
                    }
                }
            }
            //尝试将缓冲区中的成品推送到斜下方
            tryClearingSpoutputOverflow();
        }
            else
            {
                visualizedOutputItems.forEach(IntAttached::decrement);
                visualizedOutputItems.removeIf(IntAttached::isOrBelowZero);
            }
    }

    @Override
    public void lazyTick()
    {
        if (level != null && !level.isClientSide)
        {
            //检查更新输出方向
            updateSpoutput();
        }
    }

    //检查更新输出方向
    private void updateSpoutput()
    {
        BlockState state = getBlockState();
        Direction currentFacing = state.getValue(HasBasinOutputFaucetDepotBlock.FACING);
        Direction newFacing = Direction.DOWN;

        //遍历水平方向，寻找找第一个可用的方向
        for (Direction test : Iterate.horizontalDirections)
        {
            if (HasBasinOutputFaucetDepotBlock.canOutputTo(level, worldPosition, test) && !disabledSpoutputs.contains(test))
            {
                newFacing = test;
                break;
            }
        }

        //如果玩家设置了首选方向且该方向可用，则使用此方向
        if (preferredSpoutput != null && HasBasinOutputFaucetDepotBlock.canOutputTo(level, worldPosition, preferredSpoutput) && preferredSpoutput != Direction.UP)
        {
            newFacing = preferredSpoutput;
        }

        if (newFacing != currentFacing)
        {
            //方向改变后，下一Tick会自动尝试推送
            level.setBlockAndUpdate(worldPosition, state.setValue(HasBasinOutputFaucetDepotBlock.FACING, newFacing));
        }
    }

    //尝试将缓冲区物品推送出去
    private void tryClearingSpoutputOverflow()
    {
        if (!spoutputBuffer.isEmpty())
        {
            BlockState state = getBlockState();
            Direction facing = state.getValue(HasBasinOutputFaucetDepotBlock.FACING);

            if (facing == Direction.DOWN)
            {
                //方向为DOWN时，掉落所有缓冲物品到世界
                for (ItemStack itemStack : spoutputBuffer)
                {
                    Block.popResource(level, worldPosition, itemStack);
                }
                spoutputBuffer.clear();
                sendData();
                return;
            }

            //确定斜下方目标方块
            BlockPos targetPosition = worldPosition.below().relative(facing);
            BlockEntity targetBlockEntity = level.getBlockEntity(targetPosition);
            IItemHandler targetInventory = null;

            if (targetBlockEntity != null)
            {
                targetInventory = targetBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, facing.getOpposite()).orElse(null);
                //如果目标没有直接提供ITEM_HANDLER则尝试通过InvManipulationBehaviour获取
                if (targetInventory == null)
                {
                    InvManipulationBehaviour inventoryManipulationBehavior = BlockEntityBehaviour.get(level, targetPosition, InvManipulationBehaviour.TYPE);
                    if (inventoryManipulationBehavior != null)
                    {
                        targetInventory = inventoryManipulationBehavior.getInventory();
                    }
                }
            }

            //确定可以输出
            if (targetInventory != null)
            {
                boolean changed = false;
                Iterator<ItemStack> iterator = spoutputBuffer.iterator();
                while (iterator.hasNext())
                {
                    ItemStack stack = iterator.next();
                    //模拟插入，确认数量上可行
                    ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, stack, true);
                    if (remainder.getCount() < stack.getCount())
                    {
                        //实际插入
                        remainder = ItemHandlerHelper.insertItemStacked(targetInventory, stack.copy(), false);
                        if (remainder.isEmpty())
                        {
                            iterator.remove();
                            //客户端可视化
                            if (visualizedOutputItems.isEmpty())
                            {
                                visualizedOutputItems.add(IntAttached.with(10, stack));
                            }
                            changed = true;
                        }
                            else
                            {
                                stack.setCount(remainder.getCount());
                            }
                    }
                }

                if (changed)
                {
                    sendData();
                    setChanged();
                }
            }
        }
    }

    //使用扳手调整方向
    public void onWrenched(Direction direction)
    {
        BlockState state = getBlockState();
        Direction currentFacing = state.getValue(HasBasinOutputFaucetDepotBlock.FACING);
        disabledSpoutputs.remove(direction);
        if (currentFacing == direction)
        {
            if (preferredSpoutput == direction)
            {
                preferredSpoutput = null;
            }
            disabledSpoutputs.add(direction);
        }
            else
            {
                preferredSpoutput = direction;
            }
        updateSpoutput();
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket)
    {
        super.write(tag, clientPacket);
        if (preferredSpoutput != null)
        {
            NBTHelper.writeEnum(tag, "PreferredSpoutput", preferredSpoutput);
        }
        ListTag disabledList = new ListTag();
        disabledSpoutputs.forEach(direction -> disabledList.add(StringTag.valueOf(direction.name())));
        tag.put("DisabledSpoutput", disabledList);
        tag.put("Overflow", NBTHelper.writeItemList(spoutputBuffer));
        if (clientPacket)
        {
            tag.put("VisualizedItems", NBTHelper.writeCompoundList(visualizedOutputItems, itemStackIntAttached -> itemStackIntAttached.getValue().serializeNBT()));
            this.visualizedOutputItems.clear();
        }
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket)
    {
        super.read(tag, clientPacket);
        preferredSpoutput = tag.contains("PreferredSpoutput") ? NBTHelper.readEnum(tag, "PreferredSpoutput", Direction.class) : null;
        disabledSpoutputs.clear();
        tag.getList("DisabledSpoutput", 8).forEach(isDisabled -> disabledSpoutputs.add(Direction.valueOf(((StringTag) isDisabled).getAsString())));
        spoutputBuffer.clear();
        spoutputBuffer.addAll(NBTHelper.readItemList(tag.getList("Overflow", 10)));
        if (clientPacket)
        {
            visualizedOutputItems.clear();
            NBTHelper.iterateCompoundList(tag.getList("VisualizedItems", 10), compoundTag -> visualizedOutputItems.add(IntAttached.with(10, ItemStack.of(compoundTag))));
        }
    }
}
