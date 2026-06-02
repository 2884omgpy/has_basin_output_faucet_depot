package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLzhLangProvider extends LanguageProvider
{
    public ModLzhLangProvider(PackOutput output)
    {
        super(output, HasBasinOutputFaucetDepot.MOD_ID,"Lzh");
    }

    @Override
    protected void addTranslations()
    {
        add("block.hasbasinoutputfaucetdepot.has_basin_output_faucet_depot","載煉釜流口之货墩");
        add("block.hasbasinoutputfaucetdepot.has_hopper_output_depot","似漏斗出物之货墩");
        add("itemGroup.has_basin_output_faucet_depot_creative_tab","載煉釜流口之货墩");
    }
}