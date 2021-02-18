package me.luoqiwen.minecraft.plugin.lotteryme.listeners;

import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SignPlaceListener implements Listener
{
    private static final LotteryMe plugin = LotteryMe.INSTANCE;

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e)
    {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, ()->
        {
            if (e.getBlockPlaced().getType().equals(Material.SIGN_POST))
                e.getPlayer().sendMessage(plugin.getConfig().getString("succeed")
                        .replaceAll("&", "§"));
        });
    }
}
