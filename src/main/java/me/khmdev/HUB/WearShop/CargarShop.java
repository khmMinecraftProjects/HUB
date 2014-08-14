package me.khmdev.HUB.WearShop;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIAuxiliar.Inventory.InventoryConfig;
import me.khmdev.APIAuxiliar.Inventory.ItemConfig;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomInventory;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.ItemOpenInventory;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIBase.Almacenes.ConfigFile;
import me.khmdev.HUB.KitShop.InventoryShowKitShop;
import me.khmdev.HUB.KitShop.OpenInventoryBuy;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CargarShop {
	private static CustomInventory shop=new CustomInventory("Vestidor");
	private static ItemOpenInventory open=
			new ItemOpenInventory(
					AuxPlayer.getItem(Material.APPLE,
							"Shop wear"), shop.getInventory());
	public static void cargar(JavaPlugin plug) {
		
		CItems.addItem(open);
		CItems.addInventorys(shop);

		
		ConfigFile conf = new ConfigFile(plug.getDataFolder(), 
				"Wear");
		
		
		FileConfiguration section = conf.getConfig();
		Iterator<String> iter = section.getKeys(false).iterator();
		while (iter.hasNext()) {
			String string = (String) iter.next();
			if (section.isConfigurationSection(string)) {
				addItem(section.getConfigurationSection(string));
			}
		}
	}
	
	private static void addItem(
			ConfigurationSection section){
		String name=section.getName();
		InventoryConfig inv=new InventoryConfig(name);
		inv.initConf(section);
		double p=0;
		if(section.isDouble("Precio")||
				section.isInt("Precio"))
		{p=section.getDouble("Precio");}
		List<String> l=new LinkedList<>();
		if(section.isList("Grupos"))
		{l=section.getStringList("Grupos");}
		ItemStack sim=AuxPlayer.getItem(Material.APPLE,
				name);
		for (String string : section.getKeys(false)) {
			if(!string.equalsIgnoreCase("Armor")&&
					!string.equalsIgnoreCase("Inventory")&&
					section.isConfigurationSection(string)){
				ItemStack item=
				ItemConfig.crearItem(section.getConfigurationSection(string));
				if(item!=null){
					sim=item;break;
				}
			}
		}
		addItem(name, sim
				
				,inv.getInventory(), inv.getArmor(), p,l);
		
	}
	private static void addItem(String name,ItemStack item,
			ItemStack[] inv, ItemStack[] arm,
			double p,List<String> perms){
		


		InventoryShowKitShop buy = new InventoryShowKitShop(
				name,
				inv, arm, shop.getInventory(), p,perms);
		CItems.addInventorys(buy);
		shop.addItem(new OpenInventoryBuy(
				item,
				buy, name));
	}

	public static ItemOpenInventory getOpen() {
		return open;
	}

	public static Inventory getInventory() {
		return shop.getInventory();
	}

	
}
