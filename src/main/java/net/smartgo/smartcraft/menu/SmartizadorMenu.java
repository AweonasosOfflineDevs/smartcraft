package net.smartgo.smartcraft.menu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.smartgo.smartcraft.block.ModBlocks;
import net.smartgo.smartcraft.block.entity.SmartizadorBlockEntity;
import net.smartgo.smartcraft.item.ModItems;
import net.smartgo.smartcraft.recipe.ModRecipes;
import net.smartgo.smartcraft.recipe.SmartizadorRecipe;
import java.util.Comparator;
import java.util.List;
public class SmartizadorMenu extends AbstractContainerMenu {
    private final Level level;
    private final SmartizadorBlockEntity machine;
    private final ItemStackHandler inputs;
    private final ContainerData data;
    private final ContainerLevelAccess access;
    private final SimpleContainer result = new SimpleContainer(1);
    private final DataSlot selected = DataSlot.standalone();
    public SmartizadorMenu(int id, Inventory inv, FriendlyByteBuf buffer) {
        this(id, inv, (SmartizadorBlockEntity) null); buffer.readBlockPos();
    }
    public SmartizadorMenu(int id, Inventory inv, SmartizadorBlockEntity machine) {
        super(ModMenus.SMARTIZADOR.get(), id);
        this.level = inv.player.level(); this.machine = machine;
        inputs = machine == null ? new ItemStackHandler(2) : machine.inventory;
        data = machine == null ? new SimpleContainerData(1) : machine.data;
        access = machine == null ? ContainerLevelAccess.NULL : ContainerLevelAccess.create(level, machine.getBlockPos());
        selected.set(-1); addDataSlot(selected); addDataSlots(data);
        addSlot(new SlotItemHandler(inputs, 0, 33, 21) {
            @Override public boolean mayPlace(ItemStack stack) { return stack.is(ModBlocks.SMART.get().asItem()); }
            @Override public void setChanged() {
                super.setChanged();
                if (machine != null) { machine.setChanged(); }
            }
        });
        addSlot(new SlotItemHandler(inputs, 1, 33, 49) {
            @Override public boolean mayPlace(ItemStack stack) { return stack.is(ModItems.SMARTCORE.get()); }
            @Override public int getMaxStackSize() { return 1; }
            @Override public void setChanged() {
                super.setChanged();
                if (machine != null) { machine.setChanged(); }
            }
        });
        addSlot(new Slot(result, 0, 142, 33) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }
            @Override public boolean mayPickup(Player player) { return canCraft(); }
            @Override public void onTake(Player player, ItemStack stack) {
                if (machine != null && canCraft() && machine.consume()) { updateResult(); }
                super.onTake(player, stack);
            }
        });
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) { addSlot(new Slot(inv, col + row * 9 + 9, 9 + col * 18, 84 + row * 18)); }
        }
        for (int col = 0; col < 9; col++) { addSlot(new Slot(inv, col, 9 + col * 18, 142)); }
    }
    public int charge() { return data.get(0); }
    public int selectedIndex() { return selected.get(); }
    public List<SmartizadorRecipe> recipes() {
        SimpleContainer input = new SimpleContainer(inputs.getStackInSlot(0));
        return level.getRecipeManager().getAllRecipesFor(ModRecipes.SMARTIZADOR.get()).stream()
                .filter(recipe -> recipe.matches(input, level))
                .sorted(Comparator.comparing(recipe -> recipe.getId().toString())).toList();
    }
    private boolean canCraft() {
        List<SmartizadorRecipe> recipes = recipes();
        return charge() > 0 && selected.get() >= 0 && selected.get() < recipes.size();
    }
    private void updateResult() {
        if (selected.get() >= recipes().size()) { selected.set(-1); }
        ItemStack output = canCraft() ? recipes().get(selected.get()).getResultItem(level.registryAccess()).copy() : ItemStack.EMPTY;
        if (!ItemStack.matches(result.getItem(0), output)) { result.setItem(0, output); }
    }
    @Override public void broadcastChanges() {
        if (machine != null) { machine.refill(); updateResult(); }
        super.broadcastChanges();
    }
    @Override public boolean clickMenuButton(Player player, int index) {
        if (index < 0 || index >= recipes().size()) { return false; }
        selected.set(index);
        if (machine != null) { updateResult(); broadcastChanges(); }
        return true;
    }
    @Override public boolean stillValid(Player player) { return stillValid(access, player, ModBlocks.SMARTIZADOR.get()); }
    @Override public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem() || !slot.mayPickup(player)) { return ItemStack.EMPTY; }
        ItemStack original = slot.getItem(), copy = original.copy();
        if (index < 3) {
            if (!moveItemStackTo(original, 3, 39, true)) { return ItemStack.EMPTY; }
        } else if (original.is(ModBlocks.SMART.get().asItem())) {
            if (!moveItemStackTo(original, 0, 1, false)) { return ItemStack.EMPTY; }
        } else if (original.is(ModItems.SMARTCORE.get())) {
            if (!moveItemStackTo(original, 1, 2, false)) { return ItemStack.EMPTY; }
        } else if (index < 30) {
            if (!moveItemStackTo(original, 30, 39, false)) { return ItemStack.EMPTY; }
        } else if (!moveItemStackTo(original, 3, 30, false)) { return ItemStack.EMPTY; }
        if (original.isEmpty()) { slot.set(ItemStack.EMPTY); } else { slot.setChanged(); }
        if (original.getCount() == copy.getCount()) { return ItemStack.EMPTY; }
        slot.onTake(player, copy); return copy;
    }
}