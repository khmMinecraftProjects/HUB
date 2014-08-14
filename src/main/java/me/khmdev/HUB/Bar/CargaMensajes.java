package me.khmdev.HUB.Bar;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIBase.Almacenes.ConfigFile;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CargaMensajes {

	public static void cargar(JavaPlugin plug) {
		ConfigFile conf = new ConfigFile(plug.getDataFolder(), "Mensajes");
		FileConfiguration section = conf.getConfig();
		List<Mensaje> mensajes = new LinkedList<>();
		Iterator<String> iter = section.getKeys(false).iterator();
		while (iter.hasNext()) {
			String string = (String) iter.next();
			if (section.isConfigurationSection(string)) {
				addMensaje(mensajes, section.getConfigurationSection(string));
			}
		}

		ChangeMensaje run = new ChangeMensaje(mensajes);

		int idd = Bukkit.getScheduler().scheduleSyncRepeatingTask(plug, run,
				0L, 1);
		run.setId(idd);
	}
	
	private static void addMensaje(List<Mensaje> l, ConfigurationSection s) {
		//int idd=Auxiliar.getNatural(s.getName(), -1);
		//if(idd==-3){return;}
		String txt="";int t=1,li=0;
		if(s.isString("txt")){txt=s.getString("txt");}
		if(s.isInt("time")){t=s.getInt("time");}
		if(s.isInt("live")){li=s.getInt("live");}

		Mensaje msg=new Mensaje(txt,t,li);
		//l.set(idd, msg);
		l.add(msg);
	}
	

}
