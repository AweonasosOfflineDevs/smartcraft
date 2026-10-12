package net.smartgo.smartcraft.block.entity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.smartgo.smartcraft.block.ModBlocks;
import net.smartgo.smartcraft.item.ModItems;
import net.smartgo.smartcraft.item.custom.SmartcoreItem;
import net.smartgo.smartcraft.menu.SmartizadorMenu;
public class SmartizadorBlockEntity extends BlockEntity implements MenuProvider {
    private int charge;
    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override protected void onContentsChanged(int slot) { setChanged(); }
        @Override public boolean isItemValid(int slot, ItemStack stack) {
            return slot == 0 ? stack.is(ModBlocks.SMART.get().asItem()) : stack.is(ModItems.SMARTCORE.get());
        }
        @Override public int getSlotLimit(int slot) { return slot == 1 ? 1 : 64; }
    };
    public final ContainerData data = new ContainerData() {
        @Override public int get(int index) { return charge; }
        @Override public void set(int index, int value) { charge = Math.max(0, Math.min(20, value)); }
        @Override public int getCount() { return 1; }
    };
    public SmartizadorBlockEntity(BlockPos pos, BlockState state) { super(ModBlockEntities.SMARTIZADOR.get(), pos, state); }
    public void refill() {
        if (level == null || level.isClientSide) { return; }
        ItemStack core = inventory.getStackInSlot(1);
        int oldCharge = charge, oldDamage = core.getDamageValue(), oldCount = core.getCount();
        charge = SmartcoreItem.refill(core, charge);
        if (charge != oldCharge || core.getDamageValue() != oldDamage || core.getCount() != oldCount) { setChanged(); }
    }
    public boolean consume() {
        if (level == null || level.isClientSide || charge <= 0 || inventory.getStackInSlot(0).isEmpty()) { return false; }
        inventory.extractItem(0, 1, false); charge--; refill(); setChanged(); return true;
    }
    @Override protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag); tag.put("Inventory", inventory.serializeNBT()); tag.putInt("Charge", charge);
    }
    @Override public void load(CompoundTag tag) {
        super.load(tag); inventory.deserializeNBT(tag.getCompound("Inventory")); charge = Math.max(0, Math.min(20, tag.getInt("Charge")));
    }
    @Override public Component getDisplayName() { return Component.translatable("block.smartcraft.smartizador"); }
    @Override public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) { refill(); return new SmartizadorMenu(id, inv, this); }
}