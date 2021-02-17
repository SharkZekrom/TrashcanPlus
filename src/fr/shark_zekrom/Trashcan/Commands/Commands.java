package fr.shark_zekrom.Trashcan.Commands;

import fr.shark_zekrom.Trashcan.Config;
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
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
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
                    player.sendMessage(ChatColor.AQUA + "/trashcan create §8» §eDelete a trashcan");
                    player.sendMessage(ChatColor.AQUA + "/trashcan reload §8» §eReload the config");
                }
                if (args[0].equalsIgnoreCase("create")) {
                    if (args[1].equalsIgnoreCase("block")) {
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
                            player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " There is already a trashcan at this coordinate.");

                        } else {

                            config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, true);
                            player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Trash can with coordinate " + locx + ", " + locy + ", " + locz + " set up.");

                            try {
                                config.save(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (args[1].equalsIgnoreCase("entity")) {

                        String key = ".";
                        File file = new File(Main.getInstance().getDataFolder(), "trashcanentity.yml");
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                        ConfigurationSection configsection = config.getConfigurationSection(key);

                        for (Entity entity : player.getNearbyEntities(0.5D, 0.5D, 0.5D)) {
                            if (entity instanceof ArmorStand) {
                                Double locx = entity.getLocation().getX();
                                Double locy = entity.getLocation().getY();
                                Double locz = entity.getLocation().getZ();
                                String locworld = entity.getLocation().getWorld().getName();

                                String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

                                if (loc != null) {
                                    player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " There is already a trashcan at this coordinate.");

                                } else {

                                    config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, true);
                                    player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Trash can entity with coordinate " + locx + ", " + locy + ", " + locz + " set up.");

                                    try {
                                        config.save(file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    if (args[1].equalsIgnoreCase("block")) {
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
                            player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Trash can with coordinate " + locx + ", " + locy + ", " + locz + " deleted.");

                            try {
                                config.save(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {

                            player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " No trash can at this coordinate.");
                        }
                    }
                    if (args[1].equalsIgnoreCase("entity")) {
                        String key = ".";
                        File file = new File(Main.getInstance().getDataFolder(), "trashcanentity.yml");
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                        ConfigurationSection configsection = config.getConfigurationSection(key);

                        for (Entity entity : player.getNearbyEntities(0.5D, 0.5D, 0.5D)) {
                            if (entity instanceof ArmorStand) {
                                Double locx = entity.getLocation().getX();
                                Double locy = entity.getLocation().getY();
                                Double locz = entity.getLocation().getZ();
                                String locworld = entity.getLocation().getWorld().getName();

                                String loc = config.getString("trashcan." + locworld + "." + locx + "." + locy + "." + locz);

                                if (loc != null) {
                                    config.set("trashcan." + locworld + "." + locx + "." + locy + "." + locz, null);
                                    player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " Trash can entity with coordinate " + locx + ", " + locy + ", " + locz + " deleted.");

                                    try {
                                        config.save(file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW + " No trash can entity at this coordinate.");
                                }
                            }
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    Config.reload();
                    player.sendMessage(ChatColor.AQUA + "[Trashcan+]" + ChatColor.YELLOW +" Plugin reloaded.");
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
            arguments.add("reload");
            arguments.add("help");

            return arguments;
        }
        return null;

    }
}