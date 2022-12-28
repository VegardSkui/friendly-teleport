package com.vegardskui.friendlyteleport;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.WorldSavePath;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.vegardskui.friendlyteleport.FriendlyTeleport.MOD_ID;

public class PlayerHomes {
    protected Path path;
    protected HashMap<String, Location> homes;

    public PlayerHomes(ServerPlayerEntity player) {
        this.path = player.server.getSavePath(WorldSavePath.ROOT).resolve(MOD_ID).resolve("homes").resolve(player.getUuidAsString().concat(".json"));
    }

    public void load() throws java.io.IOException {
        if (Files.notExists(this.path)) {
            this.homes = new HashMap<String, Location>();
            return;
        }

        var mapType = new TypeToken<Map<String, Location>>() {}.getType();
        var gson = new GsonBuilder().registerTypeAdapter(mapType, new PlayerHomesDeserializer()).create();
        var json = Files.readString(this.path);

        this.homes = gson.fromJson(json, mapType);
    }

    public void save() throws java.io.IOException {
        var json = new Gson().toJson(this.homes);
        Files.createDirectories(this.path.getParent());
        Files.writeString(this.path, json);
    }

    public Set<String> names() {
        return this.homes.keySet();
    }

    public Location get(String name) {
        return this.homes.get(name);
    }

    public void put(String name, Location location) {
        this.homes.put(name, location);
    }
}
