package qsided.quesmod.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;
import qsided.quesmod.items.materials.MythrilMaterial;

public class QuesItems {
    public static final Item MYTHRIL = register(new Item(new Item.Settings().maxCount(64).fireproof()), "mythril");
    public static final MythrilMaterial MYTHRIL_INSTANCE = new MythrilMaterial();
    public static final Item MYTHRIL_SWORD = register(new SwordItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_sword");
    public static final Item MYTHRIL_PICKAXE = register(new PickaxeItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_pickaxe");
    public static final Item MYTHRIL_AXE = register(new AxeItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_axe");
    public static final Item MYTHRIL_SHOVEL = register(new ShovelItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_shovel");
    public static final Item MYTHRIL_HOE = register(new HoeItem(MYTHRIL_INSTANCE, new Item.Settings()), "mythril_hoe");
    public static Item register(Item item, String id) {
        Identifier itemId = Identifier.of(QuesMod.MOD_ID, id);
        
        Item registeredItem = Registry.register(Registries.ITEM, itemId, item);
        
        return registeredItem;
    }
    
    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> {
                    itemGroup.add(MYTHRIL);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> {
                    itemGroup.add(MYTHRIL_PICKAXE);
                    itemGroup.add(MYTHRIL_AXE);
                    itemGroup.add(MYTHRIL_SHOVEL);
                    itemGroup.add(MYTHRIL_HOE);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(itemGroup -> {
                    itemGroup.add(MYTHRIL_SWORD);
                });
    }
}
