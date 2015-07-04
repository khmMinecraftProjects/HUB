package me.khmdev.HUB.Tutorial;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIBase.Auxiliar.runKill;
import me.khmdev.APIEconomy.ConstantesEconomy;
import me.khmdev.APIEconomy.Own.APIEconomy;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TutorialAction extends runKill implements Listener {
	public static List<String> freeze = new LinkedList<>();
	public static List<String> freezeHead = new LinkedList<>();

	private int p = 0;
	private Etapa actual = null;
	private long time = 0;
	private Player player;
	private Location first;
	private Tutorial tutorial;

	public TutorialAction() {
	}

	@EventHandler
	public void move(PlayerMoveEvent event) {
		Location l = event.getTo().clone(), from = event.getFrom().clone();

		if (freeze.contains(event.getPlayer().getName())) {
			l.setX(from.getX());
			l.setY(from.getY());
			l.setZ(from.getZ());

		}

		if (freezeHead.contains(event.getPlayer().getName())) {
			l.setPitch(from.getPitch());
			l.setYaw(from.getYaw());
		}
		if (event.getTo().distance(l) != 0
				|| (event.getTo().getPitch() != l.getPitch() || event.getTo()
						.getYaw() != l.getYaw())) {
			event.getPlayer().teleport(l);
		}
	}

	public TutorialAction(Tutorial t, Player pl) {
		tutorial = t;
		player = pl;
		p = 0;
		time = System.currentTimeMillis();
		actual = tutorial.getEtapa(p);
		first = player.getLocation();
		if (actual != null) {
			if (actual.getPos() != null) {
				player.teleport(actual.getPos());
			} else {
				player.teleport(first);
			}
			time = System.currentTimeMillis();
			actual.sendMsg(player);

			if (actual.isMove()) {
				freeze.remove(player.getName());
			} else {
				if (!freeze.contains(player.getName())) {
					freeze.add(player.getName());
				}
			}

			if (actual.isHead()) {
				freezeHead.remove(player.getName());
			} else {
				if (!freezeHead.contains(player.getName())) {
					freezeHead.add(player.getName());
				}
			}
		}
	}

	@Override
	public void kill() {
		freeze.remove(player.getName());
		freezeHead.remove(player.getName());
		tutorial.tuto(player.getPlayer().getName());
		APIEconomy.addCash(player.getName(), tutorial.getCash());
		player.sendMessage("Has ganado " + tutorial.getCash()
				+ ConstantesEconomy.UM + " por haber hecho el tutorial");
		player.teleport(first);
		super.kill();
	}

	@Override
	public void run() {
		if (actual == null) {
			kill();
			return;
		}
		long now = System.currentTimeMillis();
		if (actual.getTime() < (now - time)) {
			p++;
			actual = tutorial.getEtapa(p);
			if (actual != null) {
				if (actual.getPos() != null) {
					player.teleport(actual.getPos());
				} else {
					player.teleport(first);
				}
				actual.sendMsg(player);
				time = System.currentTimeMillis();
				if (actual.isMove()) {
					freeze.remove(player.getName());
				} else {
					if (!freeze.contains(player.getName())) {
						freeze.add(player.getName());
					}
				}

				if (actual.isHead()) {
					freezeHead.remove(player.getName());
				} else {
					if (!freezeHead.contains(player.getName())) {
						freezeHead.add(player.getName());
					}
				}
			}

		}
	}

}
