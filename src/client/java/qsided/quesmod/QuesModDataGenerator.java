package qsided.quesmod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import qsided.quesmod.data.QuesBlockTagProvider;
import qsided.quesmod.data.QuesItemTagProvider;
import qsided.quesmod.data.QuesModelProvider;
import qsided.quesmod.data.QuesRecipeProvider;

public class QuesModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack quesPack = fabricDataGenerator.createPack();
		
		quesPack.addProvider(QuesModelProvider::new);
		quesPack.addProvider(QuesItemTagProvider::new);
		quesPack.addProvider(QuesBlockTagProvider::new);
		quesPack.addProvider(QuesRecipeProvider::new);
	}
}
