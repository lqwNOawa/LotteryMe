package me.luoqiwen.minecraft.plugin.lotteryme.command;

import me.luoqiwen.minecraft.plugin.lotteryme.LotteryMe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class LotteryMeCmdHandler implements CommandExecutor
{
    private static final LotteryMe plugin = LotteryMe.INSTANCE;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (strings[0].equalsIgnoreCase("reload") &&
                (commandSender instanceof ConsoleCommandSender || commandSender.hasPermission("lotteryme.reload")))
        {
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, ()->
            {
                plugin.getData().load();
            });
        }
        return false;
    }
}
