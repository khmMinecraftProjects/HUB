package me.khmdev.HUB;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CItems;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomInventory;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.ItemOpenInventory;
import me.khmdev.APIAuxiliar.Players.APIPlayer;
import me.khmdev.APIBase.API;
import me.khmdev.APIBase.Almacenes.Almacen;
import me.khmdev.APIBase.Almacenes.Datos;
import me.khmdev.APIBase.Auxiliar.runKill;
import me.khmdev.HUB.Bar.CargaMensajes;
import me.khmdev.HUB.HoloGraph.holoAPI;
import me.khmdev.HUB.Listeners.ListenerHub;
import me.khmdev.HUB.Listeners.ListenerTags;
import me.khmdev.HUB.Tutorial.CargarTutorial;
import me.khmdev.HUB.Tutorial.Tutorial;
import me.khmdev.HUB.Tutorial.newSignTutorial;
import me.khmdev.HUB.Tutorial.signTutorial;
import me.khmdev.HUB.WearShop.CargarShop;
import me.khmdev.HUB.WearShop.PlayerDresser;
import me.khmdev.HUB.scoreBoard.CargaBoard;
import me.khmdev.HUB.shop.ItemsCust;
import me.khmdev.HUB.shop.ListenAldeano;
import me.khmdev.HUB.shop.ShopItem;
import me.khmdev.HUB.teleports.CargarComands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Base implements Datos {

	private static JavaPlugin plug = null;

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

	private static ItemOpenInventory item;
	private static CustomInventory inv;

	public static Inventory getInventory() {
		return inv.getInventory();
	}

	public static void init() {
		inv = new CustomInventory("Tienda hub");

		CItems.addItem(ItemsCust.maria);
		CItems.addItem(ItemsCust.esteroides);
		CItems.addItem(ItemsCust.Cocacola);
		CItems.addItem(ItemsCust.Cupido);
		CItems.addItem(ItemsCust.Creeperniano);
		
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

		item = new ItemOpenInventory(new ItemStack(Material.ACACIA_STAIRS),
				inv.getInventory());

		CItems.addItem(item);
		
		

	}

	public Base(JavaPlugin init) {

		/**
		 * Own
		 */
		ListenerHub.addStandarItem(APIPlayer.getEnabler());

		init.getServer().getPluginManager()
		.registerEvents(new ListenerHub(),init);
		
		plug = init;
		API.getInstance().getCentral().insertar(this);
		
		/**
		 * Tutorial
		 */
		init.getServer().getPluginManager()
				.registerEvents(new Tutorial(), init);
		CItems.addNewsSign(new newSignTutorial());
		CItems.addSign(new signTutorial());
		CargarTutorial.init(init);

		/**
		 * Tags
		 */
		if(hasPluging("PermissionsEx")){
		init.getServer().getPluginManager()
				.registerEvents(new ListenerTags(init), init);}
		
		/**
		 * ScoreBoard
		 */
		CargaBoard.cargar(plug);
		
		/**
		 * Boss Bar
		 */
		CargaMensajes.cargar(init);


		/**
		 * Aldeano Shop
		 */
		init.getServer().getPluginManager()
				.registerEvents(new ListenAldeano(), init);
		init();
		ListenAldeano.addVillager("Tienda",
				inv.getInventory());

		/**
		 * Dress Shop
		 */
		CargarShop.cargar(init);
		ListenAldeano.addVillager("Vestidor", CargarShop.getInventory());
		API.getInstance().getCentral().insertar(new PlayerDresser());
		

		
		/**
		 * Command Item
		 */
		CargarComands.cargar(init);
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
		if(hasPluging("PermissionsEx")){
			init.getServer().getPluginManager()
			.registerEvents(new ListenerChat(), init);
		}
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
			ListenAldeano.spawnAldeano("Tienda",pl.getLocation());
			pl.sendMessage("Aldeano spawneado");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("vestidor")) {
			if (sender.getName().equalsIgnoreCase("CONSOLE")) {
				return true;
			}
			@SuppressWarnings("deprecation")
			Player pl = Bukkit.getPlayer(sender.getName());
			ListenAldeano.spawnAldeano("Vestidor",pl.getLocation());
			pl.sendMessage("Aldeano spawneado");
			return true;
		}
		return false;
	}

	private static List<String> tutorizados = new LinkedList<>();

	@Override
	public void cargar(Almacen nbt) {
		Almacen alm = nbt.getAlmacen("Tutorizados");
		for (String s : alm.getKeys()) {
			tutorizados.add(s);
		}
	}

	@Override
	public void guardar(Almacen nbt) {
		Almacen alm = nbt.getAlmacen("Tutorizados");
		for (String s : tutorizados) {
			alm.setBoolean(s, true);
		}
		nbt.setAlmacen("Tutorizados", alm);
	}

	public static void tuto(String player) {
		tutorizados.add(player);
	}

	public static boolean esTuto(String player) {
		return tutorizados.contains(player);
	}

}
