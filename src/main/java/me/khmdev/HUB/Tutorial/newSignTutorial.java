package me.khmdev.HUB.Tutorial;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomSign;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.NewsCustomSign;

public class newSignTutorial extends NewsCustomSign{
	private signTutorial sign=new signTutorial();
	@Override
	public CustomSign getSign(Sign event) {
		return sign;
	}

	@Override
	public boolean isthis(SignChangeEvent event) {
		if(event.getLines()[0].equalsIgnoreCase("<Tutorial>")&&
				event.getPlayer().hasPermission("tutorial.createsign"))
		{
			event.setLine(0, "");
			event.setLine(1,ChatColor
					.translateAlternateColorCodes('&',"&A<INICIAR>"));
			event.setLine(2,ChatColor
					.translateAlternateColorCodes('&',"&A<TUTORIAL>"));
			event.setLine(3, "");

			return true;
		}
		return false;
	}

}
