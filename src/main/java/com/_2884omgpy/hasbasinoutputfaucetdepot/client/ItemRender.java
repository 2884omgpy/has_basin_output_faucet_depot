package com._2884omgpy.hasbasinoutputfaucetdepot.client;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlockEntity;
import com.simibubi.create.content.logistics.depot.DepotRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HasBasinOutputFaucetDepot.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemRender
{
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntity.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get(), DepotRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntity.HAS_HOPPER_OUTPUT_DEPOT.get(), DepotRenderer::new);
    }
}
