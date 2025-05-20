package fr.augma.othoumod.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

    @Inject(method = "render", at = @At("HEAD"))
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Gui gui = (Gui) (Object) this;

        if (!gui.getDebugOverlay().showDebugScreen()) {
            guiGraphics.drawString(Minecraft.getInstance().font, "Day " + Minecraft.getInstance().level.getDayTime() / 24000L, 4, 4, 0xFFFFFF, false);
        }
    }
}
