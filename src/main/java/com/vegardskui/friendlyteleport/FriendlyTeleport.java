package com.vegardskui.friendlyteleport;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.minecraft.server.command.CommandManager.*;

public class FriendlyTeleport implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("friendly_teleport");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("ftp").then(
                argument("target", EntityArgumentType.player()).executes(new TeleportCommand())
            ));
        });

        LOGGER.info("[FriendlyTeleport] Initialized");
    }
}
