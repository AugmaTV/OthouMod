package fr.augma.othoumod.mixin;

import fr.augma.othoumod.client.OthouMod_1_21Client;
import fr.augma.othoumod.payload.CompactPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {

    @Shadow
    protected Slot hoveredSlot;

    protected AbstractContainerScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void keyPressed(int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (OthouMod_1_21Client.keyBinding.matches(i, j) && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            ClientPlayNetworking.send(new CompactPayload(this.hoveredSlot.index));
            cir.setReturnValue(true);
        }
    }

}