package com.vegardskui.friendlyteleport;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Location {
    protected String dimension;
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;

    public Location(String dimension, double x, double y, double z, float yaw, float pitch) {
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location(ServerPlayerEntity player) {
        this.dimension = player.getWorld().getRegistryKey().getValue().toString();
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
        this.yaw = player.getYaw();
        this.pitch = player.getPitch();
    }

    public RegistryKey<World> getDimension() {
        return RegistryKey.of(RegistryKeys.WORLD, Identifier.tryParse(this.dimension));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }
}
