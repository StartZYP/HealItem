package com.qq44920040.minecraft.healitem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main extends JavaPlugin implements Listener {
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return super.onCommand(sender, command, label, args);
    }

    @EventHandler
    public void PlayerUseHealitem(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand!=null&&itemInHand.getType()!= Material.AIR){
            //System.out.println("1");
            ItemMeta itemMeta = itemInHand.getItemMeta();
            if (itemMeta.hasDisplayName()){
                String Displayname = itemMeta.getDisplayName();
                if (Displayname.toLowerCase().contains("hp")){
                    String disname =ChatColor.stripColor(Displayname);
                    //System.out.println(disname);
                    Pattern pattern = Pattern.compile("[^0-9]");
                    Matcher matcher = pattern.matcher(disname);
                    String group = matcher.replaceAll("");
                    //System.out.println(group);
                    if (group==null||group.equalsIgnoreCase("")){
                        return;
                    }
                    int Hp = Integer.parseInt(group);
                    double nowHp = event.getPlayer().getHealth();
                    //System.out.println(nowHp+"dangqian"+Hp);
                    double MaxHeal = event.getPlayer().getMaxHealth();
                    if (nowHp+Hp>=MaxHeal){
                        event.getPlayer().setHealth(MaxHeal);
                    }else {
                        event.getPlayer().setHealth(nowHp+Hp);
                    }
                    event.getPlayer().sendMessage(getConfig().getString("HpMsg").replace("{Hp}",group));
                    int Amount = itemInHand.getAmount();
                    if (Amount==1){
                        event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                    }else {
                        itemInHand.setAmount(Amount-1);
                        event.getPlayer().setItemInHand(itemInHand);
                    }
                }
            }
        }

    }

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(),"config.yml");
        if (!file.exists()){
            saveDefaultConfig();
        }
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        super.onEnable();
    }
}
