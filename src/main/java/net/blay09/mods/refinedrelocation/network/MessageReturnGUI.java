package net.blay09.mods.refinedrelocation.network;

import net.blay09.mods.refinedrelocation.api.container.IContainerReturnable;
import net.blay09.mods.refinedrelocation.api.container.ReturnCallback;
import net.blay09.mods.refinedrelocation.container.RootFilterContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

public class MessageReturnGUI {

    public static void handle(MessageReturnGUI message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();
            if (player == null) {
                return;
            }

            Container container = player.openContainer;
            if (container instanceof RootFilterContainer) {
                TileEntity tileEntity = ((RootFilterContainer) container).getTileEntity();
                if (tileEntity instanceof INamedContainerProvider) {
                    NetworkHooks.openGui(player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
                }
            } else if (container instanceof IContainerReturnable) {
                ReturnCallback callback = ((IContainerReturnable) container).getReturnCallback();
                if (callback != null) {
                    callback.returnToParentGui();
                }
            }
        });
        context.setPacketHandled(true);
    }
}
