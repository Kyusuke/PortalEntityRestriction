package uk.co.kyusuke;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPortalEvent;

public class EntityPortalEventListener implements Listener {
	
	// When the entity portal event is called, a check is made to see if the world is
	// in the configuration file's UUID list and stops the portal from transferring the
	// entity if the UUID entry is present.
	@EventHandler
	public void onEntityPortalEvent(EntityPortalEvent event){
		FileConfiguration config = PortalEntityRestriction.getInstance().getConfig();
		
		Location loc = event.getEntity().getLocation();
		String locUUID = loc.getWorld().getUID().toString();
		if(config.contains(locUUID)) event.setCancelled(true);
	}
}
