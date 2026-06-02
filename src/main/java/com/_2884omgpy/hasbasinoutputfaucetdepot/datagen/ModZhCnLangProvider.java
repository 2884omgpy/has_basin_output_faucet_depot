package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider
{
    public ModZhCnLangProvider(PackOutput output)
    {
        super(output, HasBasinOutputFaucetDepot.MOD_ID,"zh_cn");
    }

    @Override
    protected void addTranslations()
    {
        add("block.hasbasinoutputfaucetdepot.has_basin_output_faucet_depot","带有工作盆输出口的置物台");
        add("block.hasbasinoutputfaucetdepot.has_hopper_output_depot","带有漏斗输出的置物台");
        add("itemGroup.has_basin_output_faucet_depot_creative_tab","带有工作盆输出口的置物台");
    }
}