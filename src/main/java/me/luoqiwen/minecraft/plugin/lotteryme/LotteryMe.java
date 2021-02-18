package me.luoqiwen.minecraft.plugin.lotteryme;

import me.luoqiwen.minecraft.plugin.lotteryme.command.LotteryMeCmdHandler;
import me.luoqiwen.minecraft.plugin.lotteryme.listeners.DispenserPlaceListener;
import me.luoqiwen.minecraft.plugin.lotteryme.listeners.SignInteractListener;
import me.luoqiwen.minecraft.plugin.lotteryme.listeners.SignPlaceListener;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class LotteryMe extends JavaPlugin
{
    public static LotteryMe INSTANCE;
    private Data data;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        INSTANCE = this;

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists())
        {
            getDataFolder().mkdirs();
            saveResource("config.yml", true);
        }

        data = new Data("data.yml");

        getServer().getPluginManager().registerEvents(new DispenserPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new SignPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new SignInteractListener(), this);

        getCommand("lotteryme").setExecutor(new LotteryMeCmdHandler());
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic

        HandlerList.unregisterAll(this);
        getServer().getScheduler().cancelTasks(this);
        data.save();
    }

    public Data getData()
    {
        return data;
    }
}
