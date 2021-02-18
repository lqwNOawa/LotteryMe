package me.luoqiwen.minecraft.plugin.lotteryme.listeners;

import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import me.luoqiwen.minecraft.plugin.lotteryme.utils.BlockDataUtil;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class SignPlaceListener implements Listener
{
    private static final LotteryMe plugin = LotteryMe.INSTANCE;

    @EventHandler(ignoreCancelled = true)
    public void onPlace(SignChangeEvent e)
    {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, ()->
        {
            if (BlockDataUtil.isLotterySign(e.getBlock()))
            {
                e.getPlayer().sendMessage(plugin.getConfig().getString("succeed")
                        .replaceAll("&", "§"));
                Sign state = (Sign)e.getBlock().getState();
                state.setLine(0, BlockDataUtil.getColoredSymbol());
                state.update();
            }
        });
    }
}
