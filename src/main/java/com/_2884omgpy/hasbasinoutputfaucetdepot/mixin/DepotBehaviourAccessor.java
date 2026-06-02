package com._2884omgpy.hasbasinoutputfaucetdepot.mixin;

import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = DepotBehaviour.class, remap = false)
public interface DepotBehaviourAccessor
{
    @Accessor("heldItem")
    TransportedItemStack getHeldItem();

    @Accessor("heldItem")
    void setHeldItem(TransportedItemStack heldItem);

    @Accessor("processingOutputBuffer")
    ItemStackHandler getProcessingOutputBuffer();
}
