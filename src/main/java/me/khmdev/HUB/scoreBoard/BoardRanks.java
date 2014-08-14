package me.khmdev.HUB.scoreBoard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import me.khmdev.APIAuxiliar.Players.APIPermisons;
import me.khmdev.APIAuxiliar.ScoreBoard.Board;
import me.khmdev.APIAuxiliar.ScoreBoard.ObjetiveData;
import me.khmdev.APIAuxiliar.ScoreBoard.getStringConst;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class BoardRanks extends Board {

	public BoardRanks(String name) {
		super(name);
	}

	private HashMap<String, List<String>> ranks = new HashMap<>();

	private List<String> ranksC = new LinkedList<>();

	public void addRank(String r) {
		ranks.put(r, new LinkedList<String>());
	}

	public void actualizar() {
		ranks.clear();
		for (String s : ranksC) {
			List<String> onl = APIPermisons.getOnlines(s);
			ranks.put(s, onl);
		}
	}

	public void actualizar(Player p) {
		if (title == null || title == null) {
			return;
		}
		String s = title.getString(p);
		Scoreboard board = p.getScoreboard();
		Objective objective = board.getObjective(s);
		if (objective != null) {
			objective.unregister();
		}
		objective = board.registerNewObjective(s, s);
		for (ObjetiveData o : objs) {
			String obj=o.getTitle(p);
			if (obj == null) {
				o.setTitle(new getStringConst(getWhite()));
				obj=o.getTitle(p);
			}
			Score scr = objective.getScore(obj);
			scr.setScore(o.getF(p));

		}
		int i=-16;
		for (Entry<String, List<String>> ent : ranks.entrySet()) {
			if (ent.getValue().size() != 0) {
				
				i--;

				set(objective, "&4 " + ent.getKey() + " online", i);
				i--;
				for (String e : ent.getValue()) {
					set(objective, e, i);
					i--;
				}
			}

		}
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
	}
	private void set(Objective objective, String s, int i) {
		s = ChatColor.translateAlternateColorCodes('&', s);
		if (s.length() > 16) {
			s = s.substring(0, 16);
		}
		Score scr = objective.getScore(s);

		scr.setScore(i);
	}
	public void setRanks(List<String> ranks2) {
		ranksC = ranks2;
	}
}
