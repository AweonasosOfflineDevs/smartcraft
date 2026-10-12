package net.smartgo.smartcraft.recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smartgo.smartcraft.SmartCraft;
public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SmartCraft.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, SmartCraft.MOD_ID);
    public static final RegistryObject<RecipeType<SmartizadorRecipe>> SMARTIZADOR = TYPES.register("smartizador", () -> new RecipeType<>() {
        @Override public String toString() { return "smartcraft:smartizador"; }
    });
    public static final RegistryObject<RecipeSerializer<SmartizadorRecipe>> SMARTIZADOR_SERIALIZER = SERIALIZERS.register("smartizador", SmartizadorRecipe.Serializer::new);
    public static void register(IEventBus bus) { TYPES.register(bus); SERIALIZERS.register(bus); }
}