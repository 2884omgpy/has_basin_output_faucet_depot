package com._2884omgpy.hasbasinoutputfaucetdepot.mixin;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BlockEntityBehaviour.class, remap = false)
public interface BlockEntityBehaviourAccessor
{
    @Accessor("blockEntity")
    SmartBlockEntity getBlockEntity();
}
