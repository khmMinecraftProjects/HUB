package me.khmdev.HUB.Tutorial;

import org.bukkit.entity.Player;

import me.khmdev.HUB.Base;
import me.khmdev.MDS.Action;
import me.khmdev.MDS.MobDo;

public class MDSTurorial implements Action{

	@Override
	public void execute(MobDo mob, Player pl) {
		Object param=mob.getParams().get("tutorial");
		if(param!=null&&param instanceof String){
			Tutorial t=Base.getTutorial((String) param);
			if(t!=null){
				t.initTutorial(pl);
			}
		}
	}

}
