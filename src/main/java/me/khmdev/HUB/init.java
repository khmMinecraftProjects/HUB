package me.khmdev.HUB;

import me.khmdev.HUB.Tutorial.MDSTurorial;
import me.khmdev.HUB.lang.Lang;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;



public class init extends JavaPlugin{
	private Base base;


	public void onEnable() {
		if (!hasPluging("APIEconomy")||
				!hasPluging("APIAuxiliar")) {
			getLogger().severe(
					getName()
							+ " se desactivo debido ha que no encontro la API");
			setEnabled(false);
			return;
		}
		Lang.init(this);
		base=new Base(this);
		if (hasPluging("MDS")){
			me.khmdev.MDS.base.addAction("tutorial", new MDSTurorial());
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (base.onCommand(sender, cmd, label, args)) {
			return true;
		}

		return false;
	}


	public static boolean hasPluging(String s) {
		try {
			return Bukkit.getPluginManager().getPlugin(s).isEnabled();
		} catch (Exception e) {

		}
		return false;
	}
	@Override
	public void onDisable(){
		if(base!=null){base.onDisable();}
	}

}
