package qsided.quesmod;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {
    
    public HashMap<UUID, PlayerData> players = new HashMap<>();
    
    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        
        NbtCompound playersNbt = new NbtCompound();
        players.forEach((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            
            NbtCompound skillLevelsNbt = new NbtCompound();
            playerData.skillLevels.forEach(skillLevelsNbt::putInt);
            playerNbt.put("skillLevels", skillLevelsNbt);
            
            NbtCompound skillExpNbt = new NbtCompound();
            playerData.skillExperience.forEach(skillExpNbt::putFloat);
            playerNbt.put("skillExp", skillExpNbt);
            
            NbtCompound rpClassLevelNbt = new NbtCompound();
            playerData.rpClassLevel.forEach(rpClassLevelNbt::putInt);
            playerNbt.put("rpClassLevel", rpClassLevelNbt);
            
            NbtCompound rpClassExpNbt = new NbtCompound();
            playerData.rpClassExperience.forEach(rpClassExpNbt::putFloat);
            playerNbt.put("rpClassExperience", rpClassExpNbt);
            
            //Puts each player's data into nbt
            playersNbt.put(uuid.toString(), playerNbt);
        });
        //Writes all players' saved class and skill nbt into a save file
        nbt.put("players", playersNbt);
        
        return nbt;
    }
    
    public static StateSaverAndLoader createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        
        NbtCompound playersNbt = nbt.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();
            
            NbtCompound skillLevelsNbt = playersNbt.getCompound(key).getCompound("skillLevels");
            skillLevelsNbt.getKeys().forEach(s -> {
                String skill = String.valueOf(s);
                int level = skillLevelsNbt.getInt(s);
                playerData.skillLevels.put(skill, level);
            });
            NbtCompound skillExpNbt = playersNbt.getCompound(key).getCompound("skillExp");
            skillExpNbt.getKeys().forEach(s -> {
                String skill = String.valueOf(s);
                float experience = skillExpNbt.getInt(s);
                playerData.skillExperience.put(skill, experience);
            });
            
            NbtCompound rpClassLevelNbt = playersNbt.getCompound(key).getCompound("rpClassLevel");
            rpClassLevelNbt.getKeys().forEach(s -> {
                String rpClass = String.valueOf(s);
                int level = rpClassLevelNbt.getInt(s);
                playerData.rpClassLevel.put(rpClass, level);
            });
            
            NbtCompound rpClassExpNbt = playersNbt.getCompound(key).getCompound("rpClassExp");
            rpClassExpNbt.getKeys().forEach(s -> {
                String rpClass = String.valueOf(s);
                float experience = rpClassExpNbt.getInt(s);
                playerData.rpClassExperience.put(rpClass, experience);
            });
            
            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });
        
        return state;
    }
    
    private static Type<StateSaverAndLoader> type = new Type<>(
            StateSaverAndLoader::new,
            StateSaverAndLoader::createFromNbt,
            null
    );
    
    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(type, QuesMod.MOD_ID);
        
        state.markDirty();
        
        return state;
    }
    
    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(player.getWorld().getServer());
        
        return serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
    }
}
