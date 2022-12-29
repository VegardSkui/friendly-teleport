package com.vegardskui.friendlyteleport;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.IOException;

public class HomeTeleportMainCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrThrow();

        // Load the player's homes
        var homes = new PlayerHomes(player);
        try {
            homes.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var location = homes.get("main");

        // Write an error message to the user if no home was found
        if (location == null) {
            throw new SimpleCommandExceptionType(Text.literal("You have no home named main")).create();
        }

        player.teleport(player.server.getWorld(location.getDimension()), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        player.sendMessage(Text.literal("Teleported to your main home"));
        FriendlyTeleport.LOGGER.info("[FriendlyTeleport] " + player.getDisplayName().getString() + " teleported to their main home");

        return 1;
    }
}
