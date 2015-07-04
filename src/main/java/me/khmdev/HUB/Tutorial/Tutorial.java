package me.khmdev.HUB.Tutorial;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIBase.Auxiliar.Auxiliar;
import me.khmdev.HUB.Base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Tutorial {
	private List<Etapa> etapas = new LinkedList<>();
	private int cash = 200;
	private String id;
	private List<String> tutorizados = new LinkedList<>();

	public Tutorial(ConfigurationSection section) {
		id = section.getName();
		if (section.isInt("cash")) {
			setCash(section.getInt("cash"));
		}
		for (String key : section.getKeys(false)) {
			if (section.isConfigurationSection(key)) {
				addEtapa(section.getConfigurationSection(key));
			}
		}
	}

	public List<String> getTutorizados() {
		return tutorizados;
	}

	public double getCash() {
		return cash;
	}

	public String getId() {
		return id;
	}

	public Etapa getEtapa(int id) {
		return etapas.size() > id ? etapas.get(id) : null;
	}

	private void addEtapa(ConfigurationSection section) {
		String pos = section.getString("Posicion");
		Location location = null;
		if (pos != null) {
			World world = getWorld(Auxiliar.getSeparate(pos, 0, ';'));

			double x = Auxiliar.getDouble(Auxiliar.getSeparate(pos, 1, ';'), 0), y = Auxiliar
					.getDouble(Auxiliar.getSeparate(pos, 2, ';'), 0), z = Auxiliar
					.getDouble(Auxiliar.getSeparate(pos, 3, ';'), 0);
			float pitch = (float) Auxiliar.getDouble(
					Auxiliar.getSeparate(pos, 4, ';'), 0), yaw = (float) Auxiliar
					.getDouble(Auxiliar.getSeparate(pos, 5, ';'), 0);
			location = new Location(world, x, y, z, yaw, pitch);
		}
		boolean move = getBoolean(section, "move", true), head = getBoolean(
				section, "head", true);

		int time = getInt(section, "time", 5);

		List<String> l = new LinkedList<>();
		if (section.isList("Mensajes")) {
			l = section.getStringList("Mensajes");
		}
		etapas.add(new Etapa(location, move, head, time, l));
	}

	private static World getWorld(String s) {

		World w = Bukkit.getWorld(s);
		return w == null ? Bukkit.getWorlds().get(0) : w;

	}

	private static boolean getBoolean(ConfigurationSection section, String s,
			boolean d) {

		if (section.isBoolean(s)) {
			return section.getBoolean(s);
		}
		return d;
	}

	private static int getInt(ConfigurationSection section, String s, int d) {
		if (section.isInt(s)) {
			return section.getInt(s);
		}
		return d;
	}

	public void setCash(int c) {
		cash = c;
	}

	public void tuto(String player) {
		tutorizados.add(player);
	}

	public boolean esTuto(String player) {
		return tutorizados.contains(player);
	}
	public void initTutorial(Player player) {
		if(!player.hasPermission("tutorial.repetible")&&esTuto(player.getName())){
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&',
							"&CYa has hecho el tutorial"));
		}else{
			Base.run(new TutorialAction(this,player));
		}
	}
}
