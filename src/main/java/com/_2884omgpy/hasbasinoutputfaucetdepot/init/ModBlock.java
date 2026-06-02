package com._2884omgpy.hasbasinoutputfaucetdepot.init;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import com._2884omgpy.hasbasinoutputfaucetdepot.block.HasBasinOutputFaucetDepotBlock;
import com._2884omgpy.hasbasinoutputfaucetdepot.block.HasHopperOutputDepotBlock;
import com.simibubi.create.AllBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlock
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HasBasinOutputFaucetDepot.MOD_ID);

    public static final RegistryObject<Block> HAS_BASIN_OUTPUT_FAUCET_DEPOT = registerBlock("has_basin_output_faucet_depot", () -> new HasBasinOutputFaucetDepotBlock(BlockBehaviour.Properties.copy(AllBlocks.DEPOT.get())));
    public static final RegistryObject<Block> HAS_HOPPER_OUTPUT_DEPOT = registerBlock("has_hopper_output_depot", () -> new HasHopperOutputDepotBlock(BlockBehaviour.Properties.copy(AllBlocks.DEPOT.get())));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        return blocks;
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
