package net.smartgo.smartcraft.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.smartgo.smartcraft.SmartCraft;
import net.smartgo.smartcraft.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SmartCraft.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SMARTCRAFT_TAB =
            CREATIVE_MODE_TABS.register("smartcraft_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.SMARTGO.get()))
                    .title(Component.translatable("creativetab.smartcraft_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.SMARTGO.get());
                        output.accept(ModBlocks.DARKGO.get());
                        output.accept(ModBlocks.GLINK_SMART.get());
                        output.accept(ModBlocks.MR_SMART.get());
                        output.accept(ModBlocks.SMART.get());
                        output.accept(ModBlocks.SMART_18WN.get());
                        output.accept(ModBlocks.SMART_2807.get());
                        output.accept(ModBlocks.SMART_CASTILLO.get());
                        output.accept(ModBlocks.SMART_CD.get());
                        output.accept(ModBlocks.SMART_CPSITO.get());
                        output.accept(ModBlocks.SMART_DEL_OXXO.get());
                        output.accept(ModBlocks.SMART_ELPEPE.get());
                        output.accept(ModBlocks.SMART_FALOPA.get());
                        output.accept(ModBlocks.SMART_FIXION.get());
                        output.accept(ModBlocks.SMART_FLOYD.get());
                        output.accept(ModBlocks.SMART_KEK.get());
                        output.accept(ModBlocks.SMART_MASTER_7PRO.get());
                        output.accept(ModBlocks.SMART_MIZUNO.get());
                        output.accept(ModBlocks.SMART_MOMARDOPAPU.get());
                        output.accept(ModBlocks.SMART_NAVIDAD.get());
                        output.accept(ModBlocks.SMART_NONSITO.get());
                        output.accept(ModBlocks.SMART_SANS.get());
                        output.accept(ModBlocks.SMART_SUPER_IDOL.get());
                        output.accept(ModBlocks.SMART_ZAD.get());
                        output.accept(ModBlocks.SMART_ZAMN.get());
                        output.accept(ModBlocks.SMARTDEL_CALAMAR.get());
                        output.accept(ModBlocks.SMARTGHOST.get());
                        output.accept(ModBlocks.SMARTIZADOR.get());
                        output.accept(ModBlocks.SMARTLINKED.get());
                        output.accept(ModBlocks.SMARTROX.get());
                        output.accept(ModBlocks.SMARTSMILE.get());
                        output.accept(ModBlocks.SMARTTLER.get());
                        output.accept(ModBlocks.SMARTY.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}