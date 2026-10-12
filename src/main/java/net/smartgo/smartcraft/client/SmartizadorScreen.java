package net.smartgo.smartcraft.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.smartgo.smartcraft.SmartCraft;
import net.smartgo.smartcraft.menu.SmartizadorMenu;
import net.smartgo.smartcraft.recipe.SmartizadorRecipe;
import java.util.List;

public class SmartizadorScreen extends AbstractContainerScreen<SmartizadorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SmartCraft.MOD_ID, "textures/gui/smartizador_gui.png");
    private static final ResourceLocation CHARGE_TEXTURE =
            new ResourceLocation(SmartCraft.MOD_ID, "textures/gui/smartizador_barra.png");
    private static final int CHARGE_WIDTH = 14;
    private static final int CHARGE_HEIGHT = 56;
    private int firstRow;
    private boolean draggingScroll;

    public SmartizadorScreen(SmartizadorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 166;
        titleLabelY = 4;
        inventoryLabelY = 73;
    }

    private int maxRow() { return Math.max(0, (menu.recipes().size() + 3) / 4 - 3); }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int filled = CHARGE_HEIGHT * menu.charge() / 20;
        if (filled > 0) {
            graphics.blit(CHARGE_TEXTURE, leftPos + 13, topPos + 15 + CHARGE_HEIGHT - filled,
                    0, CHARGE_HEIGHT - filled, CHARGE_WIDTH, filled, CHARGE_WIDTH, CHARGE_HEIGHT);
        }
        firstRow = Math.min(firstRow, maxRow());
        List<SmartizadorRecipe> recipes = menu.recipes();
        for (int cell = 0; cell < 12; cell++) {
            int index = firstRow * 4 + cell;
            if (index >= recipes.size()) { break; }
            int x = leftPos + 54 + cell % 4 * 16;
            int y = topPos + 16 + cell / 4 * 18;
            int textureY = index == menu.selectedIndex() ? 184 :
                    (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 18 ? 202 : 166);
            graphics.blit(TEXTURE, x, y, 0, textureY, 16, 18);
            graphics.renderItem(recipes.get(index).getResultItem(minecraft.level.registryAccess()), x, y + 1);
        }
        int slider = maxRow() == 0 ? 0 : 39 * firstRow / maxRow();
        graphics.blit(TEXTURE, leftPos + 121, topPos + 16 + slider,
                maxRow() == 0 ? 188 : 176, 0, 12, 15);
        if (recipes.isEmpty()) {
            graphics.drawWordWrap(font, Component.translatable("gui.smartcraft.smartizador.no_recipes"),
                    leftPos + 56, topPos + 23, 62, 0xFFE0E0E0);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
        if (mouseX >= leftPos + 13 && mouseX < leftPos + 27 && mouseY >= topPos + 15 && mouseY < topPos + 71) {
            graphics.renderTooltip(font, Component.translatable("gui.smartcraft.smartizador.charge", menu.charge(), 20), mouseX, mouseY);
        }
        List<SmartizadorRecipe> recipes = menu.recipes();
        for (int cell = 0; cell < 12; cell++) {
            int index = firstRow * 4 + cell;
            if (index >= recipes.size()) { break; }
            int x = leftPos + 54 + cell % 4 * 16, y = topPos + 16 + cell / 4 * 18;
            if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 18) {
                ItemStack output = recipes.get(index).getResultItem(minecraft.level.registryAccess());
                graphics.renderTooltip(font, output, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (int cell = 0; cell < 12; cell++) {
                int x = leftPos + 54 + cell % 4 * 16, y = topPos + 16 + cell / 4 * 18;
                int index = firstRow * 4 + cell;
                if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 18
                        && menu.clickMenuButton(minecraft.player, index)) {
                    minecraft.gameMode.handleInventoryButtonClick(menu.containerId, index);
                    return true;
                }
            }
            if (mouseX >= leftPos + 120 && mouseX < leftPos + 134 && mouseY >= topPos + 14 && mouseY < topPos + 70 && maxRow() > 0) {
                draggingScroll = true;
                scrollTo(mouseY);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void scrollTo(double mouseY) {
        firstRow = Math.max(0, Math.min(maxRow(), (int) Math.round((mouseY - topPos - 23.5) / 39 * maxRow())));
    }

    @Override public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (draggingScroll && button == 0) { scrollTo(mouseY); return true; }
        return super.mouseDragged(mouseX, mouseY, button, dx, dy);
    }
    @Override public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingScroll = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }
    @Override public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (mouseX >= leftPos + 53 && mouseX < leftPos + 134 && mouseY >= topPos + 14 && mouseY < topPos + 70) {
            firstRow = Math.max(0, Math.min(maxRow(), firstRow - (int) Math.signum(delta)));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
}
