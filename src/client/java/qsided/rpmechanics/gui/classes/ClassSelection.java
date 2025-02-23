package qsided.rpmechanics.gui.classes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.DropdownComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import qsided.rpmechanics.config.roleplay_classes.RoleplayClass;
import qsided.rpmechanics.networking.SendClassSelectedPayload;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ClassSelection extends BaseOwoScreen<FlowLayout> {
    Integer rpClassId = 0;
    Integer itemPosX;
    Integer itemPosY;
    Integer levelBonusPosX;
    Integer levelBonusPosY;
    
    public Integer getBonusModifierPosX() {
        return bonusModifierPosX;
    }
    
    public void setBonusModifierPosX(Integer bonusModifierPosX) {
        this.bonusModifierPosX = bonusModifierPosX;
    }
    
    public Integer getBonusModifierPosY() {
        return bonusModifierPosY;
    }
    
    public void setBonusModifierPosY(Integer bonusModifierPosY) {
        this.bonusModifierPosY = bonusModifierPosY;
    }
    
    Integer bonusModifierPosX;
    Integer bonusModifierPosY;
    Integer maxWidthWithGUIScaleOffset;
    
    public Integer getMaxWidthWithGUIScaleOffset() {
        return maxWidthWithGUIScaleOffset;
    }
    
    public void setMaxWidthWithGUIScaleOffset(Integer maxWidthWithGUIScaleOffset) {
        this.maxWidthWithGUIScaleOffset = maxWidthWithGUIScaleOffset;
    }
    
    public Integer getRpClassId() {
        return rpClassId;
    }
    
    public void setRpClassId(Integer rpClass) {
        this.rpClassId = rpClass;
    }
    
    public Integer getItemPosX() {
        return itemPosX;
    }
    
    public void setItemPosX(Integer itemPosX) {
        this.itemPosX = itemPosX;
    }
    
    public Integer getItemPosY() {
        return itemPosY;
    }
    
    public void setItemPosY(Integer itemPosY) {
        this.itemPosY = itemPosY;
    }
    
    public Integer getLevelBonusPosX() {
        return levelBonusPosX;
    }
    
    public void setLevelBonusPosX(Integer levelBonusPosX) {
        this.levelBonusPosX = levelBonusPosX;
    }
    
    public Integer getLevelBonusPosY() {
        return levelBonusPosY;
    }
    
    public void setLevelBonusPosY(Integer levelBonusPosY) {
        this.levelBonusPosY = levelBonusPosY;
    }
    
    @Override
    protected @NotNull OwoUIAdapter createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        
        System.out.println("GUI Scale is currently: " + MinecraftClient.getInstance().options.getGuiScale().getValue());
        
        switch (MinecraftClient.getInstance().options.getGuiScale().getValue()) {
            case 1 -> setMaxWidthWithGUIScaleOffset(1100);
            case 2 -> setMaxWidthWithGUIScaleOffset(585);
            case 3 -> setMaxWidthWithGUIScaleOffset(380);
            default -> setMaxWidthWithGUIScaleOffset(270);
        }
        
        rootComponent.surface(Surface.tiled(Identifier.of("minecraft","textures/block/daylight_detector_side.png"), 16, 16));
        
        setItemPosX(0);
        setItemPosY(12);
        setLevelBonusPosX(3);
        setLevelBonusPosY(12);
        setBonusModifierPosX(3);
        setBonusModifierPosY(12);
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<Integer, RoleplayClass> rpClasses = mapper.readValue(new File(FabricLoader.getInstance().getConfigDir() + "/rpmechanics/classes/classes.json"), new TypeReference<Map<Integer, RoleplayClass>>() {});
            
            rootComponent.child(Components.dropdown(Sizing.content()).id("classes"));
            
            rootComponent.child(Containers.verticalScroll(Sizing.content(), Sizing.fixed(60),
                            rootComponent.childById(DropdownComponent.class, "classes")
                                    .surface(Surface.flat(Color.decode("#171717").getRGB()).and(Surface.outline(Color.decode("#121212").getRGB())))
                                    .horizontalAlignment(HorizontalAlignment.CENTER)
                                    .horizontalSizing(Sizing.content()))
                    .positioning(Positioning.relative(0,54)));
            
            rootComponent.child(Containers.verticalFlow(Sizing.fill(64), Sizing.fill(60))
                        //Description box
                        .child(Components.label(Text.translatable("classes.rpmechanics.description"))
                                .positioning(Positioning.relative(0,0))
                        )
                        .child(Containers.verticalScroll(Sizing.fill(), Sizing.fill(30),
                                Components.label(Text.of(rpClasses.get(getRpClassId()).getDescription()))
                                    .maxWidth(getMaxWidthWithGUIScaleOffset())
                                    .positioning(Positioning.relative(0, 10)))
                                .padding(Insets.of(6))
                                .surface(Surface.flat(Color.decode("#171717").getRGB()).and(Surface.outline(Color.decode("#121212").getRGB())))
                                .positioning(Positioning.absolute(0, 12))
                        )
                        //Starting gear dropdown
                        .child(Containers.verticalFlow(Sizing.fill(33), Sizing.fill(60))
                                .child(Components.label(Text.translatable("classes.rpmechanics.starting_gear"))
                                        .positioning(Positioning.absolute(0,0)))
                                .surface(Surface.flat(Color.decode("#171717").getRGB()).and(Surface.outline(Color.decode("#121212").getRGB())))
                                .padding(Insets.of(3))
                                .id("items")
                                .positioning(Positioning.relative(0, 100))
                        )
                        //Skill level bonuses
                        .child(Containers.verticalFlow(Sizing.fill(33), Sizing.fill(60))
                                .child(Components.label(Text.translatable("classes.rpmechanics.levels"))
                                        .positioning(Positioning.absolute(0,0)))
                                .surface(Surface.flat(Color.decode("#171717").getRGB()).and(Surface.outline(Color.decode("#121212").getRGB())))
                                .padding(Insets.of(3))
                                .id("starting_skills")
                                .positioning(Positioning.relative(50, 100))
                        )
                        .child(Containers.verticalFlow(Sizing.fill(33), Sizing.fill(60))
                                .child(Components.label(Text.translatable("classes.rpmechanics.modifiers"))
                                        .positioning(Positioning.absolute(0,0)))
                                .surface(Surface.flat(Color.decode("#171717").getRGB()).and(Surface.outline(Color.decode("#121212").getRGB())))
                                .padding(Insets.of(3))
                                .id("xp_modifiers")
                                .positioning(Positioning.relative(100, 100))
                        )
                    //Information box formatting
                    .padding(Insets.of(6))
                    .surface(Surface.DARK_PANEL)
                    .positioning(Positioning.relative(50,50)));
            
            rpClasses.forEach((id, roleplayClass) -> {
                rootComponent.childById(DropdownComponent.class, "classes").button(
                        Text.of(roleplayClass.getName())
                                .getWithStyle(Style.EMPTY.withColor(Color.decode(roleplayClass.getColor()).getRGB())).getFirst(),
                        button -> {
                            setRpClassId(id);
                            rootComponent.clearChildren();
                            build(rootComponent);
                        });
                
                if (getRpClassId().equals(id)) {
                    roleplayClass.getStartingEquipment().forEach(startingEquipment -> {
                        rootComponent.childById(FlowLayout.class, "items")
                                .child(Components.item(Registries.ITEM.get(Identifier.of(startingEquipment.getItem())).getDefaultStack()).sizing(Sizing.fixed(16))
                                                .positioning(Positioning.absolute(getItemPosX(), getItemPosY()))
                                );
                        if (getItemPosX() >= 96) {
                            setItemPosX(0);
                            setItemPosY(getItemPosY() + 16);
                        } else {
                            setItemPosX(getItemPosX() + 16);
                        }
                    });
                    
                    roleplayClass.getStartingSkills().forEach(bonus -> {
                        rootComponent.childById(FlowLayout.class, "starting_skills")
                                .child(Components.label(Text.literal("+" + bonus.getLevel() + " ").append(Text.translatable("skills.rpmechanics." + bonus.getSkill())))
                                        .positioning(Positioning.absolute(getLevelBonusPosX(), getLevelBonusPosY())));
                        if (getLevelBonusPosY() >= 120) {
                            setLevelBonusPosY(12);
                            setLevelBonusPosX(getLevelBonusPosX() + 36);
                        } else {
                            setLevelBonusPosY(getLevelBonusPosY() + 12);
                        }
                    });
                    roleplayClass.getSkillModifiers().forEach(skillModifier -> {
                        rootComponent.childById(FlowLayout.class, "xp_modifiers")
                                .child(Components.label(Text.literal("+" + skillModifier.getPercentage() + "% ").append(Text.translatable("skills.rpmechanics." + skillModifier.getSkill())))
                                        .positioning(Positioning.absolute(getBonusModifierPosX(), getBonusModifierPosY())));
                        if (getBonusModifierPosY() >= 120) {
                            setBonusModifierPosY(12);
                            setBonusModifierPosX(getBonusModifierPosX() + 36);
                        } else {
                            setBonusModifierPosY(getBonusModifierPosY() + 12);
                        }
                    });
                }
            });
            
            rootComponent.child(Components.button(Text.translatable("classes.rpmechanics.select")
                    .append(" ")
                    .append(Text.of(rpClasses.get(getRpClassId()).getName())
                            .getWithStyle(Style.EMPTY.withColor(Color.decode(rpClasses.get(getRpClassId()).getColor()).getRGB())).getFirst()),
                    buttonComponent -> {
                        ClientPlayNetworking.send(new SendClassSelectedPayload(getRpClassId()));
                        MinecraftClient.getInstance().currentScreen.close();
                    })
                    .sizing(Sizing.content(3), Sizing.fixed(20))
                    .positioning(Positioning.relative(50, 10)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
