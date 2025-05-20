package fr.augma.othoumod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import static fr.augma.othoumod.OthouMod_1_21.COMPACT_COMMAND;


public class OthouMod_1_21Client implements ClientModInitializer {

    public static KeyMapping keyBinding;

    private boolean wasClicked = false;

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping("Compact", GLFW.GLFW_KEY_K, "OthouMod"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.isDown() && !wasClicked) {
                client.player.connection.sendCommand(COMPACT_COMMAND);
                wasClicked = true;
                return;
            }

            if (!keyBinding.isDown() && wasClicked) {
                wasClicked = false;
            }
        });
    }
}
