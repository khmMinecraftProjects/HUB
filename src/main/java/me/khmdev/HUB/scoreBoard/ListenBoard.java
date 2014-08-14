package me.khmdev.HUB.scoreBoard;

import me.khmdev.APIAuxiliar.ScoreBoard.IBoard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenBoard implements Listener{
	IBoard board;
	public ListenBoard(IBoard b){
		board=b;
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			board.addPlayer(p);
		}
	}
	@EventHandler
	public void logeIn(PlayerJoinEvent e) {
		board.addPlayer(e.getPlayer());
	}
}
