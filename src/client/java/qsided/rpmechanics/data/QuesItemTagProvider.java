package qsided.rpmechanics.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import qsided.rpmechanics.items.QuesItems;

import java.util.concurrent.CompletableFuture;

public class QuesItemTagProvider extends FabricTagProvider<Item> {
    
    public QuesItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }
    
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(QuesItems.MYTHRIL_HELMET)
                .add(QuesItems.MYTHRIL_CHESTPLATE)
                .add(QuesItems.MYTHRIL_LEGGINGS)
                .add(QuesItems.MYTHRIL_BOOTS)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(QuesItems.MYTHRIL_SWORD)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(QuesItems.MYTHRIL_PICKAXE)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(QuesItems.MYTHRIL_AXE)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(QuesItems.MYTHRIL_SHOVEL)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.HOES)
                .add(QuesItems.MYTHRIL_HOE)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(QuesItems.MYTHRIL_HELMET)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(QuesItems.MYTHRIL_CHESTPLATE)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(QuesItems.MYTHRIL_LEGGINGS)
                .setReplace(false);
        getOrCreateTagBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(QuesItems.MYTHRIL_BOOTS)
                .setReplace(false);
    }
}
