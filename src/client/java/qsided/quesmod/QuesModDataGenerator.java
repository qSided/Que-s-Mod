package qsided.quesmod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import qsided.quesmod.data.*;

public class QuesModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack quesPack = fabricDataGenerator.createPack();
		
		quesPack.addProvider(QuesModelProvider::new);
		quesPack.addProvider(QuesItemTagProvider::new);
		quesPack.addProvider(QuesBlockTagProvider::new);
		quesPack.addProvider(QuesRecipeProvider::new);
		quesPack.addProvider(QuesBlockLootTableProvider::new);
	}
}
