package me.luoqiwen.minecraft.plugin.lotteryme.listeners;

import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import me.luoqiwen.minecraft.plugin.lotteryme.utils.BlockDataUtil;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignPlaceListener implements Listener
{
    private static final LotteryMe plugin = LotteryMe.INSTANCE;

    @EventHandler(ignoreCancelled = true)
    public void onPlace(SignChangeEvent e)
    {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, ()->
        {
            if (e.getBlock().getType().equals(Material.SIGN_POST)
                    && e.getBlock().getRelative(BlockFace.DOWN).getState() instanceof Dispenser
                    && ((Sign)e.getBlock().getState()).getLine(0).equals(plugin.getConfig().getString("symbol")))
            {
                e.getPlayer().sendMessage(plugin.getConfig().getString("succeed")
                        .replaceAll("&", "§"));
                Sign state = (Sign)e.getBlock().getState();
                state.setLine(0, BlockDataUtil.getColoredSymbol());
                state.setLine(1, "§a§l创建者: §3§l" + e.getPlayer().getName());
                state.update();
            }
        });
    }
}
