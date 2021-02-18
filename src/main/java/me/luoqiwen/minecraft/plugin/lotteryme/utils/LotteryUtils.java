package me.luoqiwen.minecraft.plugin.lotteryme.utils;
;
import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
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

    private final LotteryMe plugin = LotteryMe.INSTANCE;

    public static void launch(Block block)
    {
        if (block.getState() instanceof Dispenser)
        {
            Dispenser dispenser = (Dispenser) block.getState();
            Inventory inventory = dispenser.getInventory();
            if (dispenser.isPlaced() && inventory.firstEmpty() < 8)
            {
                ItemStack result;
                while ((result = inventory.getItem(new Random().nextInt(9))) == null) {}
                DispenseListener dispenseListener = new DispenseListener(block, result);

                dispenser.dispense();
            }
        }
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
