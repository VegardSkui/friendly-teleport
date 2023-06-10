package com.vegardskui.friendlyteleport;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

public class TeleportCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        var source = context.getSource().getPlayerOrThrow();
        var target = EntityArgumentType.getPlayer(context, "target");

        var targetWorld = target.server.getWorld(target.getWorld().getRegistryKey());
        source.teleport(targetWorld, target.getX(), target.getY(), target.getZ(), target.getYaw(), target.getPitch());

        target.sendMessage(source.getDisplayName().copy().append(" teleported to you!"));
        FriendlyTeleport.LOGGER.info("[FriendlyTeleport] " + source.getDisplayName().getString() + " teleported to " + target.getDisplayName().getString());

        return 1;
    }
}
