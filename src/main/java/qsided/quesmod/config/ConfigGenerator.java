package qsided.quesmod.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import qsided.quesmod.config.experience_values.BlockExperience;
import qsided.quesmod.config.requirements.ItemWithRequirements;
import qsided.quesmod.config.requirements.Requirements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigGenerator {
    
    public static void genReqsConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ItemWithRequirements> items = new ArrayList<>();
        
        items.add(new ItemWithRequirements("minecraft:wooden_sword", new Requirements("combat", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_pickaxe", new Requirements("mining", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_axe", new Requirements("mining", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_shovel", new Requirements("mining", 1)));
        //items.add(new ItemWithRequirements("minecraft:wooden_hoe", new Requirements("farming", 1)));
        items.add(new ItemWithRequirements("minecraft:leather_helmet", new Requirements("endurance", 1)));
        items.add(new ItemWithRequirements("minecraft:leather_chestplate", new Requirements("endurance", 1)));
        items.add(new ItemWithRequirements("minecraft:leather_leggings", new Requirements("endurance", 1)));
        items.add(new ItemWithRequirements("minecraft:leather_boots", new Requirements("endurance", 1)));
        
        items.add(new ItemWithRequirements("minecraft:stone_sword", new Requirements("combat", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_pickaxe", new Requirements("mining", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_axe", new Requirements("mining", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_shovel", new Requirements("mining", 4)));
        //items.add(new ItemWithRequirements("minecraft:stone_hoe", new Requirements("farming", 4)));
        
        items.add(new ItemWithRequirements("minecraft:iron_sword", new Requirements("combat", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_pickaxe", new Requirements("mining", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_axe", new Requirements("mining", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_shovel", new Requirements("mining", 12)));
        //items.add(new ItemWithRequirements("minecraft:iron_hoe", new Requirements("farming", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_helmet", new Requirements("endurance", 10)));
        items.add(new ItemWithRequirements("minecraft:iron_chestplate", new Requirements("endurance", 10)));
        items.add(new ItemWithRequirements("minecraft:iron_leggings", new Requirements("endurance", 10)));
        items.add(new ItemWithRequirements("minecraft:iron_boots", new Requirements("endurance", 10)));
        
        items.add(new ItemWithRequirements("minecraft:gold_sword", new Requirements("combat", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_pickaxe", new Requirements("mining", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_axe", new Requirements("mining", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_shovel", new Requirements("mining", 15)));
        //items.add(new ItemWithRequirements("minecraft:gold_hoe", new Requirements("farming", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_helmet", new Requirements("endurance", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_chestplate", new Requirements("endurance", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_leggings", new Requirements("endurance", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_boots", new Requirements("endurance", 15)));
        
        items.add(new ItemWithRequirements("minecraft:diamond_sword", new Requirements("combat", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_pickaxe", new Requirements("mining", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_axe", new Requirements("mining", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_shovel", new Requirements("mining", 25)));
        //items.add(new ItemWithRequirements("minecraft:diamond_hoe", new Requirements("farming", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_helmet", new Requirements("endurance", 20)));
        items.add(new ItemWithRequirements("minecraft:diamond_chestplate", new Requirements("endurance", 20)));
        items.add(new ItemWithRequirements("minecraft:diamond_leggings", new Requirements("endurance", 20)));
        items.add(new ItemWithRequirements("minecraft:diamond_boots", new Requirements("endurance", 20)));
        
        items.add(new ItemWithRequirements("minecraft:netherite_sword", new Requirements("combat", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_pickaxe", new Requirements("mining", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_axe", new Requirements("mining", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_shovel", new Requirements("mining", 40)));
        //items.add(new ItemWithRequirements("minecraft:netherite_hoe", new Requirements("farming", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_helmet", new Requirements("endurance", 30)));
        items.add(new ItemWithRequirements("minecraft:netherite_chestplate", new Requirements("endurance", 30)));
        items.add(new ItemWithRequirements("minecraft:netherite_leggings", new Requirements("endurance", 30)));
        items.add(new ItemWithRequirements("minecraft:netherite_boots", new Requirements("endurance", 30)));
        
        items.add(new ItemWithRequirements("ques-mod:mythril_sword", new Requirements("combat", 50)));
        items.add(new ItemWithRequirements("ques-mod:mythril_pickaxe", new Requirements("mining", 50)));
        items.add(new ItemWithRequirements("ques-mod:mythril_axe", new Requirements("mining", 50)));
        items.add(new ItemWithRequirements("ques-mod:mythril_shovel", new Requirements("mining", 50)));
        //items.add(new ItemWithRequirements("ques-mod:mythril_hoe", new Requirements("farming", 40)));
        items.add(new ItemWithRequirements("ques-mod:mythril_helmet", new Requirements("endurance", 40)));
        items.add(new ItemWithRequirements("ques-mod:mythril_chestplate", new Requirements("endurance", 40)));
        items.add(new ItemWithRequirements("ques-mod:mythril_leggings", new Requirements("endurance", 40)));
        items.add(new ItemWithRequirements("ques-mod:mythril_boots", new Requirements("endurance", 40)));
        
        
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        File dir = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod");
        File reqs = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/reqs.json");
        if (!reqs.exists() && !reqs.isDirectory()) {
            try {
                dir.mkdirs();
                mapper.writeValue(reqs, items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public static void genWoodcuttingConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<BlockExperience> woodcutting = new ArrayList<>();
        
        Registries.BLOCK.forEach(block -> {
            System.out.println(block.getDefaultState().toString());
            if (block.asItem().toString().contains("log") ||
                    block.asItem().toString().contains("wood") ||
                    block.asItem().toString().contains("roots") ||
                    block.asItem().toString().contains("bamboo")) {
                woodcutting.add(new BlockExperience(block.asItem().toString(),
                        Map.of(
                                "mining", 20F
                               )));
            }
        });
        
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        File dir = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/skills");
        File reqs = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/skills/mining.json");
        if (!reqs.exists() && !reqs.isDirectory()) {
            try {
                dir.mkdirs();
                mapper.writeValue(reqs, woodcutting);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        
    }
    
    public static void genMiningConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<BlockExperience> mining = new ArrayList<>();
        
        Registries.BLOCK.forEach(block -> {
            System.out.println(block.getDefaultState().toString());
            if (block.asItem().toString().contains("stone") ||
                    block.asItem().toString().contains("andesite") ||
                    block.asItem().toString().contains("diorite") ||
                    block.asItem().toString().contains("granite") ||
                    block.asItem().toString().contains("slate") ||
                    block.asItem().toString().contains("dirt") ||
                    block.asItem().toString().contains("grass")) {
                mining.add(new BlockExperience(block.asItem().toString(),
                        Map.of(
                                "mining", 5 + (block.getHardness() / 3))));
            } else if (
                    block.asItem().toString().contains("ore") ||
                            block.asItem().toString().contains("debris") ||
                            block.asItem().toString().contains("mythril")) {
                mining.add(new BlockExperience(block.asItem().toString(),
                        Map.of(
                                "mining", 9 + (block.getHardness() / 3))));
            }
        });
        
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        File dir = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/skills");
        File reqs = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/skills/mining.json");
        if (!reqs.exists() && !reqs.isDirectory()) {
            try {
                dir.mkdirs();
                mapper.writeValue(reqs, mining);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
