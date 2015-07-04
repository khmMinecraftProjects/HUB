package me.khmdev.HUB;

import java.util.HashMap;
import java.util.Map.Entry;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomInventory;
import me.khmdev.APIBase.API;
import me.khmdev.APIBase.Almacenes.Almacen;
import me.khmdev.APIBase.Almacenes.Central;
import me.khmdev.APIBase.Almacenes.Datos;
import me.khmdev.APIBase.Almacenes.local.ConfigFile;
import me.khmdev.APIBase.Auxiliar.runKill;
import me.khmdev.HUB.Bar.CargaMensajes;
import me.khmdev.HUB.HoloGraph.holoAPI;
import me.khmdev.HUB.Listeners.ListenerHub;
import me.khmdev.HUB.Listeners.ListenerTags;
import me.khmdev.HUB.Tutorial.Tutorial;
import me.khmdev.HUB.Tutorial.TutorialAction;
import me.khmdev.HUB.Tutorial.newSignTutorial;
import me.khmdev.HUB.Tutorial.signTutorial;
import me.khmdev.HUB.WearShop.CargarShop;
import me.khmdev.HUB.WearShop.PlayerDresser;
import me.khmdev.HUB.lang.Lang;
import me.khmdev.HUB.scoreBoard.CargaBoard;
import me.khmdev.HUB.shop.ItemsCust;
import me.khmdev.HUB.shop.ListenAldeano;
import me.khmdev.HUB.shop.ShopItem;
import me.khmdev.HUB.teleports.CargarComands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Base implements Datos {

	private static JavaPlugin plug = null;
	private Central central;
	private static ItemVisible enabler;

	public static ItemVisible getEnabler() {
		return enabler;
	}
	public static JavaPlugin getInstance() {
		return plug;
	}

	public static void run(runKill run) {
		if (plug == null) {
			return;
		}

		int idd = Bukkit.getScheduler().scheduleSyncRepeatingTask(plug, run,
				0L, 20L);
		run.setId(idd);
	}

	private static CustomInventory inv;

	public static Inventory getInventory() {
		return inv.getInventory();
	}

	public static void init() {
		inv = new CustomInventory(Lang.get("Base.tienda"));
		CItems.addItem(ItemsCust.maria);
		CItems.addItem(ItemsCust.esteroides);
		CItems.addItem(ItemsCust.Cocacola);
		CItems.addItem(ItemsCust.Cupido);
		CItems.addItem(ItemsCust.Creeperniano);
		CItems.addItem(ItemsCust.milk);
		
		ListenerHub.addStandarItem(enabler.getVis());
		ListenerHub.addNDItem(ItemsCust.maria.getItem());
		ListenerHub.addNDItem(ItemsCust.esteroides.getItem());
		ListenerHub.addNDItem(ItemsCust.Cocacola.getItem());
		ListenerHub.addNDItem(ItemsCust.Cupido.getItem());
		ListenerHub.addNDItem(ItemsCust.Creeperniano.getItem());

		inv.addItem(new ShopItem(ItemsCust.maria.getItem(), 60));
		inv.addItem(new ShopItem(ItemsCust.esteroides.getItem(), 120));
		inv.addItem(new ShopItem(ItemsCust.Cocacola.getItem(), 85));
		inv.addItem(new ShopItem(ItemsCust.Cupido.getItem(), 150));
		inv.addItem(new ShopItem(ItemsCust.Creeperniano.getItem(), 150));

		CItems.addInventorys(inv);

	}

	public Base(JavaPlugin pl) {
		plug = pl;

		/**
		 * Bungee
		 */
		Bukkit.getMessenger().registerOutgoingPluginChannel(pl, "BungeeCord");

		/**
		 * Tutorial
		 */
		pl.getServer().getPluginManager()
				.registerEvents(new TutorialAction(), pl);

		/**
		 * Own
		 */
		enabler = new ItemVisible();
		CItems.addItem(enabler);
		init(pl);
		central = new Central(pl);
		central.insertar(this);
		pl.getServer().getPluginManager().registerEvents(new ListenerHub(), pl);

		/**
		 * Tags
		 */
		if (hasPluging("PermissionsEx")) {
			pl.getServer().getPluginManager()
					.registerEvents(new ListenerTags(pl), pl);
		}

		/**
		 * ScoreBoard
		 */
		CargaBoard.cargar(plug);

		/**
		 * Boss Bar
		 */
		CargaMensajes.cargar(pl);

		/**
		 * Aldeano Shop
		 */
		pl.getServer().getPluginManager()
				.registerEvents(new ListenAldeano(), pl);
		init();
		ListenAldeano.addVillager(Lang.get("Base.VillagerTienda"), inv.getInventory());

		/**
		 * Dress Shop
		 */
		CargarShop.cargar(pl);
		ListenAldeano.addVillager(Lang.get("Base.VillagerVestidor"), CargarShop.getInventory());
		API.getInstance().getCentral().insertar(new PlayerDresser());

		/**
		 * Command Item
		 */
		CargarComands.cargar(pl);
		ListenerHub.addStandarItem(CargarComands.getItemOpenInventory()
				.getItem());
		/**
		 * HoloIn
		 */
		if (hasPluging("HoloAPI")) {
			holoAPI.init();
		}

		/**
		 * Chat Listener
		 */
		/*
		 * if(hasPluging("PermissionsEx")){ init.getServer().getPluginManager()
		 * .registerEvents(new ListenerChat(), init); }
		 */

		ListenerHub.enable();

	}

	private static boolean hasPluging(String s) {
		try {
			return Bukkit.getPluginManager().getPlugin(s).isEnabled();
		} catch (Exception e) {

		}
		return false;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("tienda")) {
			if (sender.getName().equalsIgnoreCase("CONSOLE")) {
				return true;
			}
			@SuppressWarnings("deprecation")
			Player pl = Bukkit.getPlayer(sender.getName());
			ListenAldeano.spawnAldeano("Tienda", pl.getLocation());
			pl.sendMessage("Aldeano spawneado");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("vestidor")) {
			if (sender.getName().equalsIgnoreCase("CONSOLE")) {
				return true;
			}
			@SuppressWarnings("deprecation")
			Player pl = Bukkit.getPlayer(sender.getName());
			ListenAldeano.spawnAldeano("Vestidor", pl.getLocation());
			pl.sendMessage("Aldeano spawneado");
			return true;
		}
		return false;
	}

	private static HashMap<String, Tutorial> tutoriales = new HashMap<>();

	public static void init(JavaPlugin plug) {
		plug.getServer().getPluginManager()
				.registerEvents(new ListenerHub(), plug);

		ConfigFile conf = new ConfigFile(plug.getDataFolder(), "Tutorial");
		FileConfiguration section = conf.getConfig();

		for (String s : section.getKeys(false)) {
			if (section.isConfigurationSection(s)) {
				Tutorial t = new Tutorial(section.getConfigurationSection(s));

				addTutorial(s, t);
			}
		}
	}

	public static void addTutorial(String s, Tutorial t) {
		CItems.addSign(new signTutorial(t));
		CItems.addNewsSign(new newSignTutorial(t));
		tutoriales.put(s, t);
	}

	public static Tutorial getTutorial(String s) {
		return tutoriales.get(s);
	}

	@Override
	public void cargar(Almacen nbt) {
		Almacen alm = nbt.getAlmacen("Tutorizados");
		for (Almacen n : alm.getAlmacenes()) {
			Tutorial t = tutoriales.get(n.getName());
			if (t != null) {
				for (String p : n.getKeys()) {
					t.tuto(p);
				}
			}
			alm.setAlmacen(n.getName(), n);
		}
		nbt.setAlmacen("Tutorizados", alm);
	}

	@Override
	public void guardar(Almacen nbt) {
		Almacen alm = nbt.getAlmacen("Tutorizados");
		for (Entry<String, Tutorial> s : tutoriales.entrySet()) {

			Almacen n = alm.getAlmacen(s.getKey());
			for (String p : s.getValue().getTutorizados()) {
				n.setBoolean(p, true);
			}
			alm.setAlmacen(s.getKey(), n);

		}
		nbt.setAlmacen("Tutorizados", alm);
	}

	public void onDisable() {
		central.guardar();
		ListenerHub.resetHearts();
	}
}
