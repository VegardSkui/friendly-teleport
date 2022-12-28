package com.vegardskui.friendlyteleport;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerHomesDeserializer implements JsonDeserializer<Map<String, Location>> {
    @Override
    public Map<String, Location> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return jsonElement.getAsJsonObject().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> {
            var homeObj = e.getValue().getAsJsonObject();
            return new Location(homeObj.get("dimension").getAsString(), homeObj.get("x").getAsDouble(), homeObj.get("y").getAsDouble(), homeObj.get("z").getAsDouble(), homeObj.get("yaw").getAsFloat(), homeObj.get("pitch").getAsFloat());
        }));
    }
}
