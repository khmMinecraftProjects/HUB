package me.khmdev.HUB.shop;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

public class ListenAldeano implements Listener {

	private static HashMap<String, Inventory> inventorys
			=new HashMap<>();
	public ListenAldeano() {

	}
	
	public static void addVillager(String name,Inventory i){
		inventorys.put(
				ChatColor.translateAlternateColorCodes('&',
						"&6"+name), i);
	}



	@SuppressWarnings("deprecation")
	public static void spawnAldeano(String name,Location l) {
		Villager v = (Villager) l.getWorld().spawnCreature(l,
				EntityType.VILLAGER);
		v.setProfession(Villager.Profession.LIBRARIAN);
		v.setCustomName(
				ChatColor.translateAlternateColorCodes('&',
						"&6"+name));
		
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent e) {

		if (!(e.getRightClicked().getType() == EntityType.VILLAGER)) {
			return;
		}
		Villager v = (Villager) e.getRightClicked();
		if(v==null||v.getCustomName()==null){return;}
		Inventory inv=getInventory(v.getCustomName());
		if (inv!=null) {
			e.getPlayer().openInventory(inv);
			e.setCancelled(true);
		}

	}
	private Inventory getInventory(String customName) {
		return inventorys.get(customName);
	}

	private static boolean contain(String name){
		
		return inventorys.containsKey(name);
		}
	@EventHandler
	public void atack(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Villager
				&& contain(((Villager) e.getEntity()).getCustomName())) {

			
			if (!(e.getDamager() instanceof Player && ((Player) e.getDamager())
					.hasPermission("villagerTienda.attack"))) {
				e.setCancelled(true);
			}
		}

	}
}
