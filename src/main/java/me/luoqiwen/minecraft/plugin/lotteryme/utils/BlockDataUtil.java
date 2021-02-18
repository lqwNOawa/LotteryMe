package me.luoqiwen.minecraft.plugin.lotteryme.utils;

import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
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
        return sign.getState() instanceof Sign && sign.getRelative(BlockFace.DOWN) instanceof Dispenser;
    }
}
