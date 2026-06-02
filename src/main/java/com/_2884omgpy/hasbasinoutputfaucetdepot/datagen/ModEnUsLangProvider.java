package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider
{
    public ModEnUsLangProvider(PackOutput output)
    {
        super(output, HasBasinOutputFaucetDepot.MOD_ID,"en_us");
    }

    @Override
    protected void addTranslations()
    {
        add("block.hasbasinoutputfaucetdepot.has_basin_output_faucet_depot","Has Basin Output Faucet Depot");
        add("block.hasbasinoutputfaucetdepot.has_hopper_output_depot","Has Hopper Output Depot");
        add("itemGroup.has_basin_output_faucet_depot_creative_tab","Has Basin Output Faucet Depot");
    }
}