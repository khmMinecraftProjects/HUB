package me.khmdev.HUB.Tutorial;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerInteractEvent;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomSign;
import me.khmdev.HUB.Base;

public class signTutorial extends CustomSign{

	@Override
	public void execute(PlayerInteractEvent event) {
		if(Base.esTuto(event.getPlayer().getName())){
			event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&',
							"&CYa has hecho el tutorial"));
		}else{
			Base.run(new Tutorial(event.getPlayer()));
		}
	}

	@Override
	public boolean isthis(Sign s) {
		
		
		return (s.getLines()[0].equalsIgnoreCase("")&&
				s.getLines()[1].equalsIgnoreCase(ChatColor
						.translateAlternateColorCodes('&',"&A<INICIAR>"))&&
				s.getLines()[2].equalsIgnoreCase(  ChatColor
						.translateAlternateColorCodes('&',"&A<TUTORIAL>"))&&
				s.getLines()[3].equalsIgnoreCase(  ""));
	}

}
