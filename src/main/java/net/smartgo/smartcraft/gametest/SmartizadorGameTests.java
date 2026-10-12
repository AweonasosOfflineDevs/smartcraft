package net.smartgo.smartcraft.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.RenderShape;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.smartgo.smartcraft.SmartCraft;
import net.smartgo.smartcraft.block.ModBlocks;
import net.smartgo.smartcraft.block.entity.SmartizadorBlockEntity;
import net.smartgo.smartcraft.item.ModItems;
import net.smartgo.smartcraft.item.custom.SmartcoreItem;
import net.smartgo.smartcraft.menu.SmartizadorMenu;
import net.smartgo.smartcraft.recipe.SmartizadorRecipe;
import java.util.ArrayList;
import java.util.List;

@GameTestHolder(SmartCraft.MOD_ID)
@PrefixGameTestTemplate(false)
public class SmartizadorGameTests {
    private static SmartizadorBlockEntity machine(GameTestHelper helper) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SMARTIZADOR.get());
        return (SmartizadorBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(pos));
    }

    @GameTest(template = "test/empty", timeoutTicks = 40)
    public static void chargeAndPersistence(GameTestHelper helper) {
        SmartizadorBlockEntity machine = machine(helper);
        ItemStack core = new ItemStack(ModItems.SMARTCORE.get());
        machine.inventory.setStackInSlot(1, core);
        machine.refill();
        helper.assertTrue(machine.data.get(0) == 20 && SmartcoreItem.remainingUses(core) == 60, "Initial charge must transfer exactly 20 uses");
        machine.refill();
        helper.assertTrue(SmartcoreItem.remainingUses(core) == 60, "A full bar must not consume charge");
        machine.data.set(0, 3);
        core.setDamageValue(70);
        machine.refill();
        helper.assertTrue(machine.data.get(0) == 13 && machine.inventory.getStackInSlot(1).isEmpty(), "3 stored + 10 core must produce 13 and remove core");
        machine.inventory.setStackInSlot(0, new ItemStack(ModBlocks.SMART.get(), 4));
        SmartizadorBlockEntity restored = new SmartizadorBlockEntity(machine.getBlockPos(), machine.getBlockState());
        restored.load(machine.saveWithoutMetadata());
        helper.assertTrue(restored.data.get(0) == 13 && restored.inventory.getStackInSlot(0).getCount() == 4, "Inventory and charge must survive saving");
        helper.assertTrue(machine.getBlockState().getRenderShape() == RenderShape.MODEL, "Smartizador must remain visible");
        helper.succeed();
    }

    @GameTest(template = "test/empty", timeoutTicks = 40)
    public static void detachedCore(GameTestHelper helper) {
        SmartizadorBlockEntity machine = machine(helper);
        machine.inventory.setStackInSlot(1, new ItemStack(ModItems.SMARTCORE.get()));
        machine.inventory.setStackInSlot(0, new ItemStack(ModBlocks.SMART.get(), 21));
        machine.refill();
        ItemStack removed = machine.inventory.extractItem(1, 1, false);
        helper.assertTrue(SmartcoreItem.remainingUses(removed) == 60, "Removed core must keep remaining uses");
        for (int i = 0; i < 20; i++) { helper.assertTrue(machine.consume(), "Stored charge should allow 20 uses"); }
        helper.assertTrue(!machine.consume() && machine.inventory.getStackInSlot(0).getCount() == 1, "Empty machine must not consume a head");
        helper.succeed();
    }

    @GameTest(template = "test/empty", timeoutTicks = 40)
    public static void outputAndShiftClick(GameTestHelper helper) {
        var manager = helper.getLevel().getRecipeManager();
        List<Recipe<?>> original = new ArrayList<>(manager.getRecipes());
        List<Recipe<?>> temporary = new ArrayList<>(original);
        ResourceLocation id = new ResourceLocation(SmartCraft.MOD_ID, "test_conversion");
        temporary.add(new SmartizadorRecipe(id, Ingredient.of(ModBlocks.SMART.get()),
                new ItemStack(ModBlocks.DARKGO.get())));
        manager.replaceRecipes(temporary);
        try {
            SmartizadorBlockEntity machine = machine(helper);
            machine.inventory.setStackInSlot(1, new ItemStack(ModItems.SMARTCORE.get()));
            machine.inventory.setStackInSlot(0, new ItemStack(ModBlocks.SMART.get(), 64));
            machine.refill();
            Player player = helper.makeMockPlayer();
            SmartizadorMenu menu = new SmartizadorMenu(1, player.getInventory(), machine);
            int selected = -1;
            for (int i = 0; i < menu.recipes().size(); i++) { if (menu.recipes().get(i).getId().equals(id)) { selected = i; } }
            helper.assertTrue(menu.clickMenuButton(player, selected), "Recipe must be selectable");
            helper.assertTrue(SmartcoreItem.remainingUses(machine.inventory.getStackInSlot(1)) == 60, "Selecting a recipe must not spend charge");
            for (int i = 0; i < 64; i++) { helper.assertTrue(!menu.quickMoveStack(player, 2).isEmpty(), "Shift-click must craft"); }
            machine.inventory.setStackInSlot(0, new ItemStack(ModBlocks.SMART.get(), 17));
            menu.clickMenuButton(player, selected);
            for (int i = 0; i < 16; i++) { helper.assertTrue(!menu.quickMoveStack(player, 2).isEmpty(), "Remaining stored charge must craft"); }
            helper.assertTrue(menu.quickMoveStack(player, 2).isEmpty(), "No output may be granted without charge");
            int total = 0;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.is(ModBlocks.DARKGO.get().asItem())) { total += stack.getCount(); }
            }
            helper.assertTrue(total == 80 && machine.data.get(0) == 0 && machine.inventory.getStackInSlot(1).isEmpty()
                    && machine.inventory.getStackInSlot(0).getCount() == 1, "One core must craft exactly 80, with no free output");
            helper.succeed();
        } finally { manager.replaceRecipes(original); }
    }
}
