package me.khmdev.HUB.Tutorial;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerInteractEvent;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomSign;
import me.khmdev.HUB.Base;

public class signTutorial extends CustomSign{
	Tutorial tutorial;
	public signTutorial(Tutorial t){
		tutorial=t;
	}
	@Override
	public void execute(PlayerInteractEvent event) {
		if(tutorial.esTuto(event.getPlayer().getName())){
			event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&',
							"&CYa has hecho el tutorial"));
		}else{
			Base.run(new TutorialAction(tutorial,event.getPlayer()));
		}
	}

	@Override
	public boolean isthis(Sign s) {
		return (s.getLines()[0].equalsIgnoreCase("")&&
				s.getLines()[1].equalsIgnoreCase(ChatColor
						.translateAlternateColorCodes('&',"&A<INICIAR>"))&&
				s.getLines()[2].equalsIgnoreCase(  ChatColor
						.translateAlternateColorCodes('&',"&A<TUTORIAL>"))&&
				s.getLines()[3].equalsIgnoreCase(ChatColor
						.translateAlternateColorCodes('&',"&A<"+tutorial.getId()+">")));
	}

}
