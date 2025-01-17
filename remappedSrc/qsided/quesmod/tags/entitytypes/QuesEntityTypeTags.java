package qsided.quesmod.tags.entitytypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

public class QuesEntityTypeTags {
    public static final TagKey<EntityType<?>> NON_HOSTILE = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(QuesMod.MOD_ID, "non_hostile"));
    public static final TagKey<EntityType<?>> HOSTILE = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(QuesMod.MOD_ID, "hostile"));
}
