package qsided.quesmod.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Sync;
import qsided.quesmod.QuesMod;

@Modmenu(modId = QuesMod.MOD_ID)
@Config(name = "ques-config", wrapperName = "QuesConfig")
public class QuesConfigModel {
    
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean displayJoinMessage = true;
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean enableRequirements = true;
}
