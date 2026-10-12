package net.smartgo.smartcraft.menu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import net.smartgo.smartcraft.SmartCraft;
public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SmartCraft.MOD_ID);
    public static final RegistryObject<MenuType<SmartizadorMenu>> SMARTIZADOR =
            MENUS.register("smartizador", () -> IForgeMenuType.create(SmartizadorMenu::new));
    public static void register(IEventBus bus) { MENUS.register(bus); }
}