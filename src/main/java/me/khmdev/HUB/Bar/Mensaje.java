package me.khmdev.HUB.Bar;

import org.bukkit.ChatColor;

public class Mensaje {
	private String txt;
	private float time;
	private int live;
	public Mensaje(String tx,int t,int l){
		txt=ChatColor.translateAlternateColorCodes('&',tx);
		time=t;//*1000;
		live=l;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public float getTime() {
		return time;
	}
	public void setTime(float time) {
		this.time = time;
	}
	public int getLive() {
		return live;
	}
	public void setLive(int live) {
		this.live = live;
	}
	
}
