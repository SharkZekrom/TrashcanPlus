package fr.shark_zekrom.Trashcan.Commands;

import fr.shark_zekrom.Trashcan.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
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
                    String key = ".";
                    File file = new File(Main.getInstance().getDataFolder(), "trashcan.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                    ConfigurationSection configsection = config.getConfigurationSection(key);


                    Block block = player.getTargetBlock(null, 100);

                    long locx = (long) block.getLocation().getX();
                    long locy = (long) block.getLocation().getY();
                    long locz = (long) block.getLocation().getZ();
                    String locworld = block.getLocation().getWorld().getName();

                    String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

                    if (loc != null) {
                        player.sendMessage("il y a déja un trashcan a cette coordoné");
                    }
                    else {

                        config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, true);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        player.sendMessage("create");
                    }



                }
                if (args[0].equalsIgnoreCase("delete")) {

                    String key = ".";
                    File file = new File(Main.getInstance().getDataFolder(), "trashcan.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                    ConfigurationSection configsection = config.getConfigurationSection(key);

                    Block block = player.getTargetBlock(null, 100);

                    long locx = (long) block.getLocation().getX();
                    long locy = (long) block.getLocation().getY();
                    long locz = (long) block.getLocation().getZ();
                    String locworld = block.getLocation().getWorld().getName();

                    String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

                    if (loc != null) {
                        config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, null);
                        try {
                            config.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        player.sendMessage("trashcan delete");
                    }
                    else {

                        player.sendMessage("aucun trashcan ici");
                    }
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
            arguments.add("delete");
            arguments.add("help");
            return arguments;
        }
        return null;

    }
}