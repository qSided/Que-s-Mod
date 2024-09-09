package qsided.quesmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qsided.quesmod.blocks.QuesBlocks;
import qsided.quesmod.items.QuesItems;
import qsided.quesmod.items.materials.QuesArmorMaterials;
import qsided.quesmod.networking.*;
import qsided.quesmod.skills.MiningSkill;

import java.text.DecimalFormat;

public class QuesMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ques-mod");
	public static final String MOD_ID = "ques-mod";
	public static final RegistryKey<PlacedFeature> MYTHRIL_DEPOSIT_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "mythril_deposit_feature"));
	@Override
	public void onInitialize() {
		QuesItems.initialize();
		QuesBlocks.initialize();
		QuesArmorMaterials.initialize();
		MiningSkill.registerMining();
		
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, MYTHRIL_DEPOSIT_PLACED_KEY);
		
		PayloadTypeRegistry.playS2C().register(LevelUpPayload.ID, LevelUpPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(RequestSkillsPayload.ID, RequestSkillsPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SendSkillsLevelsPayload.ID, SendSkillsLevelsPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SendSkillsExperiencePayload.ID, SendSkillsExperiencePayload.CODEC);
		
		ServerPlayNetworking.registerGlobalReceiver(RequestSkillsPayload.ID, (payload, context) -> {
			PlayerData playerState = StateSaverAndLoader.getPlayerState(context.player());
			
			sendSkillData(playerState, context.player());
		});
		
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			PlayerData playerState = getPlayerState(handler.getPlayer());
			playerState.skillLevels.putIfAbsent("miningLevel", 1);
			playerState.skillExperience.putIfAbsent("miningExp", 0F);
			
			sendSkillData(playerState, handler.getPlayer());
		});
		
		LOGGER.info("Que's mod loaded!");
	}
	
	public PlayerData getPlayerState(ServerPlayerEntity player) {
		return StateSaverAndLoader.getPlayerState(player);
	}
	
	public DecimalFormat decimalFormat() {
		decimalFormat().setMaximumFractionDigits(2);
		return decimalFormat();
	}
	
	public static void sendSkillData(PlayerData playerState, ServerPlayerEntity player) {
		Integer miningLevel = playerState.skillLevels.getOrDefault("miningLevel", 1);
		Integer enchantingLevel = playerState.skillLevels.getOrDefault("enchantingLevel", 1);
		Integer combatLevel = playerState.skillLevels.getOrDefault("combatLevel", 1);
		Integer alchemyLevel = playerState.skillLevels.getOrDefault("alchemyLevel", 1);
		
		Float miningExp = playerState.skillExperience.getOrDefault("miningExp", 0F);
		Float enchantingExp = playerState.skillExperience.getOrDefault("enchantingExp", 0F);
		Float combatExp = playerState.skillExperience.getOrDefault("combatExp", 0F);
		Float alchemyExp = playerState.skillExperience.getOrDefault("alchemyExp", 0F);
		
		ServerPlayNetworking.send(player, new SendSkillsLevelsPayload(miningLevel, enchantingLevel, combatLevel, alchemyLevel));
		ServerPlayNetworking.send(player, new SendSkillsExperiencePayload(miningExp, enchantingExp, combatExp, alchemyExp));
	}
}