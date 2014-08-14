 package me.khmdev.HUB.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.khmdev.APIAuxiliar.Effects.ParticleEffect;
import me.khmdev.APIAuxiliar.Inventory.CustomInventorys.CustomItem;
import me.khmdev.APIAuxiliar.Players.AuxPlayer;
import me.khmdev.APIBase.Auxiliar.runKill;
import me.khmdev.HUB.Base;

public class ItemsCust {
	
	
	@SuppressWarnings("deprecation")
	public static final CustomItem maria=new CustomItem(
			AuxPlayer
			.getItem(Material.GRASS, "Marihuana",
					ChatColor.translateAlternateColorCodes
					('&', "&5Veneno y mareo por dos minutos!"))
			) {

		@Override
		public void execute(PlayerInteractEvent event) {
			event.setCancelled(true);

			Player pl=event.getPlayer();
			if(pl.getItemInHand().getAmount()==1){
				pl.setItemInHand(null);
			}else{
				pl.getItemInHand().setAmount(pl.getItemInHand().getAmount()-1);
			}
			pl.updateInventory();
			pl.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
					120*20, 1));
			pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
					120*20, 1));
		}
		
		@Override
		public void execute(InventoryClickEvent event) {
			
		}
	},
	esteroides =new CustomItem(
			AuxPlayer
			.getItem(Material.MAGMA_CREAM, "Esteroides",
					ChatColor.translateAlternateColorCodes
					('&', "&5Salto,velocidad y 3 corazones extras por 2 minutos!"))
			) {

		@Override
		public void execute(PlayerInteractEvent event) {
			event.setCancelled(true);

			final Player pl=event.getPlayer();
			
			if(pl.getItemInHand().getAmount()==1){
				pl.setItemInHand(null);
			}else{
				pl.getItemInHand().setAmount(pl.getItemInHand().getAmount()-1);
			}
			pl.updateInventory();
			runKill run=new runKill() {
				
				@Override
				public void run() {
					pl.setMaxHealth(20);
					kill();
				}
			};
			
			int idd = Bukkit.getScheduler()
					.scheduleSyncDelayedTask(Base.getInstance(),run,20*120);
			run.setId(idd);
			
			pl.setMaxHealth(20+3*2);
		
			pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,
					120*20, 3));
			pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
					120*20, 3));
			//pl.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
				//	120*20, 3));
		}
		
		@Override
		public void execute(InventoryClickEvent event) {
			
		}
	},
	Cocacola =new CustomItem(
			AuxPlayer
			.getItem(Material.COAL,"Cocacola" ,
					ChatColor.translateAlternateColorCodes
					('&', "&5Ceguera y velocidad 3 por dos minutos!"))
			) {

		@Override
		public void execute(PlayerInteractEvent event) {
			event.setCancelled(true);
			Player pl=event.getPlayer();
			if(pl.getItemInHand().getAmount()==1){
				pl.setItemInHand(null);
			}else{
				pl.getItemInHand().setAmount(pl.getItemInHand().getAmount()-1);
			}
			pl.updateInventory();
			pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
					120*20, 1));
			pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
					120*20, 3));
		}
		
		@Override
		public void execute(InventoryClickEvent event) {
			
		}
	},
	Cupido =new CustomItem(
			AuxPlayer
			.getItem(Material.RED_ROSE,"Cupido",
					ChatColor.translateAlternateColorCodes
					('&', 
		"&5Particulas de corazones y 9 corazones extras por dos minutos!")
			)) {

		@Override
		public void execute(PlayerInteractEvent event) {
			event.setCancelled(true);
			final Player pl=event.getPlayer();
			if(pl.getItemInHand().getAmount()==1){
				pl.setItemInHand(null);
			}else{
				pl.getItemInHand().setAmount(pl.getItemInHand().getAmount()-1);
			}
			pl.updateInventory();
			runKill run=new runKill() {
				
				@Override
				public void run() {
					pl.setMaxHealth(20);
					
					kill();
				}
			};
			
			int idd = Bukkit.getScheduler()
					.scheduleSyncDelayedTask(Base.getInstance(),run,20*120);
			run.setId(idd);

			pl.setMaxHealth(20+9*2);
			Base.run(new EffectTime(ParticleEffect.HEART, pl,
												120
												));
			//pl.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
				//	120*20
					//, 9));
		}
		
		@Override
		public void execute(InventoryClickEvent event) {
			
		}
	},
	Creeperniano =new CustomItem(
			AuxPlayer
			.getItem(Material.MONSTER_EGG,"Poder Creeperniano", 50, 
					ChatColor.translateAlternateColorCodes
					('&', 
		"&5Particulas de explosion grandes y salto 2 por un minuto! "))
			) {

		@Override
		public void execute(PlayerInteractEvent event) {
			event.setCancelled(true);
			Player pl=event.getPlayer();
			if(pl.getItemInHand().getAmount()==1){
				pl.setItemInHand(null);
			}else{
				pl.getItemInHand().setAmount(pl.getItemInHand().getAmount()-1);
			}
			pl.updateInventory();
			Base.run(new EffectTime(ParticleEffect.LARGE_EXPLODE, pl,
												60));
			pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,
					60*20, 2));
		}
		
		@Override
		public void execute(InventoryClickEvent event) {
			
		}
	};
	

}
