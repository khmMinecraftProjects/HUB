package me.khmdev.HUB.Listeners;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIAuxiliar.Effects.ParticleEffect;
import me.khmdev.APIAuxiliar.Effects.SendParticles;
import me.khmdev.APIAuxiliar.Players.ItemVisible;
import me.khmdev.HUB.Base;
import me.khmdev.HUB.scoreBoard.CargaBoard;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ListenerHub implements Listener {
	private static List<ItemStack> noDrop=new LinkedList<>();
	private static List<ItemStack> standarItems=new LinkedList<>();

	public ListenerHub(){
		enable();
	}
	
	public static void enable(){
		for(Player p:Bukkit.getServer().getOnlinePlayers()){
			setUp(p);
		}
	}

	@SuppressWarnings("deprecation")
	public static void setUp(Player p){
		p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,
				Integer.MAX_VALUE, 0));
		p.setMaxHealth(16);
		if(p.hasPermission("doblesalto.usar")){
			p.setAllowFlight(true);
		}
		addStandarItems(p);
	}
	
	public static void addStandarItem(ItemStack itemStack){
		noDrop.add(itemStack);
		standarItems.add(itemStack);
	}
	public static void addNDItem(ItemStack itemStack){
		noDrop.add(itemStack);
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void changeGameMode(final PlayerGameModeChangeEvent e) {
		
		if (e.getPlayer().hasPermission("doblesalto.usar")) {
			Bukkit.getScheduler()
			.runTaskLaterAsynchronously(Base.getInstance(), 
					new BukkitRunnable() {
				
				@Override
				public void run() {
					e.getPlayer().setAllowFlight(true);
					
				}
			}, 20);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onquit(PlayerQuitEvent e){
		e.getPlayer().setMaxHealth(20);

	}
	
	@SuppressWarnings("deprecation")
	public static void resetHearts(){
		for(Player pl:Bukkit.getServer().getOnlinePlayers()){
			pl.setMaxHealth(20);
		}
	}
	private static final boolean limit=true;
    @EventHandler
    public void onItemDrop (PlayerDropItemEvent e) {
    	
    	if(e.getPlayer().hasPermission("HubPlugin.dropItem")
    			){
    		return;
    	}
    	if(limit){e.setCancelled(true);}
    	ItemStack itm=e.getItemDrop().getItemStack();
    	if(itm.getAmount()!=1){itm=itm.clone();itm.setAmount(1);}
    	if(noDrop.contains(itm)){
    		e.setCancelled(true);
    	}
    }
	@EventHandler
	public void logeIn(PlayerJoinEvent e) {
		setUp(e.getPlayer());
	}
	
	@SuppressWarnings("deprecation")
	public static void addStandarItems(Player pl){
		pl.getInventory().remove(ItemVisible.inv);
		pl.getInventory().remove(ItemVisible.vis);
		
		for (ItemStack item: standarItems) {
			pl.getInventory().remove(item);
			pl.getInventory().addItem(item);
		}
		pl.updateInventory();
	}

	@EventHandler
	public void onFlightAttempt(PlayerToggleFlightEvent event) {
		/*
		float y = event.getPlayer().getFlySpeed();

		for (PotionEffect pot : event.getPlayer().getActivePotionEffects()) {
			if (pot.getType().equals(PotionEffectType.JUMP)) {
				y += pot.getAmplifier() / 8 + 0.46;
			}
		}
		 */
		double z=event.getPlayer().getVelocity().getZ();
		double x=event.getPlayer().getVelocity().getX();
		if (!event.getPlayer().isFlying()
				&& event.getPlayer().getGameMode() != GameMode.CREATIVE
				&& event.getPlayer().hasPermission("doblesalto.usar")) {

			event.getPlayer().setVelocity(
					event.getPlayer().getVelocity()
					.add(new Vector(x*10, 0, z*10)));
			SendParticles.send(ParticleEffect.LARGE_EXPLODE,
					event.getPlayer().getLocation().add(0,-4,0), 1, 5, 
						new Vector(0,0,0));
			event.getPlayer().getWorld()
			.playEffect(event.getPlayer().getLocation()
					, Effect.BOW_FIRE, 3);
			event.setCancelled(true);

		}

	}
	
	@EventHandler
    public void death(PlayerDeathEvent e) {
		for(ItemStack it:noDrop){
			e.getDrops().remove(it);
		}
	}

	@EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
		if(CargaBoard.getBoard()!=null&&
				!CargaBoard.getBoard().containPlayer(e.getPlayer())){return;}
    	setUp(e.getPlayer());
    }
}
