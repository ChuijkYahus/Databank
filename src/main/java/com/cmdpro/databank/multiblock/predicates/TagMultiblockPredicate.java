package com.cmdpro.databank.multiblock.predicates;

import com.cmdpro.databank.multiblock.MultiblockPredicate;
import com.cmdpro.databank.multiblock.MultiblockPredicateSerializer;
import com.cmdpro.databank.registry.MultiblockPredicateRegistry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class TagMultiblockPredicate extends MultiblockPredicate {
    public TagMultiblockPredicate(TagKey<Block> tag) {
        this.tag = tag;
    }
    public TagKey<Block> tag;
    @Override
    public boolean isSame(BlockState other, Rotation rotation) {
        return other.is(tag);
    }

    @Override
    public MultiblockPredicateSerializer<?> getSerializer() {
        return MultiblockPredicateRegistry.TAG.get();
    }
    @Override
    public BlockState getVisual() {
        HolderSet.Named<Block> tag = BuiltInRegistries.BLOCK.getOrCreateTag(this.tag);
        List<Holder<Block>> blocks = tag.stream().toList();
        if (!blocks.isEmpty()) {
            Block block = blocks.get((int)(Util.getMillis() / 1000L) % blocks.size()).value();
            return block.defaultBlockState();
        }
        return Blocks.AIR.defaultBlockState();
    }
}
