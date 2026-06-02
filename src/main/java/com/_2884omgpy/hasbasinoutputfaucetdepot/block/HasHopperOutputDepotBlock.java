package com._2884omgpy.hasbasinoutputfaucetdepot.block;

import com._2884omgpy.hasbasinoutputfaucetdepot.blockentity.HasHopperOutputDepotBlockEntity;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlockEntity;
import com.simibubi.create.content.logistics.depot.DepotBlock;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class HasHopperOutputDepotBlock extends DepotBlock
{
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public HasHopperOutputDepotBlock(BlockBehaviour.Properties p_i48440_1_)
    {
        super(p_i48440_1_);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.DOWN));
    }

    //机械动力方块状态定义
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    //扳手右键调整输出方向操作传递到blockentity
    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context)
    {
        if (!context.getLevel().isClientSide)
        {
            withBlockEntityDo(context.getLevel(), context.getClickedPos(), blockEntity -> ((HasHopperOutputDepotBlockEntity) blockEntity).onWrenched(context.getClickedFace()));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockEntityType<? extends DepotBlockEntity> getBlockEntityType()
    {
        return ModBlockEntity.HAS_HOPPER_OUTPUT_DEPOT.get();
    }
}
