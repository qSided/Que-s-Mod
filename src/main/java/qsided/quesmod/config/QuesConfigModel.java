package qsided.quesmod.config;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import qsided.quesmod.QuesMod;

@Modmenu(modId = QuesMod.MOD_ID)
@Config(name = "ques-config", wrapperName = "QuesConfig")
public class QuesConfigModel {
    public boolean displayJoinMessage = true;
}
