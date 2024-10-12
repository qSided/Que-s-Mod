package qsided.quesmod.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record SendSkillsExperiencePayload(Float mining, Float enchanting, Float combat, Float woodcutting, Float farming, Float endurance) implements CustomPayload {
    
    public static final Id<SendSkillsExperiencePayload> ID = new Id<>(QuesNetworkingConstants.SEND_SKILLS_EXPERIENCE);
    public static final PacketCodec<RegistryByteBuf, SendSkillsExperiencePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, SendSkillsExperiencePayload::mining,
            PacketCodecs.FLOAT, SendSkillsExperiencePayload::enchanting,
            PacketCodecs.FLOAT, SendSkillsExperiencePayload::combat,
            PacketCodecs.FLOAT, SendSkillsExperiencePayload::woodcutting,
            PacketCodecs.FLOAT, SendSkillsExperiencePayload::farming,
            PacketCodecs.FLOAT, SendSkillsExperiencePayload::endurance,
            SendSkillsExperiencePayload::new);
    
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
