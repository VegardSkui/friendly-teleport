package com.vegardskui.friendlyteleport;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.io.IOException;

public class HomeSetCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrThrow();
        var name = StringArgumentType.getString(context, "name");
        var location = new Location(player);

        var homes = new PlayerHomes(player);

        try {
            homes.load();
            homes.put(name, location);
            homes.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.sendMessage(Text.literal("Set " + name + " home"));
        FriendlyTeleport.LOGGER.info("[FriendlyTeleport] " + player.getDisplayName().getString() + " set their " + name + "home");

        return 1;
    }
}
