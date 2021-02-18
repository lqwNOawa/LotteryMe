package me.luoqiwen.minecraft.plugin.lotteryme.utils;

import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;

public class BlockDataUtil
{
    private BlockDataUtil() {}
    private static final LotteryMe plugin = LotteryMe.INSTANCE;

    public static boolean isLotterySign(Block sign)
    {
        return sign.getType().equals(Material.SIGN_POST)
                && sign.getRelative(BlockFace.DOWN).getState() instanceof Dispenser
                && ((Sign)sign.getState()).getLine(0).equals(plugin.getConfig().getString("symbol"));
    }
}
