package com._2884omgpy.hasbasinoutputfaucetdepot.client;

import com._2884omgpy.hasbasinoutputfaucetdepot.block.HasBasinOutputFaucetDepotBlock;
import com._2884omgpy.hasbasinoutputfaucetdepot.blockentity.HasBasinOutputFaucetDepotBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.content.logistics.depot.DepotRenderer;
import com.simibubi.create.content.processing.basin.BasinBlock;
import dev.engine_room.flywheel.lib.transform.PoseTransformStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.data.IntAttached;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class HasBasinOutputFaucetDepotRenderer extends DepotRenderer
{
    public HasBasinOutputFaucetDepotRenderer(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    protected void renderSafe(DepotBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay)
    {
        //调用父类渲染原版置物台物品
        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);

        if (!(blockEntity instanceof HasBasinOutputFaucetDepotBlockEntity hasBasinOutputFaucetDepotBlockEntity))
        {
            return;
        }

        Direction facing = blockEntity.getBlockState().getValue(HasBasinOutputFaucetDepotBlock.FACING);

        if (facing == Direction.DOWN)
        {
            return;
        }

        Vec3 directionVec = Vec3.atLowerCornerOf(facing.getNormal());
        Vec3 outVec = VecHelper.getCenterOf(BlockPos.ZERO).add(directionVec.scale(0.55).subtract(0, 0.5, 0));

        boolean outToBasin = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos().relative(facing)).getBlock() instanceof BasinBlock;

        //完全复制BasinRenderer
        for (IntAttached<ItemStack> entry : hasBasinOutputFaucetDepotBlockEntity.visualizedOutputItems)
        {
            //计算动画进度
            float progress = 1.0F - ((float) entry.getFirst() - partialTicks) / 10.0F;

            //如果输出目标不是工作盆，只渲染动画的前35%
            if (!outToBasin && progress > 0.35F)
            {
                continue;
            }

            ms.pushPose();

            ((PoseTransformStack)((PoseTransformStack)((PoseTransformStack)TransformStack.of(ms).translate(outVec)).translate(0.0, Math.max(-0.55, -(progress * progress * 2.0)), 0.0)).translate(directionVec.scale(progress * 0.5))).rotateYDegrees(AngleHelper.horizontalAngle(facing)).rotateXDegrees(progress * 180.0F);

            //渲染物品
            Minecraft.getInstance().getItemRenderer().renderStatic(entry.getValue(), ItemDisplayContext.GROUND, light, overlay, ms, buffer, blockEntity.getLevel(), 0);
            ms.popPose();
        }
    }
}