package Trubby.co.th;

import java.util.HashMap;

import org.black_ixx.bossshop.core.BSShop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerChestManager implements Listener{

	public HashMap<String, PlayerChest> chestlist = new HashMap<String, PlayerChest>();
	
	public void addPlayer(Player p){
		chestlist.put(p.getName(), new PlayerChest(p));
	}
	
	public void removePlayer(Player p){
		PlayerChest pchest = chestlist.get(p.getName());
		if(p.getInventory().getName().equalsIgnoreCase(p.getName())){
			pchest.updateChest(p.getInventory());
		}
		pchest.saveChest();
	}
	
	@EventHandler
	public void onLogin(PlayerJoinEvent e){
		chestlist.put(e.getPlayer().getName(), new PlayerChest(e.getPlayer()));
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if(chestlist.containsKey(p.getName())){
			PlayerChest pchest = chestlist.get(p.getName());
			pchest.saveChest();
			
			chestlist.remove(p.getName());
			
		}
	}	
	
	@EventHandler
	public void onPlayerClickChest(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		Block b = e.getClickedBlock();
		
		if (b.getType() != Material.ENDER_CHEST) {
			return;
		}
		
		e.setCancelled(true);

		PlayerChest pchest = chestlist.get(p.getName());
		pchest.openChest();
	}
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e){
		Player p = (Player) e.getPlayer();
		if(e.getInventory().getName().equalsIgnoreCase(p.getName())){
			PlayerChest pchest = chestlist.get(p.getName());
			
			pchest.updateChest(e.getInventory());
			
			p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 0.7F, 1.0F);
		}
	}
	
	//NPC
	@EventHandler
	public void oninteract(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Villager){
			Villager vil = (Villager) e.getRightClicked();
			
			if(vil.getCustomName().equalsIgnoreCase(ChatColor.GREEN + "Chest Upgrade")){
				openShop(e.getPlayer(), "ChestShop");
			}
		}
	}
	
	public boolean openShop(Player p, String shop_name){
		
		BSShop shop = GTAEnderChest.instance.bsm.getBossShopAPI().getShop(shop_name);
		if(shop==null){
			p.sendMessage(ChatColor.RED+"Shop "+shop_name+" not found...");
			return false;
		}
		
		GTAEnderChest.instance.bsm.getBossShopAPI().openShop(p, shop);
		
		return true;
	}
}
