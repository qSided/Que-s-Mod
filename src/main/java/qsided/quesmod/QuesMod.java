package qsided.quesmod;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
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
import qsided.quesmod.config.ConfigGenerator;
import qsided.quesmod.events.IncreaseSkillExperienceCallback;
import qsided.quesmod.events.IncreaseSkillLevelCallback;
import qsided.quesmod.items.QuesItems;
import qsided.quesmod.items.materials.QuesArmorMaterials;
import qsided.quesmod.networking.*;
import qsided.quesmod.skills.CombatSkill;
import qsided.quesmod.skills.EnchantingSkill;
import qsided.quesmod.skills.LevelRequirement;
import qsided.quesmod.skills.MiningSkill;

import java.io.IOException;

import static net.minecraft.server.command.CommandManager.*;

public class QuesMod implements ModInitializer {
 
	public static final Logger LOGGER = LoggerFactory.getLogger("ques-mod");
	public static final String MOD_ID = "ques-mod";
	public static final RegistryKey<PlacedFeature> MYTHRIL_CHUNK_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "mythril_chunk_feature"));
	
	@Override
	public void onInitialize() {
        QuesItems.initialize();
        QuesBlocks.initialize();
        QuesArmorMaterials.initialize();
        MiningSkill.register();
        EnchantingSkill.register();
        CombatSkill.register();
        try {
            ConfigGenerator.genReqsConfig();
            ConfigGenerator.genWoodcuttingConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LevelRequirement.register();
        
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, MYTHRIL_CHUNK_FEATURE);
        
        PayloadTypeRegistry.playS2C().register(LevelUpPayload.ID, LevelUpPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(RequestSkillsPayload.ID, RequestSkillsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSkillsLevelsPayload.ID, SendSkillsLevelsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSkillsExperiencePayload.ID, SendSkillsExperiencePayload.CODEC);
        
        ServerPlayNetworking.registerGlobalReceiver(RequestSkillsPayload.ID, (payload, context) -> {
            PlayerData playerState = StateSaverAndLoader.getPlayerState(context.player());
            
            sendSkillData(playerState, context.player());
        });
        
        IncreaseSkillExperienceCallback.EVENT.register((player, state, skill, value) -> {
            if (state.skillLevels.getOrDefault(skill, 1) < 100) {
                state.skillExperience.put(skill, value);
            }
            player.sendMessage(Text.of("text"), true);
            if (state.skillExperience.getOrDefault(skill, 0F) >= 120 * (state.skillLevels.getOrDefault(skill, 1) * 3.4)) {
                IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, 1);
            }
            
            sendSkillData(state, player);
            return ActionResult.PASS;
        });
        
        IncreaseSkillLevelCallback.EVENT.register((player, state, skill, value) -> {
            
            state.skillExperience.put(skill, (float) Math.max((state.skillExperience.getOrDefault(skill, 0F) - (120 * (state.skillLevels.getOrDefault(skill, 1) * 3.4))), 0));
            state.skillLevels.put(skill, state.skillLevels.getOrDefault(skill, 1) + value);
            ServerPlayNetworking.send(player, new LevelUpPayload(skill, state.skillLevels.get(skill)));
            
            if (skill.equals("mining")) {
                Identifier miningModifier = Identifier.of(QuesMod.MOD_ID, "mining_modifier");
                
                player.getAttributeInstance(EntityAttributes.PLAYER_MINING_EFFICIENCY).overwritePersistentModifier(
                        new EntityAttributeModifier(miningModifier, state.skillLevels.getOrDefault(skill, 1) * .5, EntityAttributeModifier.Operation.ADD_VALUE));
                
                if (state.skillLevels.getOrDefault(skill, 1) == 33) {
                    player.getAttributeInstance(EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED).overwritePersistentModifier(
                            new EntityAttributeModifier(Identifier.of(QuesMod.MOD_ID, "mining_one"), 10, EntityAttributeModifier.Operation.ADD_VALUE)
                    );
                }
            }
            
            if (skill.equals("combat")) {
                Identifier combatModifier = Identifier.of(QuesMod.MOD_ID, "combat_modifier");
                
                player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).overwritePersistentModifier(
                        new EntityAttributeModifier(combatModifier, state.skillLevels.getOrDefault(skill, 1) * .18, EntityAttributeModifier.Operation.ADD_VALUE)
                );
                player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED).overwritePersistentModifier(
                        new EntityAttributeModifier(combatModifier, state.skillLevels.getOrDefault(skill, 1) * .03, EntityAttributeModifier.Operation.ADD_VALUE)
                );
            }
            
            sendSkillData(state, player);
            return ActionResult.PASS;
        });
        
        
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerData state = getPlayerState(handler.getPlayer());
            
            handler.getPlayer().sendMessage(Text.translatable("ques-mod.player_joined"));
            
            sendSkillData(state, handler.getPlayer());
        });
        
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("skills")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(literal("setLevel")
                            .then(argument("skill", StringArgumentType.string())
                                    .then(argument("value", IntegerArgumentType.integer())
                                            .executes(context -> {
                                                final String skill = StringArgumentType.getString(context, "skill");
                                                final int value = IntegerArgumentType.getInteger(context, "value");
                                                final ServerPlayerEntity player = context.getSource().getPlayer();
                                                PlayerData state = StateSaverAndLoader.getPlayerState(player);
                                                IncreaseSkillLevelCallback.EVENT.invoker().increaseLevel(player, state, skill, value - state.skillLevels.getOrDefault(skill, 1));
                                                return 1;
                                            })
                                    )))
                    .then(literal("setExperience")
                            .then(argument("skill", StringArgumentType.string())
                                    .then(argument("value", FloatArgumentType.floatArg())
                                            .executes(context -> {
                                                final String skill = StringArgumentType.getString(context, "skill");
                                                final float value = FloatArgumentType.getFloat(context, "value");
                                                final ServerPlayerEntity player = context.getSource().getPlayer();
                                                PlayerData state = StateSaverAndLoader.getPlayerState(player);
                                                IncreaseSkillExperienceCallback.EVENT.invoker().increaseExp(player, state, skill, value);
                                                return 1;
                                            })))));
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
		Integer alchemyLevel = playerState.skillLevels.getOrDefault("alchemy", 1);
		
		Float miningExp = playerState.skillExperience.getOrDefault("mining", 0F);
		Float enchantingExp = playerState.skillExperience.getOrDefault("enchanting", 0F);
		Float combatExp = playerState.skillExperience.getOrDefault("combat", 0F);
		Float alchemyExp = playerState.skillExperience.getOrDefault("alchemy", 0F);
		
		ServerPlayNetworking.send(player, new SendSkillsLevelsPayload(miningLevel, enchantingLevel, combatLevel, alchemyLevel));
		ServerPlayNetworking.send(player, new SendSkillsExperiencePayload(miningExp, enchantingExp, combatExp, alchemyExp));
	}
}