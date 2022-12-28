package com.vegardskui.friendlyteleport;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static net.minecraft.server.command.CommandManager.*;

public class FriendlyTeleport implements ModInitializer {
    public static final String MOD_ID = "friendly_teleport";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("ftp").then(
                argument("target", EntityArgumentType.player()).executes(new TeleportCommand())
            ));

            dispatcher.register(literal("sethome").then(
                argument("name", StringArgumentType.word()).executes(new HomeSetCommand())
            ).executes(new HomeSetMainCommand()));

            dispatcher.register(literal("home").then(
                argument("name", StringArgumentType.word()).executes(new HomeTeleportCommand())
            ).executes(new HomeTeleportMainCommand()));
        });

        LOGGER.info("[FriendlyTeleport] Initialized");
    }
}
