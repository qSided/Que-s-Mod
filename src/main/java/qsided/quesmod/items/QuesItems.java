package qsided.quesmod.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;
import qsided.quesmod.blocks.QuesBlocks;
import qsided.quesmod.items.materials.MythrilMaterial;
import qsided.quesmod.items.materials.QuesArmorMaterials;

import java.util.List;

public class QuesItems {
    public static final RegistryKey<ItemGroup> QUES_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(QuesMod.MOD_ID, "ques_items"));
    public static final ItemGroup QUES_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(QuesItems.MYTHRIL_SWORD))
            .displayName(Text.translatable("itemGroup.ques_items"))
            .build();
    public static final Item MYTHRIL_FRAGMENT = register(new Item(new Item.Settings().maxCount(64).fireproof()), "mythril_fragment");
    public static final Item MYTHRIL_INGOT = register(new Item(new Item.Settings().maxCount(64).fireproof()), "mythril_ingot");
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
    public static final Identifier EMPTY_SLOT_INGOT_TEXTURE = Identifier.ofVanilla("item/empty_slot_ingot");
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
                                    EMPTY_SLOT_INGOT_TEXTURE)),
                        "mythril_upgrade_template");
    public static Item register(Item item, String id) {
        Identifier itemId = Identifier.of(QuesMod.MOD_ID, id);
        
        return Registry.register(Registries.ITEM, itemId, item);
    }
    
    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, QUES_ITEM_GROUP_KEY, QUES_ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(QUES_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(MYTHRIL_FRAGMENT);
            itemGroup.add(MYTHRIL_INGOT);
            itemGroup.add(QuesBlocks.MYTHRIL_CHUNK.asItem());
            itemGroup.add(MYTHRIL_UPGRADE_TEMPLATE);
            itemGroup.add(MYTHRIL_SWORD);
            itemGroup.add(MYTHRIL_PICKAXE);
            itemGroup.add(MYTHRIL_AXE);
            itemGroup.add(MYTHRIL_SHOVEL);
            itemGroup.add(MYTHRIL_HOE);
            itemGroup.add(MYTHRIL_HELMET);
            itemGroup.add(MYTHRIL_CHESTPLATE);
            itemGroup.add(MYTHRIL_LEGGINGS);
            itemGroup.add(MYTHRIL_BOOTS);
        });
    }
}
