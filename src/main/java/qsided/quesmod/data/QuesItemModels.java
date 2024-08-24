package qsided.quesmod.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import qsided.quesmod.items.QuesItems;

public class QuesItemModels extends FabricModelProvider {
    public QuesItemModels(FabricDataOutput output) {
        super(output);
    }
    
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    
    }
    
    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(QuesItems.MYTHRIL_SWORD, Models.HANDHELD);
        itemModelGenerator.register(QuesItems.MYTHRIL_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(QuesItems.MYTHRIL_AXE, Models.HANDHELD);
        itemModelGenerator.register(QuesItems.MYTHRIL_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(QuesItems.MYTHRIL_HOE, Models.HANDHELD);
    }
}
