package qsided.quesmod.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

public class QuesBlocks {
    public static final Block MYTHRIL_CHUNK = register(new Block(AbstractBlock.Settings.create().hardness(3)), "mythril_chunk", true);
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = Identifier.of(QuesMod.MOD_ID, name);
        
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }
        
        return Registry.register(Registries.BLOCK, id, block);
    }
    
    public static void initialize() {
    }
}
