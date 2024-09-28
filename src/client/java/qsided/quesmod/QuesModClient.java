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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import qsided.quesmod.config.requirements.ItemWithRequirements;
import qsided.quesmod.gui.CombatSkillScreen;
import qsided.quesmod.gui.EnchantingSkillScreen;
import qsided.quesmod.gui.MiningSkillScreen;
import qsided.quesmod.networking.LevelUpPayload;
import qsided.quesmod.networking.RequestSkillsPayload;
import qsided.quesmod.networking.SendSkillsExperiencePayload;
import qsided.quesmod.networking.SendSkillsLevelsPayload;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
		
		
		
		ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
			
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