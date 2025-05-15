package com.cmdpro.databank.registry;

import com.cmdpro.databank.Databank;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Databank.MOD_ID);

    private static <T extends Item> Supplier<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }
}
