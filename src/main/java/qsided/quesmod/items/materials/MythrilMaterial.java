package qsided.quesmod.items.materials;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class MythrilMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 1850;
    }
    
    @Override
    public float getMiningSpeedMultiplier() {
        return 10.5F;
    }
    
    @Override
    public float getAttackDamage() {
        return 7.0F;
    }
    
    @Override
    public TagKey<Block> getInverseTag() {
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }
    
    @Override
    public int getEnchantability() {
        return 18;
    }
    
    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}
