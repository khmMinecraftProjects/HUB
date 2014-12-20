package me.khmdev.HUB.Listeners;

import me.khmdev.APIBase.Auxiliar.UsuariosOcupados;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ListenerChat implements Listener{
	@EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
		if(UsuariosOcupados.contain(event.getPlayer())){
			return;
		}

		PermissionUser user=PermissionsEx.getUser(
					event.getPlayer().getName());
		PermissionGroup g=PermissionsEx.getPermissionManager()
				.getDefaultGroups(event.getPlayer()
						.getWorld().getName()).get(0);
		if(!(g!=null?
				contain(user.getGroupsNames(), g.getName()):true)){
		event.setMessage(
				ChatColor.translateAlternateColorCodes('&'
						,"&F"+event.getMessage()));
		}
    }
	public boolean contain(String[] a,String s){
		for (int i = 0; i < a.length; i++) {
			if(a[i].equalsIgnoreCase(s)){return true;}
		}
		return false;
	}
}
