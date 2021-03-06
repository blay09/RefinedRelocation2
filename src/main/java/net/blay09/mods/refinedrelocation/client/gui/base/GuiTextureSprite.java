package net.blay09.mods.refinedrelocation.client.gui.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.blay09.mods.refinedrelocation.api.client.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class GuiTextureSprite implements IDrawable {
    private static final ResourceLocation TEXTURE = new ResourceLocation("refinedrelocation", "textures/gui/gui.png");

    private final int spriteX;
    private final int spriteY;
    private final int spriteWidth;
    private final int spriteHeight;

    public GuiTextureSprite(int spriteX, int spriteY, int spriteWidth, int spriteHeight) {
        this.spriteX = spriteX;
        this.spriteY = spriteY;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
    }

    public void bind() {
        TextureManager textureManager = Minecraft.getInstance().textureManager;
        textureManager.bindTexture(TEXTURE);
    }

    @Override
    public void draw(MatrixStack matrixStack, double x, double y, double zLevel) {
        AbstractGui.blit(matrixStack, (int) x, (int) y, spriteX, spriteY, spriteWidth, spriteHeight, 256, 256);
    }

    @Override
    public void draw(MatrixStack matrixStack, double x, double y, double width, double height, double zLevel) {
        AbstractGui.blit(matrixStack, (int) x, (int) y, (int) width, (int) height, spriteX, spriteY, spriteWidth, spriteHeight, 256, 256);
    }
}
