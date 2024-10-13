package qsided.quesmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qsided.quesmod.blocks.QuesBlocks;
import qsided.quesmod.commands.SkillsCommand;
import qsided.quesmod.config.ConfigGenerator;
import qsided.quesmod.config.QuesConfig;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;
import qsided.quesmod.items.QuesItems;
import qsided.quesmod.items.materials.QuesArmorMaterials;
import qsided.quesmod.networking.*;
import qsided.quesmod.skills.*;
import qsided.quesmod.skills.leveling.ExperienceUp;
import qsided.quesmod.skills.leveling.LevelUp;

import java.io.IOException;

public class QuesMod implements ModInitializer {
 
	public static final Logger LOGGER = LoggerFactory.getLogger("ques-mod");
	public static final String MOD_ID = "ques-mod";
	public static final RegistryKey<PlacedFeature> MYTHRIL_CHUNK_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "mythril_chunk_feature"));
    public static final QuesConfig OWO_CONFIG = QuesConfig.createAndLoad();
    
	@Override
	public void onInitialize() {
        QuesItems.initialize();
        QuesBlocks.initialize();
        QuesArmorMaterials.initialize();
        
        try {
            ConfigGenerator.genReqsConfig();
            ConfigGenerator.genWoodcuttingConfig();
            ConfigGenerator.genMiningConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MiningSkill.register();
        EnchantingSkill.register();
        CombatSkill.register();
        WoodcuttingSkill.register();
        EnduranceSkill.register();
        SkillCheckHandler.register();
        
        LevelUp.onLevelUp();
        ExperienceUp.onExperienceUp();
        
        SkillsCommand.register();
        
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES, MYTHRIL_CHUNK_FEATURE);
        
        PayloadTypeRegistry.playS2C().register(LevelUpPayload.ID, LevelUpPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(RequestSkillsPayload.ID, RequestSkillsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSkillsLevelsPayload.ID, SendSkillsLevelsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSkillsExperiencePayload.ID, SendSkillsExperiencePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(SendPlayerFallPayload.ID, SendPlayerFallPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(SendPlayerJumpPayload.ID, SendPlayerJumpPayload.CODEC);
        
        ServerPlayNetworking.registerGlobalReceiver(RequestSkillsPayload.ID, (payload, context) -> {
            PlayerData playerState = StateSaverAndLoader.getPlayerState(context.player());
            
            sendSkillData(playerState, context.player());
        });
        
        ServerPlayNetworking.registerGlobalReceiver(SendPlayerFallPayload.ID, ((payload, context) -> {
            PlayerData state = StateSaverAndLoader.getPlayerState(context.player());
            IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp(context.player(), state, "agility", (float) Math.min((payload.integer() / 100), 50));
        }));
        
        ServerPlayNetworking.registerGlobalReceiver(SendPlayerJumpPayload.ID, ((payload, context) -> {
            PlayerData state = StateSaverAndLoader.getPlayerState(context.player());
            IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp(context.player(), state, "agility", (float) (payload.integer()));
        }));
        
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerData state = getPlayerState(handler.getPlayer());
            
            if (OWO_CONFIG.displayJoinMessage()) {
                handler.getPlayer().sendMessage(Text.translatable("ques-mod.player_joined"));
            }
            
            sendSkillData(state, handler.getPlayer());
        });
        
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.equals(LootTables.END_CITY_TREASURE_CHEST)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.75f))
                        .with(ItemEntry.builder(QuesItems.MYTHRIL_UPGRADE_TEMPLATE))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 1f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
        
        LOGGER.info("Que's mod loaded!");
    }
	
	public PlayerData getPlayerState(ServerPlayerEntity player) {
		return StateSaverAndLoader.getPlayerState(player);
	}
	
	public static void sendSkillData(PlayerData playerState, ServerPlayerEntity player) {
		Integer miningLevel = playerState.skillLevels.getOrDefault("mining", 1);
		Integer enchantingLevel = playerState.skillLevels.getOrDefault("enchanting", 1);
		Integer combatLevel = playerState.skillLevels.getOrDefault("combat", 1);
		Integer woodcuttingLevel = playerState.skillLevels.getOrDefault("woodcutting", 1);
		Integer enduranceLevel = playerState.skillLevels.getOrDefault("endurance", 1);
        Integer agilityLevel = playerState.skillLevels.getOrDefault("agility", 1);
		
		Float miningExp = playerState.skillExperience.getOrDefault("mining", 0F);
		Float enchantingExp = playerState.skillExperience.getOrDefault("enchanting", 0F);
		Float combatExp = playerState.skillExperience.getOrDefault("combat", 0F);
		Float woodcuttingExp = playerState.skillExperience.getOrDefault("woodcutting", 0F);
		Float enduranceExp = playerState.skillExperience.getOrDefault("endurance", 0F);
        Float agilityExp = playerState.skillExperience.getOrDefault("agility", 0F);
		
		ServerPlayNetworking.send(player, new SendSkillsLevelsPayload(miningLevel, enchantingLevel, combatLevel, woodcuttingLevel, enduranceLevel, agilityLevel));
		ServerPlayNetworking.send(player, new SendSkillsExperiencePayload(miningExp, enchantingExp, combatExp, woodcuttingExp, enduranceExp, agilityExp));
	}
}