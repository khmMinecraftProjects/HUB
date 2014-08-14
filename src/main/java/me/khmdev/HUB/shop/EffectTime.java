package me.khmdev.HUB.shop;

import me.khmdev.APIAuxiliar.Effects.ParticleEffect;
import me.khmdev.APIAuxiliar.Effects.SendParticles;
import me.khmdev.APIBase.Auxiliar.runKill;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EffectTime extends runKill{
	private Player pl;
	private long time,a;
	private ParticleEffect efc;
	public EffectTime(ParticleEffect e,Player p,long tim){
		pl=p;
		time=tim*1000;
		a=System.currentTimeMillis();
		efc=e;
	}
	@Override
	public void run() {
		long now=System.currentTimeMillis();
		if(time>(now-a)){
			effect();
		}else{
			kill();
		}
	}

	public void effect(){
		SendParticles.send(efc, pl.getLocation().add(0,-2,0),
				5, 5, new Vector(1, 1, 1));;
	}

}
