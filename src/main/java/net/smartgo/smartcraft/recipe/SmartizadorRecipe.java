package net.smartgo.smartcraft.recipe;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
public class SmartizadorRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final ItemStack result;
    public SmartizadorRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result) {
        this.id = id; this.ingredient = ingredient; this.result = result.copy();
    }
    @Override public boolean matches(SimpleContainer input, Level level) { return ingredient.test(input.getItem(0)); }
    @Override public ItemStack assemble(SimpleContainer input, RegistryAccess access) { return result.copy(); }
    @Override public boolean canCraftInDimensions(int width, int height) { return width * height >= 1; }
    @Override public ItemStack getResultItem(RegistryAccess access) { return result; }
    @Override public ResourceLocation getId() { return id; }
    @Override public RecipeSerializer<?> getSerializer() { return ModRecipes.SMARTIZADOR_SERIALIZER.get(); }
    @Override public RecipeType<?> getType() { return ModRecipes.SMARTIZADOR.get(); }
    @Override public boolean isSpecial() { return true; }
    @Override public NonNullList<Ingredient> getIngredients() { return NonNullList.of(Ingredient.EMPTY, ingredient); }
    public static class Serializer implements RecipeSerializer<SmartizadorRecipe> {
        @Override public SmartizadorRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(json.get("ingredient"));
            ResourceLocation outputId = new ResourceLocation(GsonHelper.getAsString(json, "result"));
            if (!ForgeRegistries.ITEMS.containsKey(outputId)) { throw new JsonSyntaxException("Unknown Smartizador result: " + outputId); }
            ItemStack output = new ItemStack(ForgeRegistries.ITEMS.getValue(outputId));
            if (output.isEmpty()) { throw new JsonSyntaxException("Empty Smartizador result"); }
            return new SmartizadorRecipe(id, input, output);
        }
        @Override public SmartizadorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new SmartizadorRecipe(id, Ingredient.fromNetwork(buffer), buffer.readItem());
        }
        @Override public void toNetwork(FriendlyByteBuf buffer, SmartizadorRecipe recipe) {
            recipe.ingredient.toNetwork(buffer); buffer.writeItem(recipe.result);
        }
    }
}