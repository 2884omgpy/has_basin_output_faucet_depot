package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlock;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTablesProvider extends BlockLootSubProvider
{
    public ModBlockLootTablesProvider()
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate()
    {
        dropSelf(ModBlock.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get());
        dropSelf(ModBlock.HAS_HOPPER_OUTPUT_DEPOT.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlock.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;   //返回迭代器
    }
}