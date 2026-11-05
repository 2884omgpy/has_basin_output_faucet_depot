package com._2884omgpy.hasbasinoutputfaucetdepot.client;

import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class BindBlockEntityAndRenderer
{
    public static void bind()
    {
        BlockEntityRenderers.register(ModBlockEntity.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get(), HasBasinOutputFaucetDepotRenderer::new);
    }
}
