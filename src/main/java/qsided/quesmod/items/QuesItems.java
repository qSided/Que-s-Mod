package qsided.quesmod.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;
import qsided.quesmod.items.materials.MythrilMaterial;
import qsided.quesmod.items.materials.QuesArmorMaterials;

import java.util.List;

public class QuesItems {
    public static final Item MYTHRIL = register(new Item(new Item.Settings().maxCount(64).fireproof()), "mythril");
    public static final MythrilMaterial MYTHRIL_INSTANCE = new MythrilMaterial();
    public static final Item MYTHRIL_SWORD = register(new SwordItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_sword");
    public static final Item MYTHRIL_PICKAXE = register(new PickaxeItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_pickaxe");
    public static final Item MYTHRIL_AXE = register(new AxeItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_axe");
    public static final Item MYTHRIL_SHOVEL = register(new ShovelItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_shovel");
    public static final Item MYTHRIL_HOE = register(new HoeItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_hoe");
    public static final Item MYTHRIL_HELMET = register(new ArmorItem(QuesArmorMaterials.MYTHRIL, ArmorItem.Type.HELMET,
            new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(QuesArmorMaterials.MYTHRIL_DURABILITY_MULTIPLIER))), "mythril_helmet");
    public static final Item MYTHRIL_CHESTPLATE = register(new ArmorItem(QuesArmorMaterials.MYTHRIL, ArmorItem.Type.CHESTPLATE,
            new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(QuesArmorMaterials.MYTHRIL_DURABILITY_MULTIPLIER))), "mythril_chestplate");
    public static final Item MYTHRIL_LEGGINGS = register(new ArmorItem(QuesArmorMaterials.MYTHRIL, ArmorItem.Type.LEGGINGS,
            new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(QuesArmorMaterials.MYTHRIL_DURABILITY_MULTIPLIER))), "mythril_leggings");
    public static final Item MYTHRIL_BOOTS = register(new ArmorItem(QuesArmorMaterials.MYTHRIL, ArmorItem.Type.BOOTS,
            new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(QuesArmorMaterials.MYTHRIL_DURABILITY_MULTIPLIER))), "mythril_boots");
    public static final Identifier EMPTY_ARMOR_SLOT_MYTHRIL_TEXTURE = Identifier.of(QuesMod.MOD_ID,"item/empty_slot_mythril");
    public static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_helmet");
    public static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_chestplate");
    public static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_leggings");
    public static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = Identifier.ofVanilla("item/empty_armor_slot_boots");
    public static final Identifier EMPTY_SLOT_HOE_TEXTURE = Identifier.ofVanilla("item/empty_slot_hoe");
    public static final Identifier EMPTY_SLOT_AXE_TEXTURE = Identifier.ofVanilla("item/empty_slot_axe");
    public static final Identifier EMPTY_SLOT_SWORD_TEXTURE = Identifier.ofVanilla("item/empty_slot_sword");
    public static final Identifier EMPTY_SLOT_SHOVEL_TEXTURE = Identifier.ofVanilla("item/empty_slot_shovel");
    public static final Identifier EMPTY_SLOT_PICKAXE_TEXTURE = Identifier.ofVanilla("item/empty_slot_pickaxe");
    public static final Item MYTHRIL_UPGRADE_TEMPLATE = register(new SmithingTemplateItem(
                            Text.translatable("item.ques-mod.mythril_template_apply").formatted(Formatting.BLUE),
                            Text.translatable("item.ques-mod.mythril_template_ingredient").formatted(Formatting.BLUE),
                            Text.translatable("item.ques-mod.mythril_template_title").formatted(Formatting.GRAY),
                            Text.translatable("item.ques-mod.mythril_template_base_description"),
                            Text.translatable("item.ques-mod.mythril_template_addition_description"),
                            List.of(
                                    EMPTY_ARMOR_SLOT_HELMET_TEXTURE,
                                    EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE,
                                    EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE,
                                    EMPTY_ARMOR_SLOT_BOOTS_TEXTURE,
                                    EMPTY_SLOT_HOE_TEXTURE,
                                    EMPTY_SLOT_AXE_TEXTURE,
                                    EMPTY_SLOT_SWORD_TEXTURE,
                                    EMPTY_SLOT_SHOVEL_TEXTURE,
                                    EMPTY_SLOT_PICKAXE_TEXTURE),
                            List.of(
                                    EMPTY_ARMOR_SLOT_MYTHRIL_TEXTURE)),
                        "mythril_upgrade_template");
    public static Item register(Item item, String id) {
        Identifier itemId = Identifier.of(QuesMod.MOD_ID, id);
        
        Item registeredItem = Registry.register(Registries.ITEM, itemId, item);
        
        return registeredItem;
    }
    
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> {
                    itemGroup.add(MYTHRIL);
                    itemGroup.add(MYTHRIL_UPGRADE_TEMPLATE);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> {
                    itemGroup.add(MYTHRIL_PICKAXE);
                    itemGroup.add(MYTHRIL_AXE);
                    itemGroup.add(MYTHRIL_SHOVEL);
                    itemGroup.add(MYTHRIL_HOE);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(itemGroup -> itemGroup.add(MYTHRIL_SWORD));
    }
}
