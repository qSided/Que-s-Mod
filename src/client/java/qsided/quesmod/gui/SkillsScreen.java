package qsided.quesmod.gui;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class SkillsScreen extends BaseOwoScreen<FlowLayout> {
    @Override
    protected @NotNull OwoUIAdapter createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }
    
    @Override    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);
        rootComponent.child(
                Containers.verticalScroll(Sizing.fill(40), Sizing.fill(60),
                        Containers.verticalFlow(Sizing.fill(), Sizing.fill())
                                .child(Components.dropdown(Sizing.fill())
                                        .nested(Text.literal("Mining"), Sizing.fill(), dropdownComponent -> {
                                            Components.label(Text.literal("test"));
                                        })))
        );
    }
}
