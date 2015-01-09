package Trubby.co.th;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GTAEnderChest extends JavaPlugin {

	public static GTAEnderChest instance;
	public FileManager fileman;
	public PlayerChestManager pcman;
	public BossShopManager bsm;
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		instance = this;
		
		bsm = new BossShopManager();
		fileman = new FileManager();
		pcman = new PlayerChestManager();
		Bukkit.getPluginManager().registerEvents(pcman, this);
		
		for(Player p : Bukkit.getOnlinePlayers()){
			pcman.addPlayer(p);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()){
			pcman.removePlayer(p);
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if(sender instanceof Player){
			
			if(label.equalsIgnoreCase("convert")){
				FileConfiguration old = fileman.oldConfig();
				for(String name : old.getConfigurationSection("ec").getKeys(false)){
					FileConfiguration newf = fileman.newConfig(name);
					int i = 0;
					for(String stritem : old.getConfigurationSection("ec." + name + ".a").getKeys(false)){
						
						ItemStack is = old.getItemStack("ec." + name + ".a." + i);
						newf.set("Enderchest.page0." + i, is);
						newf.set("Chestsize", 45);
						
						i++;
					}
					
					try {
						newf.save(fileman.newFile(name));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println(name + " - done!");
				}
			}
		}
		
		return false;
	}
	
}
