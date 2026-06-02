package com._2884omgpy.hasbasinoutputfaucetdepot.init;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItem
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HasBasinOutputFaucetDepot.MOD_ID);

    public static final RegistryObject<Item> HAS_BASIN_OUTPUT_FAUCET_DEPOT =  ITEMS.register("has_basin_output_faucet_depot", () -> new BlockItem(ModBlock.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get(), new Item.Properties()));
    public static final RegistryObject<Item> HAS_HOPPER_OUTPUT_DEPOT =  ITEMS.register("has_hopper_output_depot", () -> new BlockItem(ModBlock.HAS_HOPPER_OUTPUT_DEPOT.get(), new Item.Properties()));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
