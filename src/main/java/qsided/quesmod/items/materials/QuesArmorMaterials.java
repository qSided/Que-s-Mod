package qsided.quesmod.items.materials;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class QuesArmorMaterials {
    
    public static void initialize() {}
    public static final int MYTHRIL_DURABILITY_MULTIPLIER = 12;
    
    public static final RegistryEntry<ArmorMaterial> MYTHRIL = registerMaterial(
            "mythril",
            //Piece and toughness value
            Map.of(
                    ArmorItem.Type.HELMET, 2,
                    ArmorItem.Type.CHESTPLATE, 5,
                    ArmorItem.Type.LEGGINGS, 4,
                    ArmorItem.Type.BOOTS, 3),
            5,
            //Equip sound
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
            null,
            0.0F,
            0.0F,
            false
            );
    
    public static RegistryEntry<ArmorMaterial> registerMaterial(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, float toughness, float kbResistance, boolean dyeable) {
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(Identifier.of(QuesMod.MOD_ID, id), "", dyeable)
        );
        
        ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredient, layers, toughness, kbResistance);
        
        material = Registry.register(Registries.ARMOR_MATERIAL, Identifier.of(QuesMod.MOD_ID, id), material);
        
        return RegistryEntry.of(material);
    }
}
