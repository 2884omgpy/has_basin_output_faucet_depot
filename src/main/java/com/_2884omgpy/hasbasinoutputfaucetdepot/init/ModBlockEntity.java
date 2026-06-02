package com._2884omgpy.hasbasinoutputfaucetdepot.init;

import com._2884omgpy.hasbasinoutputfaucetdepot.HasBasinOutputFaucetDepot;
import com._2884omgpy.hasbasinoutputfaucetdepot.blockentity.HasBasinOutputFaucetDepotBlockEntity;
import com._2884omgpy.hasbasinoutputfaucetdepot.blockentity.HasHopperOutputDepotBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntity
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, HasBasinOutputFaucetDepot.MOD_ID);

    public static final RegistryObject<BlockEntityType<HasBasinOutputFaucetDepotBlockEntity>> HAS_BASIN_OUTPUT_FAUCET_DEPOT = BLOCK_ENTITIES.register("has_basin_output_faucet_depot", () -> BlockEntityType.Builder.of(HasBasinOutputFaucetDepotBlockEntity::new, ModBlock.HAS_BASIN_OUTPUT_FAUCET_DEPOT.get()).build(null));
    public static final RegistryObject<BlockEntityType<HasHopperOutputDepotBlockEntity>> HAS_HOPPER_OUTPUT_DEPOT = BLOCK_ENTITIES.register("has_hopper_output_depot", () -> BlockEntityType.Builder.of(HasHopperOutputDepotBlockEntity::new, ModBlock.HAS_HOPPER_OUTPUT_DEPOT.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
