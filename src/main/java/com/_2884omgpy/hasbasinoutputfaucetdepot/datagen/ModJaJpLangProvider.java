package com._2884omgpy.hasbasinoutputfaucetdepot.datagen;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModJaJpLangProvider extends LanguageProvider
{
    public ModJaJpLangProvider(PackOutput output)
    {
        super(output, HasBasinOutputFaucetDepot.MOD_ID,"ja_jp");
    }

    @Override
    protected void addTranslations()
    {
        add("block.hasbasinoutputfaucetdepot.has_basin_output_faucet_depot","鉢の搬出用の蛇口があるデポ");
        add("block.hasbasinoutputfaucetdepot.has_hopper_output_depot","ホッパーのように生成物が出力できるデポ");
        add("itemGroup.has_basin_output_faucet_depot_creative_tab","鉢の搬出用の蛇口があるデポ");
    }
}