package Trubby.co.th;

import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.api.BossShopAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BossShopManager {

	
	private BossShop bs; //BossShop Plugin Instance
	
	public BossShopManager(){
		
		Plugin plugin = Bukkit.getPluginManager().getPlugin("BossShop"); //Get BossShop Plugin
		
		if(plugin==null){ //Not installed? 
			System.out.print("[BS Hook] BossShop was not found... you can download it here: http://dev.bukkit.org/bukkit-plugins/bossshop");
			return;
		}
		
		bs = (BossShop) plugin; //Detected with Success :)
		
	}
	
	
	public BossShopAPI getBossShopAPI(){
		return bs.getAPI(); //Returns BossShop API
	}
}
	