package com._2884omgpy.hasbasinoutputfaucetdepot;

import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlock;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModBlockEntity;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModCreativeModeTab;
import com._2884omgpy.hasbasinoutputfaucetdepot.init.ModItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HasBasinOutputFaucetDepot.MOD_ID)
public class HasBasinOutputFaucetDepot
{
    public static final String MOD_ID = "hasbasinoutputfaucetdepot";
    public static HasBasinOutputFaucetDepot INSTANCE;

    public HasBasinOutputFaucetDepot(FMLJavaModLoadingContext context)
    {
        INSTANCE = this;
        IEventBus modEventBus = context.getModEventBus();

        ModBlock.register(modEventBus);             //方块注册
        ModItem.register(modEventBus);              //物品注册
        ModBlockEntity.register(modEventBus);       //方块实体注册
        ModCreativeModeTab.register(modEventBus);   //创造模式物品栏注册

        MinecraftForge.EVENT_BUS.register(this);
    }
}
