package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModKoKrLangProvider extends LanguageProvider
{
    public ModKoKrLangProvider(PackOutput output)
    {
        super(output, HasBasinOutputFaucetDepot.MOD_ID,"ko_kr");
    }

    @Override
    protected void addTranslations()
    {
        add("block.hasbasinoutputfaucetdepot.has_basin_output_faucet_depot","작업대야 출구가 있는 치물대");
        add("block.hasbasinoutputfaucetdepot.has_hopper_output_depot","깔때기 출력이 있는 치물대");
        add("itemGroup.has_basin_output_faucet_depot_creative_tab","작업대야 출구가 있는 치물대");
    }
}