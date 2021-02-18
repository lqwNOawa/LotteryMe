package me.luoqiwen.minecraft.plugin.lotteryme;

import me.luoqiwen.minecraft.plugin.lotteryme.utils.BlockDataUtil;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data extends YamlConfiguration
{
    private static final LotteryMe plugin = LotteryMe.INSTANCE;
    private final File dataFile;
    private final HashMap<Block, Block> signMap = new HashMap<>();

    public Data(String path)
    {
        dataFile = new File(plugin.getDataFolder(), path);
        if (!dataFile.exists())
        {
            plugin.getDataFolder().mkdirs();
            try
            {
                Files.createFile(dataFile.toPath());
            }
            catch (IOException e)
            {
                e.printStackTrace();
                plugin.getLogger().warning("无法加载数据文件");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
        }
        try
        {
            load(dataFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
            plugin.getLogger().warning("无法加载数据文件");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::save,
                1000, 1000);

        List<Map<?, ?>> refreshed = getMapList("Signs");
        getMapList("Signs").forEach(map ->
        {
            map.forEach((o, o2) ->
            {
                String key = (String) o;
                String value = (String) o2;
                Block sign = BlockDataUtil.parse(key);
                Block dispenser = BlockDataUtil.parse(value);
                if (sign.getWorld().getBlockAt(sign.getLocation()) != sign
                        || dispenser.getWorld().getBlockAt(dispenser.getLocation())!= dispenser)
                    refreshed.remove(map);
                else
                    signMap.put(sign, dispenser);
            });
        });
        set("Signs", refreshed);
    }

    public void save()
    {
        try
        {
            save(dataFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            plugin.getLogger().warning("无法保存数据");
        }
    }

    public HashMap<Block, Block> getSignMap()
    {
        return signMap;
    }

    public void addLottery(Block sign, Block dispenser)
    {
        signMap.put(sign, dispenser);
    }

    public void removeSign(Block sign)
    {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, ()->
        {
            if (signMap.containsKey(sign))
            {
                signMap.remove(sign);
                List<Map<?, ?>> refreshed = getMapList("Signs");
                getMapList("Signs").forEach(map ->
                {
                    if (map.containsKey(BlockDataUtil.toData(sign)))
                    {
                        refreshed.remove(map);
                    }
                });
                set("Signs", refreshed);
            }
        });
    }

    public void removeDispenser(Block dispenser)
    {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, ()->
        {
            Block sign = BlockDataUtil.getSign(dispenser);
            if (sign != null)
                removeSign(sign);
        });
    }
}
