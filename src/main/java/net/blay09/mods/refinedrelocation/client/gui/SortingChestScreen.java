package net.blay09.mods.refinedrelocation.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.blay09.mods.refinedrelocation.client.gui.base.ModContainerScreen;
import net.blay09.mods.refinedrelocation.client.gui.element.GuiOpenFilterButton;
import net.blay09.mods.refinedrelocation.container.SortingChestContainer;
import net.blay09.mods.refinedrelocation.tile.SortingChestTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SortingChestScreen extends ModContainerScreen<SortingChestContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

    private final SortingChestTileEntity tileEntity;

    public SortingChestScreen(SortingChestContainer container, PlayerInventory playerInventory, ITextComponent displayName) {
        super(container, playerInventory, displayName);
        this.tileEntity = container.getTileEntity();
        this.ySize = 168;
    }

    @Override
    public void init() {
        super.init();

        addButton(new GuiOpenFilterButton(guiLeft + xSize - 20, guiTop + 4, tileEntity, 0));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        blit(guiLeft, guiTop, 0, 0, xSize, 3 * 18 + 17);
        blit(guiLeft, guiTop + 3 * 18 + 17, 0, 126, xSize, 96);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        font.drawString(getTitle().getFormattedText(), 8, 6, 4210752);
        font.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

}
