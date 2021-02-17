package fr.shark_zekrom.Trashcan.Commands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor , TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("trashcan")) {
            if (sender.hasPermission("trashcan.admin")) {
                if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.AQUA + "==========[Trashcan+]==========");
                    player.sendMessage(ChatColor.AQUA + "");
                    player.sendMessage(ChatColor.AQUA + "Commands:");
                    player.sendMessage(ChatColor.AQUA + "");
                    player.sendMessage(ChatColor.AQUA + "/trashcan help §8» §eSee all commands");
                    player.sendMessage(ChatColor.AQUA + "/trashcan create §8» §eCreate a trashcan");
                    player.sendMessage(ChatColor.AQUA + "/trashcan reload §8» §eReload the config");
                }
                if (args[0].equalsIgnoreCase("create")) {
                    Block block = player.getTargetBlock(null, 4);

                    player.sendMessage(String.valueOf(block.getLocation()));
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("create");
            arguments.add("help");
            return arguments;
        }
        return null;

    }
}