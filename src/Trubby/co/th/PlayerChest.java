package Trubby.co.th;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerChest {

	//HashMap<K, items> 

	Player p;
	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	
	public PlayerChest(Player p){
		this.p = p;
		
		loadChest();
	}
	
	//update after use
	public void updateChest(Inventory inv) {
		items.clear();
		for (int i = 0; i < getChestSize(); i++) {
				items.add(inv.getItem(i));
		}
		
	}
	
	//init
	public void loadChest() {
		//PAGE
		FileConfiguration fc = GTAEnderChest.instance.fileman.playerConfig(p);
		for (int page = 0; page < getPage(); page++) {
			
			//ITEM
			int oldChestSize = fc.getInt("Chestsize");
			for (int i = 0; i < oldChestSize; i++) {
				ItemStack item = fc.getItemStack(
						"Enderchest.page" + page + "." + i);
				
				items.add(item);
			}
		}

	}
	
	//open chest
	public void openChest() {
		if(getChestSize() < 9){
			p.sendMessage("No Permission");
			return;
		}
		Inventory inv = Bukkit.createInventory(p, getChestSize(), p.getName());
		ItemStack[] itemsList = (ItemStack[]) items.toArray(new ItemStack[items.size()]);
		
		inv.setContents(itemsList);
		p.openInventory(inv);
		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 0.7F, 1.0F);
	}
	
	//save on logout
	public void saveChest() {
		FileConfiguration fc = GTAEnderChest.instance.fileman.playerConfig(p);
		//PAGE
		for (int page = 0; page < getPage(); page++) {
			//ITEM
			for (int i = 0; i < getChestSize(); i++) {
				fc.set("Enderchest.page" + page + "." + i, items.get(i));
				fc.set("Chestsize", getChestSize());
			}
		}
		
		try {
			fc.save(GTAEnderChest.instance.fileman.playerFile(p));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getPage(){
		if(getChestSize() > 54){
			return 2;
		}
		return 1;
	}
	
	public int getChestSize(){
		int size = 0;
		if(p.hasPermission("enderchest.1")){
			size = 9;
		}
		if(p.hasPermission("enderchest.2")){
			size = 18;
		}
		if(p.hasPermission("enderchest.3")){
			size = 27;
		}
		if(p.hasPermission("enderchest.4")){
			size = 36;
		}
		if(p.hasPermission("enderchest.5")){
			size = 45;
		}
		if(p.hasPermission("enderchest.6")){
			size = 54;
		}
		return size;
	}
	
	/**
	 * 		GETTER SETTER
	 */
}
