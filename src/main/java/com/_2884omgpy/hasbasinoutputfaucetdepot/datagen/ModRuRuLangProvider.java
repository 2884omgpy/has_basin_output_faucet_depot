package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModRuRuLangProvider extends LanguageProvider
{
    public ModRuRuLangProvider(PackOutput output)
    {
        super(output, HasBasinOutputFaucetDepot.MOD_ID,"ru_ru");
    }

    @Override
    protected void addTranslations()
    {
        add("block.hasbasinoutputfaucetdepot.has_basin_output_faucet_depot","Установка с выходом рабочего таза");
        add("block.hasbasinoutputfaucetdepot.has_hopper_output_depot","Установка с выходом воронки");
        add("itemGroup.has_basin_output_faucet_depot_creative_tab","Установка с выходом рабочего таза");
    }
}