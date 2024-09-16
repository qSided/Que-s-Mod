package qsided.quesmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import qsided.quesmod.gui.CombatSkillScreen;
import qsided.quesmod.gui.EnchantingSkillScreen;
import qsided.quesmod.gui.MiningSkillScreen;
import qsided.quesmod.networking.LevelUpPayload;
import qsided.quesmod.networking.RequestSkillsPayload;
import qsided.quesmod.networking.SendSkillsExperiencePayload;
import qsided.quesmod.networking.SendSkillsLevelsPayload;

public class QuesModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MinecraftClient client = MinecraftClient.getInstance();
		
		KeyBinding openSkills = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.ques-mod.open_skills", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_ALT, "key.category.skills.open"));
		
		ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
			while (openSkills.wasPressed()) {
				ClientPlayNetworking.send(new RequestSkillsPayload(client.player.getUuid().toString()));
				client.setScreen(new MiningSkillScreen());
			}
		});
		
		ClientPlayNetworking.registerGlobalReceiver(LevelUpPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				ClientPlayNetworking.send(new RequestSkillsPayload(client.player.getUuid().toString()));
				context.player().sendMessage(Text.translatable("skills.level_up.ques-mod." + payload.skill()).append(String.valueOf(payload.level())));
			});
		});
		
		ClientPlayNetworking.registerGlobalReceiver(SendSkillsLevelsPayload.ID, (payload, context) -> {
			MiningSkillScreen.setMiningLevel(payload.mining());
			EnchantingSkillScreen.setEnchantingLevel(payload.enchanting());
			CombatSkillScreen.setCombatLevel(payload.combat());
		});
		ClientPlayNetworking.registerGlobalReceiver(SendSkillsExperiencePayload.ID, (payload, context) -> {
			MiningSkillScreen.setMiningExperience(payload.mining());
			EnchantingSkillScreen.setEnchantingExperience(payload.enchanting());
			CombatSkillScreen.setCombatExperience(payload.combat());
		});
	}
}