package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider
{
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, HasBasinOutputFaucetDepot.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlock.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlock.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlock.HAS_HOPPER_OUTPUT_DEPOT.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlock.HAS_HOPPER_OUTPUT_DEPOT.get());
    }
}
