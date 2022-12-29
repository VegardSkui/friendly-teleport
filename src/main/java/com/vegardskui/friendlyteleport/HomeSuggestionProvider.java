package com.vegardskui.friendlyteleport;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class HomeSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrThrow();
        var homes = new PlayerHomes(player);
        try {
            homes.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        homes.names().forEach(builder::suggest);

        return builder.buildFuture();
    }
}
