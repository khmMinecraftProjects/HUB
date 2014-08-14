package me.khmdev.HUB.KitShop;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.HUB.WearShop.PlayerDresser;

public class OpenInventoryBuy extends CustomItem {

	private InventoryShowKitShop inventory;
	private String dress;

	public OpenInventoryBuy(ItemStack it, InventoryShowKitShop inv, String perms,
			String dress) {
		inventory = inv;
		item = it;
		permisos = perms;
		this.dress = dress;
	}

	public OpenInventoryBuy(ItemStack it, InventoryShowKitShop inv, String dress) {
		inventory = inv;
		item = it;
		this.dress = dress;

	}

	@Override
	public void execute(InventoryClickEvent event) {
		event.setCancelled(true);
		if (PlayerDresser.contain(event.getWhoClicked().getName(), dress)) {
			event.getWhoClicked().openInventory(
					inventory.getBuy().getInventory());
		} else {
			event.getWhoClicked().openInventory(
					inventory.getInventory());
		}
	}

	@Override
	public void execute(PlayerInteractEvent event) {
		event.setCancelled(true);
		if (PlayerDresser.contain(event.getPlayer().getName(), dress)) {
			event.getPlayer().openInventory(
					inventory.getBuy().getInventory());
		} else {
			event.getPlayer().openInventory(
					inventory.getInventory());
		}
	}
}
