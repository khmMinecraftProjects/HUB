package me.khmdev.HUB.Listeners;

import java.util.HashMap;

import me.khmdev.APIAuxiliar.Players.NamesTags;
import me.khmdev.APIAuxiliar.Players.PexInterface;
import me.khmdev.APIBase.Almacenes.ConfigFile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerTags implements Listener {
	
	public ListenerTags(){
		for(Player pl:Bukkit.getServer().getOnlinePlayers()){
			taguear(pl);
		}
	}
	
	@EventHandler
	public void logeIn(PlayerJoinEvent e) {
		taguear(e.getPlayer());
	}


	
	private void taguear(Player pl) {
		if (hasPluging("PermissionsEx")) {
			String s = PexInterface.getPrefix(pl);
			if (s != "") {
				String n = ChatColor.translateAlternateColorCodes('&', s
						+ pl.getName());
				NamesTags.addPlayer(pl,
						n.length() > 16 ? n.substring(0, 15) : n);
			}
		
		}
		ChatColor clr = getColor(PexInterface.getGroups(pl.getName()));
		if (clr != null) {
			String n = clr + pl.getName();
			pl.setPlayerListName(
					n.length() > 16 ? n.substring(0, 15) : n);
		}
	}

	

	private ChatColor getColor(String[] s) {
		for (int i = 0; i < s.length; i++) {
			if (colours.containsKey(s[i])) {
				return colours.get(s[i]);
			}
		}
		return null;
	}

	private static boolean hasPluging(String s) {
		try {
			return Bukkit.getPluginManager().getPlugin(s).isEnabled();
		} catch (Exception e) {

		}
		return false;
	}

	private HashMap<String, ChatColor> colours = new HashMap<>();

	public ListenerTags(JavaPlugin plug) {
		ConfigFile conf = new ConfigFile(plug.getDataFolder(), "Colors");
		FileConfiguration section = conf.getConfig();
		for (String s : section.getKeys(false)) {
			if (section.isInt(s)) {
				int c = section.getInt(s);
				ChatColor[] clrs = ChatColor.values();
				ChatColor clr=(c<clrs.length&&c>=0)?clrs[c]:null;
				if (clr != null) {

					colours.put(s, clr);
				}
			}
		}
	}
}
