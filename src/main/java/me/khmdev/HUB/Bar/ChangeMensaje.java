package me.khmdev.HUB.Bar;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIAuxiliar.Effects.BossBar;
import me.khmdev.HUB.scoreBoard.CargaBoard;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ChangeMensaje extends BukkitRunnable {
	private List<Mensaje> mensajes = new LinkedList<>();
	private Iterator<Mensaje> iter;
	private Mensaje actual;
	private long timer=0;
	private boolean death = false;
	private int id = -1;
	
	public void setId(int i){
		id=i;
	}
	public ChangeMensaje(List<Mensaje> mensaje) {
		mensajes=mensaje;
		
		iter = mensajes.iterator();
		if (iter.hasNext()) {
			setMensaje(iter.next());
		} else {
			death = true;
		}
	}

	@Override
	public void run() {
		if (death) {
			kill();
			return;
		}timer++;
		//long now = System.currentTimeMillis();
		if (
				timer>=actual.getTime()// <= (now-timer)
				) {
			if (!iter.hasNext()) {
				iter = mensajes.iterator();
				if (!iter.hasNext()) {
					kill();
					return;
				} 
				
			}
			setMensaje(iter.next());
		}
	}

	private void setMensaje(Mensaje msg){
		actual =msg;
		timer = 0;//System.currentTimeMillis();
		BossBar.sendBarToGroup(msg.getTxt(), msg.getLive(),
				CargaBoard.getPlayers());
	}

	
	private void kill() {
		if (id != -1) {
			Bukkit.getScheduler().cancelTask(id);
		}
	}

}
