package qsided.quesmod.items.materials;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import qsided.quesmod.items.QuesItems;

public class MythrilMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 2780;
    }
    
    @Override
    public float getMiningSpeedMultiplier() {
        return 10.5F;
    }
    
    @Override
    public float getAttackDamage() {
        return 6.0F;
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
        return Ingredient.ofItems(QuesItems.MYTHRIL_INGOT);
    }
}
