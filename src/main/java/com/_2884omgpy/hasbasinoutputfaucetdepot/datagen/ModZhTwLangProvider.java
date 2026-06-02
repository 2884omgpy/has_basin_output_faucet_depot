package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhTwLangProvider extends LanguageProvider
{
    public ModZhTwLangProvider(PackOutput output)
    {
        super(output, HasBasinOutputFaucetDepot.MOD_ID,"zh_tw");
    }

    @Override
    protected void addTranslations()
    {
        add("block.hasbasinoutputfaucetdepot.has_basin_output_faucet_depot","帶有工作盆輸出龍頭的置物台");
        add("block.hasbasinoutputfaucetdepot.has_hopper_output_depot","帶有漏斗輸出的置物台");
        add("itemGroup.has_basin_output_faucet_depot_creative_tab","帶有工作盆輸出龍頭的置物台");
    }
}