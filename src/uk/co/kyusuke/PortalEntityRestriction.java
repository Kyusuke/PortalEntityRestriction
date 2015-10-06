package uk.co.kyusuke;	

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalEntityRestriction extends JavaPlugin {
	
	private static PortalEntityRestriction instance;
	public static PortalEntityRestriction getInstance() {
		return instance;
	}
	
	//Registering the entity portal event into the server manager.
	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(new EntityPortalEventListener(), this);
	}
	
	@Override
	public void onDisable(){
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(command.getName().equalsIgnoreCase("peridentify")){
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Location loc = player.getLocation();
				String worldName = loc.getWorld().getName();
				String worldUUID = loc.getWorld().getUID().toString();
				sender.sendMessage("Current world name is " +worldName+ " and the UUID is " +worldUUID);
			}
			return true;
		}
		// NOTE
		// getConfig().options().copyDefaults(true) will apply the addDefault method to the current
		// server configuration memory and is needed when saveConfig() is called.
		// reloadConfig() is needed to read from the configuration file again otherwise it'll still
		// use the configuration from when the server was launched.
		else if(command.getName().equalsIgnoreCase("perrestrict")){
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Location loc = player.getLocation();
				String worldUUID = loc.getWorld().getUID().toString();
				if(getConfig().contains(worldUUID)){
					player.sendMessage("This world's portals are already restricted");
				}
				else {
					getConfig().addDefault(worldUUID, true);
					getConfig().options().copyDefaults(true);
					saveConfig();
					reloadConfig();
					player.sendMessage("This world's portals are now restricted");
				}
				
			}
			return true;
		}
		else if(command.getName().equalsIgnoreCase("perallow")){
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Location loc = player.getLocation();
				String worldUUID = loc.getWorld().getUID().toString();
				if(!getConfig().contains(worldUUID)) {
					player.sendMessage("This world's portals are already unrestricted");
				}
				else {
					getConfig().set(worldUUID, null);
					getConfig().options().copyDefaults(true);
					saveConfig();
					reloadConfig();
					player.sendMessage("This world's portals are now unrestricted");
				}
			}
			return true;
		}
		return false;
	}
}
