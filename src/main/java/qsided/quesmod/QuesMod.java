package qsided.quesmod;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qsided.quesmod.blocks.QuesBlocks;
import qsided.quesmod.items.QuesItems;
import qsided.quesmod.items.materials.QuesArmorMaterials;

public class QuesMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("ques-mod");
	public static final String MOD_ID = "ques-mod";

	@Override
	public void onInitialize() {
		QuesItems.initialize();
		QuesBlocks.initialize();
		QuesArmorMaterials.initialize();

		LOGGER.info("Que's mod loaded!");
	}
}