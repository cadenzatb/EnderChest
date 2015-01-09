package Trubby.co.th;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FileManager {

	GTAEnderChest plugin = GTAEnderChest.instance;
	public File folder;
	
	public FileManager(){
		folder = new File(GTAEnderChest.instance.getDataFolder(), "userdata");
		if(!folder.exists()){
			folder.mkdirs();
		}
	}
	
	
	public File playerFile(final Player p){
		File f = new File(folder, p.getName() + ".yml");
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(GTAEnderChest.instance, new Runnable() {
				
				public void run() {
					PlayerChest pc = GTAEnderChest.instance.pcman.chestlist.get(p.getName());
					for(ItemStack is : p.getEnderChest().getContents()){
						pc.items.add(is);
					}
				}
			}, 20L);
		}

		return f;
		//Then read the file, and check the value
	}
	
	public FileConfiguration playerConfig(Player p){
		
		File f = playerFile(p);
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fc;
	}
	
	public File oldFile(){
		File f = new File(folder, "old.yml");
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return f;
		//Then read the file, and check the value
	}
	
	public FileConfiguration oldConfig(){
		
		File f = oldFile();
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		try {
			fc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fc;
	}
	
	public File newFile(String name){
		File f = new File(folder,name + ".yml");
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return f;
		//Then read the file, and check the value
	}
	
	public FileConfiguration newConfig(String name){
		
		File f = newFile(name);
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fc;
	}
	
}
