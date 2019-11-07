package fr.neolithic.hordemobstests;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class EventListener implements Listener {
    private MobHandler mobHandler;

    public EventListener(MobHandler mobHandler) {
        this.mobHandler = mobHandler;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        for (Entity entity : chunk.getEntities()) {
            if (entity.getScoreboardTags().contains("HordeMobs") && !mobHandler.mobs.contains(entity)) {
                entity.remove();
            }
        }

        SimpleEntry<Integer, Integer> chunkCoordinates = new SimpleEntry<Integer, Integer>(chunk.getX(), chunk.getZ());
        if (mobHandler.mobsToSpawn.containsKey(chunkCoordinates)) {
            Entry<String, Location> mobToSpawn = mobHandler.mobsToSpawn.get(chunkCoordinates);
            mobHandler.mobsToSpawn.remove(chunkCoordinates);
            mobHandler.spawnMob(mobToSpawn.getKey(), mobToSpawn.getValue().getWorld(), mobToSpawn.getValue(), false);
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        if (mobHandler.mobs.contains(event.getEntity())) {
            switch (event.getEntity().getCustomName()) {
                case "§4Asterios":
                    Bukkit.broadcastMessage("§4Asterios §eest reparti dans son labyrinthe !");
                    break;

                case "§4Lucifer":
                    Bukkit.broadcastMessage("§4Lucifer §eest reparti en enfer !");
                    break;

                case "§4Neith":
                    Bukkit.broadcastMessage("§4Neith §eest repartie dans sa pyramide !");
                    break;

                case "§4Seliph":
                    Bukkit.broadcastMessage("§4Lucifer §eest reparti dans son royaume !");
                    break;

                case "§4Tsuchigumo":
                    Bukkit.broadcastMessage("§4Lucifer §eest reparti dans son trou !");
                    break;

                default:
                    break;
            }
            mobHandler.mobs.remove(event.getEntity());
            event.getDrops().clear();
        }
    }
}