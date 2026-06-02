package com._2884omgpy.hasbasinoutputfaucetdepot.init;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTab
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HasBasinOutputFaucetDepot.MOD_ID);

    public static final RegistryObject<CreativeModeTab> HAS_BASIN_OUTPUT_FAUCET_DEPOT_CREATIVE_TAB = CREATIVE_MODE_TABS.register("has_basin_output_faucet_depot_creative_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlock.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get())).title(Component.translatable("itemGroup.has_basin_output_faucet_depot_creative_tab")).displayItems((CreativeModeTab.ItemDisplayParameters pParameters, CreativeModeTab.Output pOutput) ->
    {
        pOutput.accept(ModBlock.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get());
        pOutput.accept(ModBlock.HAS_HOPPER_OUTPUT_DEPOT.get());
    }).build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
