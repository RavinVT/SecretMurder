package net.ravinvt.secretMurder;

import net.ravinvt.secretMurder.listeners.custPlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


public final class SecretMurder extends JavaPlugin {
    public static JavaPlugin plugin;
    public static Logger logger;
    public static final AtomicInteger playerCounter = new AtomicInteger(1);
    public static Team hiddenNamesTeam;

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        hiddenNamesTeam = sb.getTeam("hiddenNames");
        if (hiddenNamesTeam == null) {
            hiddenNamesTeam = sb.registerNewTeam("hiddenNames");
            hiddenNamesTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getInventory().contains(Material.OAK_SIGN)) {
                        player.getInventory().addItem(new ItemStack(Material.OAK_SIGN));
                    } else if (!player.getInventory().contains(Material.WRITABLE_BOOK)) {
                        player.getInventory().addItem(new ItemStack(Material.WRITABLE_BOOK));
                    } else if (player.getInventory().containsAtLeast(new ItemStack(Material.OAK_SIGN), 5)) {
                        player.getInventory().remove(Material.OAK_SIGN);
                    } else if (player.getInventory().containsAtLeast(new ItemStack(Material.WRITABLE_BOOK), 5)) {
                        player.getInventory().remove(Material.WRITABLE_BOOK);
                    }

                    if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                        player.hidePlayer(plugin, player);
                    } else {
                        player.showPlayer(plugin, player);
                    }
                }
            }
        }.runTaskTimer(this, 0L, 10L);

        new custPlayerJoinEvent();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
