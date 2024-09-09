package qsided.quesmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import qsided.quesmod.gui.DataSkillsScreen;
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
				client.setScreen(new DataSkillsScreen());
			}
		});
		
		ClientPlayNetworking.registerGlobalReceiver(LevelUpPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				ClientPlayNetworking.send(new RequestSkillsPayload(client.player.getUuid().toString()));
				context.player().sendMessage(Text.of("§8You are now level §e§l" + payload.level() + "§r §8in §9§l" + payload.skill()));
			});
		});
		
		ClientPlayNetworking.registerGlobalReceiver(SendSkillsLevelsPayload.ID, (payload, context) -> {
			DataSkillsScreen.setSkillsLevels(payload.mining(), payload.enchanting(), payload.combat(), payload.alchemy());
		});
		ClientPlayNetworking.registerGlobalReceiver(SendSkillsExperiencePayload.ID, (payload, context) -> {
			DataSkillsScreen.setSkillsExperience(payload.mining(), payload.enchanting(), payload.combat(), payload.alchemy());
		});
	}
}