package me.khmdev.HUB.WearShop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import me.khmdev.APIBase.Almacenes.Almacen;
import me.khmdev.APIBase.Almacenes.Datos;
import me.khmdev.APIBase.Auxiliar.Auxiliar;

public class PlayerDresser implements Datos {
	private static HashMap<String, List<String>> dress = new HashMap<>();

	public static void addDress(String pl, String d) {
		List<String> l = dress.get(pl);
		if (l == null) {
			l = new LinkedList<>();
			dress.put(pl, l);
		}
		l.add(d);
	}

	public static boolean contain(String pl, String d) {
		List<String> l = dress.get(pl);

		if (l == null) {
			return false;
		}
		return l.contains(d);
	}

	@Override
	public void cargar(Almacen nbt) {
		Almacen alm = nbt.getAlmacen("Dress");
		for (String n : alm.getKeys()) {
			dress.put(n, String2List(alm.getString(n)));
		}
	}

	@Override
	public void guardar(Almacen nbt) {
		Almacen alm = nbt.getAlmacen("Dress");
		for (Entry<String, List<String>> ent : dress.entrySet()) {
			alm.setString(ent.getKey(), List2String(ent.getValue()));
		}
		nbt.setAlmacen("Dress", alm);
	}

	private String List2String(List<String> l) {
		String s = "";
		for (String ss : l) {
			s += ss + ";";
		}
		return s;
	}

	private List<String> String2List(String s) {
		List<String> l = new LinkedList<>();
		int i = 0;
		String a = Auxiliar.getSeparate(s, i, ';');
		while (a.length() != 0) {
			l.add(a);
			i++;
			a = Auxiliar.getSeparate(s, i, ';');
		}
		return l;
	}
}
