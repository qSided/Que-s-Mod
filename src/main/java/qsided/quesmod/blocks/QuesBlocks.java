package qsided.quesmod.blocks;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

public class QuesBlocks {
    public static final Block MYTHRIL_DEPOSIT = register(new Block(AbstractBlock.Settings.create().hardness(30).requiresTool()), "mythril_deposit", true);
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = Identifier.of(QuesMod.MOD_ID, name);
        
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }
        
        return Registry.register(Registries.BLOCK, id, block);
    }
    
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(QuesBlocks.MYTHRIL_DEPOSIT.asItem());
        });
    }
}
