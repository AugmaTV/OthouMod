package fr.augma.othoumod;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import fr.augma.othoumod.payload.CompactPayload;
import fr.augma.othoumod.util.CompactUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.GameRuleCommand;

public class OthouMod_1_21 implements ModInitializer {

    public static final String COMPACT_COMMAND = "compact";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    LiteralArgumentBuilder
                            .<CommandSourceStack>literal(COMPACT_COMMAND)
                            .executes(context -> {
                                if (context.getSource().getPlayer() == null) {
                                    return 0;
                                }

                                CompactUtils.compact(context.getSource().getPlayer());
                                return 1;
                            })
            );
        });

        PayloadTypeRegistry.playC2S().register(CompactPayload.ID, CompactPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CompactPayload.ID, (payload, context) -> {
            CompactUtils.compact(context.player(), payload.getSlotId());
        });
    }
}
