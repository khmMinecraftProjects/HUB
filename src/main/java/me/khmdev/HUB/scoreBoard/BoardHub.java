package me.khmdev.HUB.scoreBoard;

import org.bukkit.entity.Player;

import me.khmdev.APIAuxiliar.ScoreBoard.Board;
import me.khmdev.APIAuxiliar.ScoreBoard.FunctorString;
import me.khmdev.HUB.Listeners.ListenerHub;

public class BoardHub extends Board{

	public BoardHub(FunctorString name) {
		super(name);
	}
	
	public BoardHub(String name) {
		super(name);
	}

	@Override
	public void addScoreBoard(Player pl) {
		super.addScoreBoard(pl);
		ListenerHub.setUp(pl);
	}
	@Override
	public void removeScoreBoard(Player pl) {
		super.removeScoreBoard(pl);
		ListenerHub.resetHearts();
	}
}
