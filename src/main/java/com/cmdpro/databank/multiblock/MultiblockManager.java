package com.cmdpro.databank.multiblock;

import com.cmdpro.databank.Databank;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class MultiblockManager extends SimpleJsonResourceReloadListener {
    protected static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public static MultiblockManager instance;
    protected MultiblockManager() {
        super(GSON, "databank/multiblocks");
    }
    public static MultiblockManager getOrCreateInstance() {
        if (instance == null) {
            instance = new MultiblockManager();
        }
        return instance;
    }
    public static Map<ResourceLocation, Multiblock> multiblocks = new HashMap<>();
    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        multiblocks = new HashMap<>();
        Databank.LOGGER.info("Adding Databank Multiblocks");
        for (Map.Entry<ResourceLocation, JsonElement> i : pObject.entrySet()) {
            ResourceLocation location = i.getKey();
            if (location.getPath().startsWith("_")) {
                continue;
            }

            try {
                JsonObject obj = i.getValue().getAsJsonObject();
                Multiblock multiblock = serializer.read(i.getKey(), obj);
                multiblocks.put(i.getKey(), multiblock);
            } catch (IllegalArgumentException | JsonParseException e) {
                Databank.LOGGER.error("Parsing error loading multiblock {}", location, e);
            }
        }
        Databank.LOGGER.info("Loaded {} multiblocks", multiblocks.size());
    }
    public static MultiblockSerializer serializer = new MultiblockSerializer();
}
