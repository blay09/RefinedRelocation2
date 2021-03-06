package net.blay09.mods.refinedrelocation.client.gui.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.blay09.mods.refinedrelocation.api.RefinedRelocationAPI;
import net.blay09.mods.refinedrelocation.api.client.IDrawable;
import net.blay09.mods.refinedrelocation.api.filter.IFilter;
import net.blay09.mods.refinedrelocation.api.filter.IRootFilter;
import net.blay09.mods.refinedrelocation.client.gui.GuiTextures;
import net.blay09.mods.refinedrelocation.client.gui.RootFilterScreen;
import net.blay09.mods.refinedrelocation.client.gui.base.ITooltipElement;
import net.blay09.mods.refinedrelocation.container.RootFilterContainer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.*;

import java.util.List;

import static net.blay09.mods.refinedrelocation.util.TextUtils.formattedTranslation;

public class GuiFilterSlot extends Button implements ITooltipElement {

    private final RootFilterScreen parentGui;
    private final IDrawable texture;
    private final IRootFilter rootFilter;
    private final int index;

    public GuiFilterSlot(int x, int y, RootFilterScreen parentGui, IRootFilter rootFilter, int index) {
        super(x, y, 24, 24, new StringTextComponent(""), it -> {
        });
        this.parentGui = parentGui;
        this.rootFilter = rootFilter;
        this.index = index;
        texture = GuiTextures.FILTER_SLOT;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        texture.bind();
        texture.draw(matrixStack, x, y, getBlitOffset());

        IFilter filter = rootFilter.getFilter(index);
        if (filter != null) {
            IDrawable filterIcon = filter.getFilterIcon();
            if (filterIcon != null) {
                filterIcon.draw(matrixStack, x, y, 24, 24, getBlitOffset());
            }
        }
        if (parentGui.isTopMostElement(this, mouseX, mouseY)) {
            fill(matrixStack, x + 1, y + 1, x + width - 1, y + height - 1, 0x99FFFFFF);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        IFilter filter = rootFilter.getFilter(index);
        if (filter == null) {
            RefinedRelocationAPI.sendContainerMessageToServer(RootFilterContainer.KEY_OPEN_ADD_FILTER, 0);
            // Minecraft.getInstance().displayGuiScreen(new AddFilterScreen(parentGui, parentGui.getPlayerInventory(), parentGui.getTitle()));
        } else {
            RefinedRelocationAPI.sendContainerMessageToServer(RootFilterContainer.KEY_EDIT_FILTER, index);
        }
    }

    @Override
    public void addTooltip(List<ITextComponent> list) {
        IFilter filter = rootFilter.getFilter(index);
        if (filter == null) {
            list.add(formattedTranslation(TextFormatting.GRAY, "gui.refinedrelocation:root_filter.no_filter_set"));
            list.add(formattedTranslation(TextFormatting.YELLOW, "gui.refinedrelocation:root_filter.click_to_add_filter"));
        } else {
            list.add(new TranslationTextComponent(filter.getLangKey()));
            if (filter.hasConfiguration()) {
                list.add(formattedTranslation(TextFormatting.YELLOW, "gui.refinedrelocation:root_filter.click_to_configure"));
            } else {
                list.add(formattedTranslation(TextFormatting.GRAY, "gui.refinedrelocation:root_filter.not_configurable"));
            }
        }
    }

    public int getFilterIndex() {
        return index;
    }

    public boolean hasFilter() {
        return rootFilter.getFilter(index) != null;
    }
}
