package net.blay09.mods.refinedrelocation.client.gui.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.blay09.mods.refinedrelocation.api.RefinedRelocationAPI;
import net.blay09.mods.refinedrelocation.client.gui.GuiTextures;
import net.blay09.mods.refinedrelocation.client.gui.base.ITooltipElement;
import net.blay09.mods.refinedrelocation.client.gui.base.element.GuiImageButton;
import net.blay09.mods.refinedrelocation.container.BlockExtenderContainer;
import net.blay09.mods.refinedrelocation.tile.TileBlockExtender;
import net.blay09.mods.refinedrelocation.util.RelativeSide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.List;

import static net.blay09.mods.refinedrelocation.util.TextUtils.formattedTranslation;

public class GuiSideButton extends GuiImageButton implements ITooltipElement {

    private final TileBlockExtender tileEntity;
    private final RelativeSide side;

    public GuiSideButton(int x, int y, TileBlockExtender tileEntity, RelativeSide side) {
        super(x, y, 16, 16, GuiTextures.SIDE_BUTTONS[side.ordinal()], it -> {
        });
        this.tileEntity = tileEntity;
        this.side = side;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (isMouseOver(mouseX, mouseY)) {
            playDownSound(Minecraft.getInstance().getSoundHandler());
            onClick(mouseX, mouseY, mouseButton);
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void onClick(double mouseX, double mouseY, int mouseButton) {
        if (side == RelativeSide.FRONT) {
            if (Screen.hasShiftDown()) {
                for (RelativeSide side : RelativeSide.values()) {
                    if (side != RelativeSide.FRONT) {
                        tileEntity.setSideMapping(side, null);
                        RefinedRelocationAPI.sendContainerMessageToServer(BlockExtenderContainer.KEY_TOGGLE_SIDE, side.ordinal(), -1);
                    }
                }
            }
            return;
        }

        Direction facing = tileEntity.getSideMapping(side);
        int index;
        if (mouseButton == 0) {
            index = facing != null ? facing.getIndex() + 1 : 0;
        } else if (mouseButton == 1) {
            index = facing != null ? facing.getIndex() - 1 : 5;
        } else {
            return;
        }
        if (index >= 6) {
            facing = null;
        } else if (index < 0) {
            facing = null;
        } else {
            facing = Direction.byIndex(index);
        }

        tileEntity.setSideMapping(side, facing);
        RefinedRelocationAPI.sendContainerMessageToServer(BlockExtenderContainer.KEY_TOGGLE_SIDE, side.ordinal(), facing != null ? facing.getIndex() : -1);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (side != RelativeSide.FRONT) {
            FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
            char sideChar = getFacingChar(tileEntity.getSideMapping(side));
            float labelX = x + width / 2f - fontRenderer.getStringWidth(String.valueOf(sideChar)) / 2f;
            float labelY = y + height / 2f - fontRenderer.FONT_HEIGHT / 2f;
            RenderSystem.translatef(0.5f, 0.5f, 0);
            fontRenderer.drawString(matrixStack, String.valueOf(sideChar), labelX, labelY, 0xFFFFFFFF);
            RenderSystem.translatef(-0.5f, -0.5f, 0);
        }
    }

    @Override
    public void addTooltip(List<ITextComponent> list) {
        if (side == RelativeSide.FRONT) {
            list.add(formattedTranslation(TextFormatting.RED, "gui.refinedrelocation:block_extender.front"));
        } else {
            Direction mapping = tileEntity.getSideMapping(side);
            list.add(formattedTranslation(TextFormatting.YELLOW, "gui.refinedrelocation:block_extender.side_tooltip", formattedTranslation(TextFormatting.WHITE, "gui.refinedrelocation:block_extender.side_" + (mapping != null ? mapping.getString() : "none"))));
            list.add(formattedTranslation(TextFormatting.RED, "gui.refinedrelocation:block_extender.toggle_side"));
        }
    }

    private char getFacingChar(@Nullable Direction facing) {
        if (facing == null) {
            return '-';
        }
        switch (facing) {
            case DOWN:
                return 'D';
            case UP:
                return 'U';
            case WEST:
                return 'W';
            case EAST:
                return 'E';
            case NORTH:
                return 'N';
            case SOUTH:
                return 'S';
        }
        return ' ';
    }

}
