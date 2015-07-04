package me.khmdev.HUB;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIAuxiliar.Players.ListenVisible;
import me.khmdev.APIAuxiliar.Players.VisiblesPlayer;
import me.khmdev.HUB.lang.Lang;

public class ItemVisible extends CustomItem {
	private ItemStack vis, inv;
	private long timeout = 5000;
	private static HashMap<String, Long> times = new HashMap<>();

	public ItemVisible() {
		ItemStack item = new ItemStack(Material.GLASS_BOTTLE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Lang.get("ItemVisible.name"));
		item.setItemMeta(meta);
		vis = AuxPlayer
				.getItem(Material.SLIME_BALL, Lang.get("ItemVisible.visible"));

		inv = AuxPlayer.getItem(Material.MAGMA_CREAM,Lang.get("ItemVisible.invisible"));

		item = vis;
	}

	@Override
	public void execute(InventoryClickEvent event) {

	}


	@SuppressWarnings("deprecation")
	@Override
	public void execute(PlayerInteractEvent event) {
		String name = event.getPlayer().getName();
		if (times.containsKey(name)) {
			if ((System.currentTimeMillis() - times.get(name)) < timeout) {
				event.getPlayer().sendMessage(Lang.get("ItemVisible.reload"));
				return;
			} else {
				times.remove(name);
			}
		}

		if (ListenVisible.containNoView(event.getPlayer().getName())) {
			VisiblesPlayer.resetearJugadores(event.getPlayer());
			ListenVisible.removeNoView(event.getPlayer().getName());
			event.getPlayer().sendMessage(Lang.get("ItemVisible.visible"));

			event.getPlayer().setItemInHand(vis);
			event.getPlayer().updateInventory();

		} else {
			VisiblesPlayer.esconderJugadores(event.getPlayer());
			ListenVisible.addNoView(event.getPlayer().getName());
			event.getPlayer().sendMessage(Lang.get("ItemVisible.invisible"));

			event.getPlayer().setItemInHand(inv);
			event.getPlayer().updateInventory();

		}
		times.put(name, System.currentTimeMillis());
	}

	public boolean isthis(ItemStack it) {
		return inv.isSimilar(it) || vis.isSimilar(it);
	}
	
	public ItemStack getInv(){
		return inv;
	}
	public ItemStack getVis(){
		return vis;
	}
}
