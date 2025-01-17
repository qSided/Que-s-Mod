package qsided.quesmod.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import qsided.quesmod.RoleplayClasses;
import qsided.quesmod.config.experience_values.BlockExperience;
import qsided.quesmod.config.requirements.ItemWithRequirements;
import qsided.quesmod.config.requirements.Requirements;
import qsided.quesmod.config.roleplay_classes.RoleplayClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigGenerator {
    
    public static void genReqsConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ItemWithRequirements> items = new ArrayList<>();
        
        items.add(new ItemWithRequirements("minecraft:wooden_sword", new Requirements("combat", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_pickaxe", new Requirements("mining", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_axe", new Requirements("woodcutting", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_shovel", new Requirements("mining", 1)));
        //items.add(new ItemWithRequirements("minecraft:wooden_hoe", new Requirements("agility", 1)));
        items.add(new ItemWithRequirements("minecraft:leather_helmet", new Requirements("endurance", 1)));
        items.add(new ItemWithRequirements("minecraft:leather_chestplate", new Requirements("endurance", 1)));
        items.add(new ItemWithRequirements("minecraft:leather_leggings", new Requirements("endurance", 1)));
        items.add(new ItemWithRequirements("minecraft:leather_boots", new Requirements("endurance", 1)));
        
        items.add(new ItemWithRequirements("minecraft:stone_sword", new Requirements("combat", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_pickaxe", new Requirements("mining", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_axe", new Requirements("woodcutting", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_shovel", new Requirements("mining", 4)));
        //items.add(new ItemWithRequirements("minecraft:stone_hoe", new Requirements("agility", 4)));
        
        items.add(new ItemWithRequirements("minecraft:iron_sword", new Requirements("combat", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_pickaxe", new Requirements("mining", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_axe", new Requirements("woodcutting", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_shovel", new Requirements("mining", 12)));
        //items.add(new ItemWithRequirements("minecraft:iron_hoe", new Requirements("agility", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_helmet", new Requirements("endurance", 10)));
        items.add(new ItemWithRequirements("minecraft:iron_chestplate", new Requirements("endurance", 10)));
        items.add(new ItemWithRequirements("minecraft:iron_leggings", new Requirements("endurance", 10)));
        items.add(new ItemWithRequirements("minecraft:iron_boots", new Requirements("endurance", 10)));
        
        items.add(new ItemWithRequirements("minecraft:golden_sword", new Requirements("combat", 15)));
        items.add(new ItemWithRequirements("minecraft:golden_pickaxe", new Requirements("mining", 15)));
        items.add(new ItemWithRequirements("minecraft:golden_axe", new Requirements("woodcutting", 15)));
        items.add(new ItemWithRequirements("minecraft:golden_shovel", new Requirements("mining", 15)));
        //items.add(new ItemWithRequirements("minecraft:gold_hoe", new Requirements("agility", 15)));
        items.add(new ItemWithRequirements("minecraft:golden_helmet", new Requirements("endurance", 15)));
        items.add(new ItemWithRequirements("minecraft:golden_chestplate", new Requirements("endurance", 15)));
        items.add(new ItemWithRequirements("minecraft:golden_leggings", new Requirements("endurance", 15)));
        items.add(new ItemWithRequirements("minecraft:golden_boots", new Requirements("endurance", 15)));
        
        items.add(new ItemWithRequirements("minecraft:diamond_sword", new Requirements("combat", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_pickaxe", new Requirements("mining", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_axe", new Requirements("woodcutting", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_shovel", new Requirements("mining", 25)));
        //items.add(new ItemWithRequirements("minecraft:diamond_hoe", new Requirements("agility", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_helmet", new Requirements("endurance", 20)));
        items.add(new ItemWithRequirements("minecraft:diamond_chestplate", new Requirements("endurance", 20)));
        items.add(new ItemWithRequirements("minecraft:diamond_leggings", new Requirements("endurance", 20)));
        items.add(new ItemWithRequirements("minecraft:diamond_boots", new Requirements("endurance", 20)));
        
        items.add(new ItemWithRequirements("minecraft:netherite_sword", new Requirements("combat", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_pickaxe", new Requirements("mining", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_axe", new Requirements("woodcutting", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_shovel", new Requirements("mining", 40)));
        //items.add(new ItemWithRequirements("minecraft:netherite_hoe", new Requirements("agility", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_helmet", new Requirements("endurance", 30)));
        items.add(new ItemWithRequirements("minecraft:netherite_chestplate", new Requirements("endurance", 30)));
        items.add(new ItemWithRequirements("minecraft:netherite_leggings", new Requirements("endurance", 30)));
        items.add(new ItemWithRequirements("minecraft:netherite_boots", new Requirements("endurance", 30)));
        
        items.add(new ItemWithRequirements("ques-mod:mythril_sword", new Requirements("combat", 50)));
        items.add(new ItemWithRequirements("ques-mod:mythril_pickaxe", new Requirements("mining", 50)));
        items.add(new ItemWithRequirements("ques-mod:mythril_axe", new Requirements("woodcutting", 50)));
        items.add(new ItemWithRequirements("ques-mod:mythril_shovel", new Requirements("mining", 50)));
        //items.add(new ItemWithRequirements("ques-mod:mythril_hoe", new Requirements("agility", 40)));
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
                                "woodcutting", 20F
                               )));
            }
        });
        
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        File dir = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/skills");
        File reqs = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/skills/woodcutting.json");
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
    
    public static void genDefaultClasses() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<Integer, RoleplayClass> classes = new HashMap<>();
        
        classes.put(0, RoleplayClasses.PALADIN);
        classes.put(1, RoleplayClasses.RANGER);
        classes.put(2, RoleplayClasses.FIGHTER);
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        File dir = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/classes");
        File rpClasses = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/classes/classes.json");
        if (!rpClasses.exists() && !rpClasses.isDirectory()) {
            try {
                dir.mkdirs();
                mapper.writeValue(rpClasses, classes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public static void genPassiveMobs() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> mobs = new ArrayList<>();
        
        mobs.add("sheep");
        mobs.add("cow");
        mobs.add("chicken");
        mobs.add("mooshroom");
        mobs.add("squid");
        mobs.add("glow_squid");
        mobs.add("pig");
        mobs.add("rabbit");
        mobs.add("agent");
        mobs.add("allay");
        mobs.add("armadillo");
        mobs.add("axolotl");
        mobs.add("bat");
        mobs.add("cat");
        mobs.add("camel");
        mobs.add("cod");
        mobs.add("donkey");
        mobs.add("horse");
        mobs.add("frog");
        mobs.add("mule");
        mobs.add("ocelot");
        mobs.add("parrot");
        mobs.add("pufferfish");
        mobs.add("salmon");
        mobs.add("skeleton_horse");
        mobs.add("sniffer");
        mobs.add("strider");
        mobs.add("tadpole");
        mobs.add("tropical_fish");
        mobs.add("turtle");
        mobs.add("villager");
        mobs.add("wandering_trader");
        mobs.add("zombie_horse");
        mobs.add("dolphin");
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        File dir = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod");
        File passiveMobs = new File(FabricLoader.getInstance().getConfigDir() + "/ques-mod/passive_mobs.json");
        if (!passiveMobs.exists() && !passiveMobs.isDirectory()) {
            try {
                dir.mkdirs();
                mapper.writeValue(passiveMobs, mobs);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
