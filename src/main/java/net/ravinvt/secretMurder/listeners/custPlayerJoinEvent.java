package net.ravinvt.secretMurder.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import io.papermc.paper.util.Tick;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import static net.ravinvt.secretMurder.SecretMurder.*;

public class custPlayerJoinEvent implements Listener {
    public custPlayerJoinEvent() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String fakeName = "Player_" + playerCounter.getAndIncrement();
        player.setDisplayName(fakeName);
        player.setPlayerListName(fakeName);
        hiddenNamesTeam.addEntry(player.getName());
        PlayerProfile profile = Bukkit.createProfile(player.getUniqueId(), fakeName);
        profile.setProperty(new ProfileProperty(
                "textures",
                "ewogICJ0aW1lc3RhbXAiIDogMTc1OTI1NDA0MjI3NSwKICAicHJvZmlsZUlkIiA6ICI4NjY3YmE3MWI4NWE0MDA0YWY1NDQ1N2E5NzM0ZWVkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGV2ZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82MGE1YmQwMTZiM2M5YTFiOTI3MmU0OTI5ZTMwODI3YTY3YmU0ZWJiMjE5MDE3YWRiYmM0YTRkMjJlYmQ1YjEiCiAgICB9LAogICAgIkNBUEUiIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk1M2NhYzhiNzc5ZmU0MTM4M2U2NzVlZTJiODYwNzFhNzE2NThmMjE4MGY1NmZiY2U4YWEzMTVlYTcwZTJlZDYiCiAgICB9CiAgfQp9",
                "I2ZIJrPlrMjIBPv+G80n8H6FVIT2XvR5dgNUrZDfiLzOG6QJDGJgsYVlLfJmRYIEA9bTOQs7Yt/nv2lSNeU1P875zQeTaOisWX6nazgqGhFXAfSTPETxcsWG1fvcnKaOhXaZBRigiD9Xfb02N9Y1qu0tdfdBexTd42yCPpbFXfjLUU7ux4loz7OWU/v41h+ZhDgPPSotc/Wtuz6uPFkXRz+COkfv2hD7kGW8GuCn2kuVqvWbarpS/vMc3QrPdNHjx2I//d29d1jCs0ZZeuPk6cFvmMW0myCc/pW11aieaMbuS7WkOVJ0oK8C3RshmlWgBXzLYhmxTQuEQbo1SSD1e5bdzBTvndu7rQdQdCRSOnxcc5967j4k3MnE0DdbO4vD8g/2Ez7xm3BpqG8WZpVR82T4Tl/E51zOXP5fd+EVCBR4FLCUs7RRSlOkrAN6MgUF7uidefIil62GrmFT3FEM+BHXqzZD7OH62kaeCqBeev/1MBFtOO71nD+zlv16O4Q8zhZGCLambSVMYF0X5DrDRSws4VY8UXPyROr6uBgscDLay6uvP+3tF/qBsI3Lf671E+MRfpIZOg0gFlqacViCord62CKgZ7+tu7+8F67J5K3c8rrF+AXiPHCvIwguWGVGBfk8oMxNj/LfTjwO2KD1dCfW+kdhu6js5h9VhZzydx8="
        ));
        player.setPlayerProfile(profile);
        player.hidePlayer(plugin, player);
        player.showPlayer(plugin, player);
        event.setJoinMessage(ChatColor.YELLOW + player.getDisplayName() + " joined the game" + ChatColor.RESET);
    }

    @EventHandler
    private static void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.GRAY + "You hear nothing but whispers" + ChatColor.RESET);
        event.setCancelled(true);
    }

    @EventHandler
    private static void onUpdate(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType().equals(Material.WRITABLE_BOOK) || event.getItemDrop().getItemStack().getType().equals(Material.OAK_SIGN)) {
            event.getPlayer().sendMessage(ChatColor.GRAY + "You can't drop this item" + ChatColor.RESET);
            event.setCancelled(true);
        }
    }

    @EventHandler
    private static void onSleep(PlayerInteractEvent event) {
         if (event.getClickedBlock().getType().equals(Material.RED_BED) && !(event.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
             if (!event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
                 event.getPlayer().setGameMode(GameMode.SPECTATOR);
             }
             new BukkitRunnable() {
                 int seconds = 120;

                 public void run() {
                     if (seconds <= 0) {
                         if (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
                             event.getPlayer().setGameMode(event.getPlayer().getPreviousGameMode());
                         }
                         cancel();
                         return;
                     }

                     int minutes = seconds / 60;
                     int sec = seconds % 60;
                     String time = String.format("%d:%02d", minutes, sec);

                     event.getPlayer().sendActionBar(Component.text("Time Left: " + time));

                     seconds--;
                 }
             }.runTaskTimer(plugin, 0L, 20L);
             event.setCancelled(true);
         }
    }
}
