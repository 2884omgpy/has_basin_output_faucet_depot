package com._2884omgpy.hasbasinoutputfaucetdepot.client;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HasBasinOutputFaucetDepot.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BindBlockEntityAndRenderer
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            BlockEntityRenderers.register(ModBlockEntity.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get(), HasBasinOutputFaucetDepotRenderer::new);
        });
    }
}
