package net.smartgo.smartcraft.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SmartcoreItem extends Item {
    public static final int MAX_USES = 80;
    public static final int SMARTIZADOR_CAPACITY = 20;

    public SmartcoreItem() {
        super(new Item.Properties().durability(MAX_USES).setNoRepair());
    }

    public static int remainingUses(ItemStack stack) {
        if (!(stack.getItem() instanceof SmartcoreItem) || stack.isEmpty()) {
            return 0;
        }
        return Math.max(0, MAX_USES - stack.getDamageValue());
    }

    /**
     * Transfers only the charge that fits in the Smartizador, consuming the
     * same number of core uses. Must be called on the logical server.
     * Returns the new stored charge. A depleted core is removed from its stack.
     */
    public static int refill(ItemStack core, int storedCharge) {
        int charge = Math.max(0, Math.min(SMARTIZADOR_CAPACITY, storedCharge));
        int available = remainingUses(core);
        if (available == 0) {
            if (!core.isEmpty() && core.getItem() instanceof SmartcoreItem) {
                core.shrink(1);
            }
            return charge;
        }
        int transferred = Math.min(SMARTIZADOR_CAPACITY - charge, available);
        if (transferred == available) {
            core.shrink(1);
        } else if (transferred > 0) {
            core.setDamageValue(core.getDamageValue() + transferred);
        }
        return charge + transferred;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.smartcraft.smartcore.uses", remainingUses(stack), MAX_USES));
    }
}
