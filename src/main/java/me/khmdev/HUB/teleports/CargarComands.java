package me.khmdev.HUB.teleports;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.khmdev.APIAuxiliar.Inventory.ItemConfig;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomInventory;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.ItemOpenInventory;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIBase.Almacenes.ConfigFile;

public class CargarComands {
	private static ItemOpenInventory itemOpenInventory;
	private static CustomInventory inv;

	public static void cargar(JavaPlugin plug) {
		ConfigFile conf = new ConfigFile(plug.getDataFolder(), "Commands");
		FileConfiguration section = conf.getConfig();
		String nam="Spawns";
		if(section.isString("Name")){nam=section.getString("Name");}
		nam=ChatColor.translateAlternateColorCodes('&', nam);	
		inv = new CustomInventory(nam);
		for (String s : section.getKeys(false)) {
			if (section.isConfigurationSection(s)) {
				ItemStack it = ItemConfig.crearItem(section
						.getConfigurationSection(s));
				String cm=section.getConfigurationSection(s)
						.getString("Comand");
				String usr=section.getConfigurationSection(s)
						.getString("User");
				CommandItem cmd;
				if(usr==null){
					cmd = new CommandItem(it, cm);
				}else{
					cmd  = new CommandItem(it, cm,usr); 
				}
				inv.addItem(cmd);
			}
		}
		CItems.addInventorys(inv);
		itemOpenInventory = (new ItemOpenInventory(AuxPlayer.getItem(
				Material.NETHER_STAR, nam), inv.getInventory()));
		CItems.addItem(itemOpenInventory);

	}

	public static ItemOpenInventory getItemOpenInventory() {
		return itemOpenInventory;
	}

}
