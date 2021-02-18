package me.luoqiwen.minecraft.plugin.lotteryme.utils;

import me.luoqiwen.minecraft.plugin.lotteryme.Data;
import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import org.bukkit.block.Block;

import java.util.Map;

public class BlockDataUtil
{
    private BlockDataUtil() {}
    private static final LotteryMe plugin = LotteryMe.INSTANCE;
    private static final Data data = plugin.getData();

    public static void write(Block sign, Block block)
    {
        data.set(toSignData(sign), toData(block));
        data.addLottery(sign, block);
    }

    public static boolean isLotterySign(Block sign)
    {
        return data.getSignMap().containsKey(sign);
    }

    public static boolean isLotteryDispenser(Block dispenser)
    {
        return data.getSignMap().containsValue(dispenser);
    }

    public static Block getDispenser(Block sign)
    {
        return data.getSignMap().get(sign);
    }

    public static Block getSign(Block block)
    {
        for (Map.Entry<Block, Block> entry : data.getSignMap().entrySet())
        {
            Block block1 = entry.getKey();
            Block block2 = entry.getValue();
            if (block2.equals(block))
            {
                return block1;
            }
        }
        return null;
    }

    public static String toSignData(Block sign)
    {
        return "Signs." + toData(sign);
    }

    public static String toData(Block block)
    {
        return block.getType() + String.format("{x=%d,y=%d,z=%d,world=%s}",
                block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
    }

    public static Block parse(String data)
    {
        int begin = data.indexOf("{") + 1;
        int end = data.indexOf("}");
        String split = data.substring(begin, end);
        String[] location = split.split(",");
        int x = Integer.parseInt(location[0].substring(location[0].indexOf("=")+1));
        int y = Integer.parseInt(location[1].substring(location[1].indexOf("=")+1));
        int z = Integer.parseInt(location[2].substring(location[2].indexOf("=")+1));
        String world = location[3].substring(location[3].indexOf("=")+1);

        return plugin.getServer().getWorld(world).getBlockAt(x, y , z);
    }
}
