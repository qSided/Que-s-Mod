package qsided.quesmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
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
	public static final RegistryKey<PlacedFeature> MYTHRIL_DEPOSIT_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "mythril_deposit_feature"));

	@Override
	public void onInitialize() {
		QuesItems.initialize();
		QuesBlocks.initialize();
		QuesArmorMaterials.initialize();
		
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, MYTHRIL_DEPOSIT_PLACED_KEY);
		
		
		
		LOGGER.info("Que's mod loaded!");
	}
}