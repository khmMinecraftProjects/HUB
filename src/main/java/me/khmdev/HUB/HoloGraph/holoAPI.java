package me.khmdev.HUB.HoloGraph;


import me.khmdev.APIEconomy.Own.APIEconomy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.dsh105.holoapi.HoloAPI;
import com.dsh105.holoapi.api.TagFormat;



public class holoAPI {
	public static void init(){
		if(hasPluging("HoloAPI")){
			HoloAPI.getTagFormatter()
			.addFormat("%apiget%", new TagFormat() {
				
				@Override
				public String getValue(Player pl) {
					return ""+APIEconomy.getCash(pl.getName());
				}
			});
		
		}
	}
	
	private static boolean hasPluging(String s) {
		try {
			return Bukkit.getPluginManager().getPlugin(s).isEnabled();
		} catch (Exception e) {

		}
		return false;
	}
}
