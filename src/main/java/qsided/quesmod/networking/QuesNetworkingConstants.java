package qsided.quesmod.networking;

import net.minecraft.util.Identifier;
import qsided.quesmod.QuesMod;

public class QuesNetworkingConstants {
    public static final Identifier LEVEL_UP = Identifier.of(QuesMod.MOD_ID, "level_up");
    public static final Identifier REQUEST_SKILLS = Identifier.of(QuesMod.MOD_ID, "request_skills");
    public static final Identifier SEND_SKILLS_LEVELS = Identifier.of(QuesMod.MOD_ID, "send_skills_levels");
    public static final Identifier SEND_SKILLS_EXPERIENCE = Identifier.of(QuesMod.MOD_ID, "send_skills_experience");
    public static final Identifier SEND_PLAYER_FALL = Identifier.of(QuesMod.MOD_ID, "send_player_fall");
    public static final Identifier SEND_PLAYER_JUMP = Identifier.of(QuesMod.MOD_ID, "send_player_jump");
}
