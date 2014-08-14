package me.khmdev.HUB.shop;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIBase.Auxiliar.Auxiliar;
import me.khmdev.APIEconomy.ConstantesEconomy;
import me.khmdev.APIEconomy.Own.APIEconomy;

public class ShopItem extends CustomItem {
	double price;
	ItemStack item2;

	public ShopItem(ItemStack it, double pric) {
		
		price = pric;

		if (it == null) {
			return;
		}
		item = it.clone();
		item2 = it;

		ItemMeta meta = item.getItemMeta();
		if (meta.getLore() != null) {
			List<String> lr=meta.getLore();
			lr.add("Precio: " + price
					+ ConstantesEconomy.UM);
			meta.setLore(lr);
		} else {

			meta.setLore(Arrays.asList("Precio: " + price
					+ ConstantesEconomy.UM));
		}
		item.setItemMeta(meta);
	}

	public ItemStack getBItem() {
		return item2;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public void execute(InventoryClickEvent event) {
		event.setCancelled(true);

		if (Buy(event.getWhoClicked())) {
			event.getWhoClicked().getInventory().addItem(item2);
		}
	}

	protected boolean Buy(HumanEntity humanEntity) {
		if (item2 == null) {
			return false;
		}
		double money = APIEconomy.getCash(humanEntity.getName());

		if (price <= money) {
			APIEconomy.reduceCash(humanEntity.getName(), price);
			String name;
			if (item.getItemMeta() != null
					&& item.getItemMeta().getDisplayName().length() != 0) {
				name = item.getItemMeta().getDisplayName();
			} else {
				name = Auxiliar.getOriginalName(item.getData().getItemType()
						.name());
			}
			Bukkit.getServer()
					.getPlayer(humanEntity.getUniqueId())
					.sendMessage(
							"Has comprado " + name + " por " + price
									+ ConstantesEconomy.UM);
			return true;
		} else {
			Bukkit.getServer().getPlayer(humanEntity.getUniqueId())
					.sendMessage("Fondos insuficientes");
			return false;
		}
	}

	@Override
	public void execute(PlayerInteractEvent event) {

	}

}
