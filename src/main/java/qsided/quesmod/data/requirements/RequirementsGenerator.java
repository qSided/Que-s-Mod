package qsided.quesmod.data.requirements;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequirementsGenerator {
    
    public static void genConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ItemWithRequirements> items = new ArrayList<>();
        
        items.add(new ItemWithRequirements("minecraft:wooden_sword", new Requirements("combat", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_pickaxe", new Requirements("mining", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_axe", new Requirements("woodcutting", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_shovel", new Requirements("mining", 1)));
        items.add(new ItemWithRequirements("minecraft:wooden_hoe", new Requirements("farming", 1)));
        
        items.add(new ItemWithRequirements("minecraft:stone_sword", new Requirements("combat", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_pickaxe", new Requirements("mining", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_axe", new Requirements("woodcutting", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_shovel", new Requirements("mining", 4)));
        items.add(new ItemWithRequirements("minecraft:stone_hoe", new Requirements("farming", 4)));
        
        items.add(new ItemWithRequirements("minecraft:iron_sword", new Requirements("combat", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_pickaxe", new Requirements("mining", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_axe", new Requirements("woodcutting", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_shovel", new Requirements("mining", 12)));
        items.add(new ItemWithRequirements("minecraft:iron_hoe", new Requirements("farming", 12)));
        
        items.add(new ItemWithRequirements("minecraft:gold_sword", new Requirements("combat", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_pickaxe", new Requirements("mining", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_axe", new Requirements("woodcutting", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_shovel", new Requirements("mining", 15)));
        items.add(new ItemWithRequirements("minecraft:gold_hoe", new Requirements("farming", 15)));
        
        items.add(new ItemWithRequirements("minecraft:diamond_sword", new Requirements("combat", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_pickaxe", new Requirements("mining", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_axe", new Requirements("woodcutting", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_shovel", new Requirements("mining", 25)));
        items.add(new ItemWithRequirements("minecraft:diamond_hoe", new Requirements("farming", 25)));
        
        items.add(new ItemWithRequirements("minecraft:netherite_sword", new Requirements("combat", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_pickaxe", new Requirements("mining", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_axe", new Requirements("woodcutting", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_shovel", new Requirements("mining", 40)));
        items.add(new ItemWithRequirements("minecraft:netherite_hoe", new Requirements("farming", 40)));
        
        items.add(new ItemWithRequirements("ques-mod:mythril_sword", new Requirements("combat", 40)));
        items.add(new ItemWithRequirements("ques-mod:mythril_pickaxe", new Requirements("mining", 40)));
        items.add(new ItemWithRequirements("ques-mod:mythril_axe", new Requirements("woodcutting", 40)));
        items.add(new ItemWithRequirements("ques-mod:mythril_shovel", new Requirements("mining", 40)));
        items.add(new ItemWithRequirements("ques-mod:mythril_hoe", new Requirements("farming", 40)));
        
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        File file = new File(FabricLoader.getInstance().getConfigDir() + "/reqs.json");
        if (!file.exists() && !file.isDirectory()) {
            try {
                mapper.writeValue(new File(FabricLoader.getInstance().getConfigDir() + "/reqs.json"), items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
