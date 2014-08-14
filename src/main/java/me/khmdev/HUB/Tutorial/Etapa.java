package me.khmdev.HUB.Tutorial;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Etapa {
	private Location pos;
	private boolean move,head;
	private int time;
	private List<String> msg;
	public Etapa(Location p,boolean m,boolean h, int t,List<String> s){
		pos=p;move=m;time=t*1000;setHead(h);msg=s;
	}
	public Location getPos() {
		return pos;
	}
	public void setPos(Location pos) {
		this.pos = pos;
	}
	public boolean isMove() {
		return move;
	}
	public void setMove(boolean move) {
		this.move = move;
	}

	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public boolean isHead() {
		return head;
	}
	public void setHead(boolean head) {
		this.head = head;
	}
	public List<String> getMsg() {
		return msg;
	}
	public void sendMsg(Player pl){
		for (String s : msg) {
			pl.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}
	}
}
