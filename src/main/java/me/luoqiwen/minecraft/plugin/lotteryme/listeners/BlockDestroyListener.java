package me.luoqiwen.minecraft.plugin.lotteryme.listeners;

import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import me.luoqiwen.minecraft.plugin.lotteryme.utils.BlockDataUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockDestroyListener implements Listener
{
    private static final LotteryMe plugin = LotteryMe.INSTANCE;

    @EventHandler(ignoreCancelled = true)
    public void onDestroy(BlockBreakEvent e)
    {
        if (BlockDataUtil.isLotterySign(e.getBlock()))
        {
            e.getPlayer().sendMessage(plugin.getConfig().getString("destroy")
                    .replaceAll("&", "§"));
            plugin.getData().removeSign(e.getBlock());
        }
        else if (BlockDataUtil.isLotteryDispenser(e.getBlock()))
        {
            e.getPlayer().sendMessage(plugin.getConfig().getString("destroy")
                    .replaceAll("&", "§"));
            plugin.getData().removeDispenser(e.getBlock());
        }
    }
}
