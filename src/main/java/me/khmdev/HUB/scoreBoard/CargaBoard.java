package me.khmdev.HUB.scoreBoard;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIAuxiliar.Players.APIPermisons;
import me.khmdev.APIAuxiliar.ScoreBoard.Functor;
import me.khmdev.APIAuxiliar.ScoreBoard.FunctorString;
import me.khmdev.APIAuxiliar.ScoreBoard.IBoard;
import me.khmdev.APIAuxiliar.ScoreBoard.ObjetiveData;
import me.khmdev.APIAuxiliar.ScoreBoard.getCash;
import me.khmdev.APIAuxiliar.ScoreBoard.getConstant;
import me.khmdev.APIAuxiliar.ScoreBoard.getPermision;
import me.khmdev.APIAuxiliar.ScoreBoard.getStringCash;
import me.khmdev.APIAuxiliar.ScoreBoard.getStringConst;
import me.khmdev.APIAuxiliar.ScoreBoard.runBoard;
import me.khmdev.APIBase.Almacenes.ConfigFile;
import me.khmdev.APIBase.Auxiliar.Auxiliar;


import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CargaBoard {
	private static BoardHub board;
	private static List<String> ranks=new LinkedList<>();
	public static void cargar(JavaPlugin plugin) {
		ConfigFile conf = new ConfigFile(plugin.getDataFolder(), "Board");
		FileConfiguration section = conf.getConfig();
		for (String key : section.getKeys(false)) {
			if (section.isList(key)) {

				add2Board(key, section.getStringList(key));
				break;
			}
		}
		if(board==null){board=new BoardHub("");return;}
		plugin.getServer().getPluginManager()
		.registerEvents(new ListenBoard(board),plugin);
		runBoard.addBoard(board);
	}
	

	
	
	private static void add2Board(String k, List<String> list) {
		
		List<ObjetiveData> l=new LinkedList<>();
		for(String s:list){
			ObjetiveData o=getObj(s);
			if(o!=null){l.add(o);}
		}
		if(ranks.size()!=0 && APIPermisons.PEXisEnable()){
			board=new BoardRanks(k);
			board.set(l);
			((BoardRanks) board).setRanks(ranks);
		}else{
			board=new BoardHub(k);
			board.set(l);
		}
		
	}
	
	private static ObjetiveData getObj(String s){
		ObjetiveData obj=null;
		String name=Auxiliar.getSeparate(s, 0, ';');
		String func=Auxiliar.getSeparate(s, 1, ';');
		int id=Auxiliar.getNatural(func, -1);
		Functor f=new getConstant(0);
		if(id!=-1){
			f=new getConstant(id);
		}else if(func.equalsIgnoreCase("cash")){
			f=new getCash();
		}
		FunctorString str=new getStringConst(
				ChatColor.translateAlternateColorCodes('&',name));
		switch (name) {
		case "rango":
			str=new getPermision();
			break;
		case "cash":
			str=new getStringCash();
			break;
		case "":
			str=null;
			break;
		default:
			break;
		}
		if(name.contains("onlines_")){
			String r=name.substring(8);
			ranks.add(r);
			return null	;
		}
		obj=new ObjetiveData(
				str, f);
		return obj;
	}

	public static IBoard getBoard() {
		return board;
	}

	public static void setBoard(BoardHub board) {
		CargaBoard.board = board;
	}

	public static List<Player> getPlayers() {
		return board.getPlayers();
	}
}
