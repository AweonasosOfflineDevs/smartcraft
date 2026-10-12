package net.smartgo.smartcraft.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smartgo.smartcraft.SmartCraft;
import net.smartgo.smartcraft.item.custom.SmartcoreItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SmartCraft.MOD_ID);

    public static final RegistryObject<Item> SMARTGORITA_BRUTO =
            ITEMS.register("smartgorita_bruto", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SMARTGORITA_LINGOTE =
            ITEMS.register("smartgorita_lingote", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SMARTCORE =
            ITEMS.register("smartcore", SmartcoreItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
