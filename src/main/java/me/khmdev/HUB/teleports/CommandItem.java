package me.khmdev.HUB.teleports;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;

public class CommandItem extends CustomItem {
	private PluginCommand cmd;
	private String name,paramsS="", user = null;
	private String[] params;

	public CommandItem(ItemStack it, String cm) {
		super(it);
		name = getCommand(cm);
		cmd = Bukkit.getServer().getPluginCommand(name);
		paramsS=getParamsS(cm);
		params = getParams(cm);
	}

	private String getParamsS(String cm) {
		String s="";
		for (int i = 0; i < cm.length(); i++) {
			char c = cm.charAt(i);
			s+=c;
			if (c == ' ') {
				break;
			}
		}
		return cm.substring(s.length());
	}

	public CommandItem(ItemStack it, String cm, String usr) {
		super(it);
		user = usr;
		name = getCommand(cm);
		cmd = Bukkit.getServer().getPluginCommand(name);
		paramsS=getParamsS(cm);
		params = getParams(cm);
	}

	@Override
	public void execute(InventoryClickEvent event) {
		event.setCancelled(true);

		CommandSender pl=null;

		if(user!=null){
			if(user.equalsIgnoreCase("console")){
				pl=Bukkit.getConsoleSender();
			}else{
				pl=Bukkit.getServer().getPlayer(user);
			}
		}
		if(pl==null){
			pl=Bukkit.getServer().getPlayer(
					event.getWhoClicked().getUniqueId());
		}
		if (cmd == null) {
			String pr=paramsS.replace("%PL", event.getWhoClicked()
					.getName());
			Bukkit.getServer()
			.dispatchCommand(pl, 
					name+" "+pr);

			return;
		}
		
		cmd.execute(pl, name, getChange(params, 
				event.getWhoClicked().getName()));
	}

	private String[] getChange(String[] s, String pl) {
		String[] cop = new String[s.length];
		for (int i = 0; i < s.length; i++) {
			cop[i] = s[i];
			if (cop[i].equalsIgnoreCase("%PL")) {
				cop[i] = pl;
			}
		}
		return cop;
	}

	private String getCommand(String s) {
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ' ') {
				return r;
			}
			r += c;
		}
		return r;
	}

	private String[] getParams(String s) {
		String r = "";
		boolean b = false;
		List<String> l = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ' ') {
				if (!b) {
					b = true;
				} else {
					l.add(r);
				}
				r = "";
			} else {
				if (b) {
					r += c;
				}
			}
		}
		l.add(r);
		String[] rr = new String[l.size()];
		int o = 0;
		for (String string : l) {
			rr[o] = string;
			o++;
		}
		return rr;
	}

	@Override
	public void execute(PlayerInteractEvent event) {

	}

}
