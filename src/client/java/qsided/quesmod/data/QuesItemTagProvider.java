package qsided.quesmod.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import qsided.quesmod.items.QuesItems;

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
    }
}
