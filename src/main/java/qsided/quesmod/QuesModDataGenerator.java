package qsided.quesmod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import qsided.quesmod.data.QuesBlockLootTables;
import qsided.quesmod.data.QuesItemModels;

public class QuesModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack quesPack = fabricDataGenerator.createPack();
		quesPack.addProvider(QuesBlockLootTables::new);
		quesPack.addProvider(QuesItemModels::new);
	}
}
