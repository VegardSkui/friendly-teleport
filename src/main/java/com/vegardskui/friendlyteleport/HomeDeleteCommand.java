package com.vegardskui.friendlyteleport;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.IOException;

public class HomeDeleteCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrThrow();
        var name = StringArgumentType.getString(context, "name");

        Location location;
        try {
            var homes = new PlayerHomes(player);
            homes.load();
            location = homes.remove(name);
            homes.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Write an error message to the user if no home was found
        if (location == null) {
            throw new SimpleCommandExceptionType(Text.literal("You have no home named " + name)).create();
        }

        player.sendMessage(Text.literal("Deleted " + name + " home"));
        FriendlyTeleport.LOGGER.info("[FriendlyTeleport] " + player.getDisplayName().getString() + " deleted their " + name + " home");

        return 1;
    }
}
