package qsided.quesmod.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

public class QuesItems {
    public static final Item MYTHRIL = register(new Item(new Item.Settings().maxCount(64).fireproof()), "mythril");
    public static Item register(Item item, String id) {
        Identifier itemId = Identifier.of(QuesMod.MOD_ID, id);
        
        Item registeredItem = Registry.register(Registries.ITEM, itemId, item);
        
        return registeredItem;
    }
    
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(QuesItems.MYTHRIL));
    }
}
