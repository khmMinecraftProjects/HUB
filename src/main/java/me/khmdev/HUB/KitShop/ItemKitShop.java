package me.khmdev.HUB.KitShop;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Players.APIPermisons;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIEconomy.ConstantesEconomy;
import me.khmdev.APIEconomy.Own.APIEconomy;
import me.khmdev.HUB.WearShop.PlayerDresser;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemKitShop extends CustomItem {
	private double price;
	private String dress;
	private Inventory inventory;
	private List<String> perms=new LinkedList<>();
	private boolean restrict=false;
	public ItemKitShop(String s, ItemStack it, Inventory inve, double pr,
			String dres, List<String> prm) {
		super(it);
		price = pr;
		AuxPlayer.addDescription(item, "Precio: " + price
				+ ConstantesEconomy.UM);
		dress = dres;
		inventory = inve;
		perms=prm;
		if(prm.size()!=0){
			restrict=true;
			}
	}
	public ItemKitShop(String s, ItemStack it, Inventory inve, double pr,
			String dres) {
		super(it);
		price = pr;
		AuxPlayer.addDescription(item, "Precio: " + price
				+ ConstantesEconomy.UM);
		dress = dres;
		inventory = inve;

	}
	public void execute(InventoryClickEvent event) {
		if (Buy(event.getWhoClicked())) {
			PlayerDresser.addDress(event.getWhoClicked().getName(), dress);
			event.getWhoClicked().openInventory(inventory);
		}
	}

	protected boolean Buy(HumanEntity humanEntity) {
		if(!humanEntity.hasPermission("dressshop.buyall")&&
				restrict&&!APIPermisons.hasPerm(humanEntity.getName()
				, perms)){
			Bukkit.getServer()
			.getPlayer(humanEntity.getUniqueId())
			.sendMessage(
					"No tienes permisos para compralo");
			return false;
		}
		
		if (PlayerDresser.contain(humanEntity.getName(), dress)) {
			humanEntity.openInventory(inventory);
			return true;
		}
		double money = APIEconomy.getCash(humanEntity.getName());

		if (price <= money) {
			APIEconomy.reduceCash(humanEntity.getName(), price);

			Bukkit.getServer()
					.getPlayer(humanEntity.getUniqueId())
					.sendMessage(
							"Has comprado el kit " + dress + " por " + price
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
