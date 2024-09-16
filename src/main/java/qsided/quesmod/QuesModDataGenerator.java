package qsided.quesmod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import qsided.quesmod.data.QuesBlockLootTables;
import qsided.quesmod.data.QuesModels;
import qsided.quesmod.data.QuesRecipes;

public class QuesModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack quesPack = fabricDataGenerator.createPack();
		quesPack.addProvider(QuesBlockLootTables::new);
		quesPack.addProvider(QuesModels::new);
		quesPack.addProvider(QuesRecipes::new);
	}
}
