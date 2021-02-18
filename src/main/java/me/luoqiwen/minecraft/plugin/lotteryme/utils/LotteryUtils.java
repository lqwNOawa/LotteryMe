package me.luoqiwen.minecraft.plugin.lotteryme.utils;
;
import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class LotteryUtils
{
    private LotteryUtils() {}

    private static final LotteryMe plugin = LotteryMe.INSTANCE;

    public static void launch(Block sign, Player player)
    {
        if (BlockDataUtil.isLotterySign(sign))
        {
            Block down = sign.getRelative(BlockFace.DOWN);
            Dispenser dispenser = (Dispenser) (down.getState());
            Inventory inventory = dispenser.getInventory();
            if (isEmpty(inventory))
                player.sendMessage(plugin.getConfig().getString("empty")
                        .replaceAll("&", "§"));
            else if (dispenser.isPlaced())
            {
                ItemStack result;
                while ((result = inventory.getItem(new Random().nextInt(9))) == null) {}
                DispenseListener dispenseListener = new DispenseListener(down, result);

                dispenser.dispense();
            }
        }
    }

    private static boolean isEmpty(Inventory inventory)
    {
        boolean empty = true;
        for (int i = 0; i < 9; i++)
        {
            if (inventory.getItem(i) != null)
                empty = false;
        }
        return empty;
    }
}

class DispenseListener implements Listener
{
    private final Block dispenser;
    private final ItemStack item;

    public DispenseListener(Block block, ItemStack item)
    {
        dispenser = block;
        this.item = item;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent e)
    {
        if (e.getBlock().equals(dispenser) && !e.getItem().equals(item))
        {
            e.setItem(item);
            finalize();
        }
    }

    @Override
    public void finalize()
    {
        HandlerList.unregisterAll(this);
    }
}
