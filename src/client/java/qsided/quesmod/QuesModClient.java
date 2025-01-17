package qsided.quesmod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import qsided.quesmod.config.requirements.ItemWithRequirements;
import qsided.quesmod.gui.classes.ClassSelection;
import qsided.quesmod.gui.skills.*;
import qsided.quesmod.networking.LevelUpPayload;
import qsided.quesmod.networking.RequestSkillsPayload;
import qsided.quesmod.networking.SendSkillsExperiencePayload;
import qsided.quesmod.networking.SendSkillsLevelsPayload;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class QuesModClient implements ClientModInitializer {
	public static String lastScreenOpen = "mining";
	
	public static String getLastScreenOpen() {
		return lastScreenOpen;
	}
	
	public static void setLastScreenOpen(String lastScreenOpen) {
		QuesModClient.lastScreenOpen = lastScreenOpen;
	}
	
	@Override
	public void onInitializeClient() {
		
		MinecraftClient client = MinecraftClient.getInstance();
		
		KeyBinding openSkills = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.ques-mod.open_skills", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_ALT, "key.category.skills.open"));
		KeyBinding openClassSelection = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.ques-mod.open_class_selection", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "key.category.class_selection.open"));
		
		ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
			while (openSkills.wasPressed()) {
				ClientPlayNetworking.send(new RequestSkillsPayload(client.player.getUuid().toString()));
				switch (getLastScreenOpen()) {
                    case "mining" -> client.setScreen(new MiningSkillScreen());
					case "enchanting" -> client.setScreen(new EnchantingSkillScreen());
					case "combat" -> client.setScreen(new CombatSkillScreen());
					case "woodcutting" -> client.setScreen(new WoodcuttingSkillScreen());
                    case "endurance" -> client.setScreen(new EnduranceSkillScreen());
					case "agility" -> client.setScreen(new AgilitySkillScreen());
                    default -> client.setScreen(new MiningSkillScreen());
                }
			}
			while (openClassSelection.wasPressed()) {
				client.setScreen(new ClassSelection());
			}
		});
		
		ClientPlayNetworking.registerGlobalReceiver(LevelUpPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				ClientPlayNetworking.send(new RequestSkillsPayload(client.player.getUuid().toString()));
				context.player().sendMessage(Text.translatable("skills.level_up.ques-mod." + payload.skill()).append(String.valueOf(payload.level())), false);
			});
		});
		
		
		
		ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
			
			if (QuesMod.OWO_CONFIG.enableRequirements()) {
				ObjectMapper mapper = new ObjectMapper();
				CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, ItemWithRequirements.class);
				try {
					List<ItemWithRequirements> items = mapper.readValue(new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/reqs.json"), typeReference);
					items.forEach(item -> {
						if (stack.getItem().toString().equals(item.getItemId())) {
							lines
									.add(Text.translatable("tooltip.ques-mod.requirements")
											.append(Text.translatable("skills.ques-mod." + item.getRequirements().getSkill()))
											.append(" " + item.getRequirements().getLevel()).formatted(Formatting.GRAY));
						}
					});
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
        });
		
		ClientPlayNetworking.registerGlobalReceiver(SendSkillsLevelsPayload.ID, (payload, context) -> {
			MiningSkillScreen.setMiningLevel(payload.mining());
			EnchantingSkillScreen.setEnchantingLevel(payload.enchanting());
			CombatSkillScreen.setCombatLevel(payload.combat());
			WoodcuttingSkillScreen.setWoodcuttingLevel(payload.woodcutting());
			EnduranceSkillScreen.setEnduranceLevel(payload.endurance());
			EnduranceSkillScreen.setMaxHealth(context.player().getMaxHealth());
			AgilitySkillScreen.setAgilityLevel(payload.agility());
			AgilitySkillScreen.setJumpStrength(context.player().getAttributeInstance(EntityAttributes.JUMP_STRENGTH).getValue());
			AgilitySkillScreen.setSafeDistance(context.player().getAttributeInstance(EntityAttributes.SAFE_FALL_DISTANCE).getValue());
			});
		ClientPlayNetworking.registerGlobalReceiver(SendSkillsExperiencePayload.ID, (payload, context) -> {
			MiningSkillScreen.setMiningExperience(payload.mining());
			EnchantingSkillScreen.setEnchantingExperience(payload.enchanting());
			CombatSkillScreen.setCombatExperience(payload.combat());
			WoodcuttingSkillScreen.setWoodcuttingExperience(payload.woodcutting());
			EnduranceSkillScreen.setEnduranceExperience(payload.endurance());
			EnduranceSkillScreen.setMaxHealth(context.player().getMaxHealth());
			AgilitySkillScreen.setAgilityExperience(payload.agility());
			AgilitySkillScreen.setJumpStrength(context.player().getAttributeInstance(EntityAttributes.JUMP_STRENGTH).getValue());
			AgilitySkillScreen.setSafeDistance(context.player().getAttributeInstance(EntityAttributes.SAFE_FALL_DISTANCE).getValue());
			});
	}
	
	public MinecraftClient getClient() {
        return MinecraftClient.getInstance();
    }
}