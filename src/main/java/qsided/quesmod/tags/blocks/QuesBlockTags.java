package qsided.quesmod.tags.blocks;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

public class QuesBlockTags {
    
    public static final TagKey<Block> INCORRECT_FOR_MYTHRIL_TOOL = TagKey.of(RegistryKeys.BLOCK, Identifier.of(QuesMod.MOD_ID, "incorrect_for_mythril"));
    
    public static void initialize() {}
}
