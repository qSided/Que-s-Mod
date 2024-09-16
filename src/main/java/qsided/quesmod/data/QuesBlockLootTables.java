package qsided.quesmod.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import qsided.quesmod.blocks.QuesBlocks;
import qsided.quesmod.items.QuesItems;

import java.util.concurrent.CompletableFuture;

public class QuesBlockLootTables extends FabricBlockLootTableProvider {
    public QuesBlockLootTables(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }
    
    @Override
    public void generate() {
        addDrop(QuesBlocks.MYTHRIL_CHUNK, drops(QuesItems.MYTHRIL_FRAGMENT));
    }
}
