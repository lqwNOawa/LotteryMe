package me.luoqiwen.minecraft.plugin.lotteryme.listeners;

import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import me.luoqiwen.minecraft.plugin.lotteryme.utils.BlockDataUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
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
            Block block = e.getBlock();
            if (block.getType() == Material.SIGN_POST)
            {
                Block check = block.getRelative(BlockFace.DOWN);
                if (check.getState() instanceof Dispenser && e.getLine(0).equals(plugin.getConfig().getString("symbol")))
                {
                    e.getPlayer().sendMessage(plugin.getConfig().getString("succeed")
                            .replaceAll("&", "§"));
                    e.setLine(0, "§6§l" + e.getLine(0));
                    BlockDataUtil.write(block, check);
                }
            }
        });
    }
}
