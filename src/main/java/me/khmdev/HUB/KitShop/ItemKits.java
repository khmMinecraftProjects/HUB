package me.khmdev.HUB.KitShop;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIAuxiliar.Inventory.InventoryBase;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.HUB.Listeners.ListenerHub;
import me.khmdev.HUB.lang.Lang;

public class ItemKits extends CustomItem {
	protected ItemStack[] armor;
	protected ItemStack[] inventory;
	protected String name;
	public ItemKits(String s,ItemStack it, InventoryBase inv) {
		name=s;
		item = it;
		inventory = inv.getInventory();
		armor = inv.getArmor();
	}

	public ItemKits(String s,ItemStack it, ItemStack[] inv, 
						ItemStack[] arm) {
		name=s;
		item = it;
		inventory = inv;
		armor = arm;
	}



	@SuppressWarnings("deprecation")
	public void execute(InventoryClickEvent event) {

		event.getWhoClicked().getInventory().setArmorContents(armor);
		event.getWhoClicked().getInventory().setContents(inventory);
		Bukkit.getServer().getPlayer(
		event.getWhoClicked().getName()).updateInventory();
		ListenerHub.addStandarItems(Bukkit.getServer().getPlayer(
				event.getWhoClicked().getName()));
		Bukkit.getServer().getPlayer(
		event.getWhoClicked().getName()).sendMessage(Lang.get("ItemKits.equipado").replace("%kit%", name));
		//API.setMetadata(event.getWhoClicked(), game+"_kit", name);
	}

	public InventoryBase getInventory() {
		return new InventoryBase(name, armor, inventory);
	}
	public ItemStack[] getItems() {
		return inventory;
	}
	public ItemStack[] getArmor() {
		return armor;
	}
	public void execute(PlayerInteractEvent event) {

	}

}
