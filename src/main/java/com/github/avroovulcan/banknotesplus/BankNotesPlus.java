package com.github.avroovulcan.banknotesplus;

import com.github.avroovulcan.banknotesplus.cmd.GiveNoteCommand;
import com.github.avroovulcan.banknotesplus.cmd.WithdrawCommand;
import com.github.avroovulcan.banknotesplus.event.ReedemEvent;
import com.github.avroovulcan.banknotesplus.util.Chat;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author AvroVulcan
 */
public class BankNotesPlus extends JavaPlugin {

    private static Economy econ = null;

    @Override
    public void onEnable()
    {

        String a = getServer().getClass().getPackage().getName();
        String version = a.substring(a.lastIndexOf('.') + 1);

        if(!version.equalsIgnoreCase("v1_8_R3")){
            getLogger().log(Level.WARNING, "This plugin is only compatible for Spigot v1_8_R1 (latest 1.8). Disabling!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();
        setupEconomy();
        getCommand("withdraw").setExecutor(new WithdrawCommand(this));
        getCommand("givenote").setExecutor(new GiveNoteCommand(this));
        getServer().getPluginManager().registerEvents(new ReedemEvent(this), this);
    }


    public void generateNote(Player player, double amount, boolean silent)
    {
        ItemStack note = new ItemStack(Material.PAPER);
        ItemMeta meta = note.getItemMeta();
        meta.setDisplayName(Chat.color(getConfig().getString("bank-notes.name").replace("%amount%", String.valueOf(amount)).replace("%player%", player.getName())));
        List<String> lore = new ArrayList<>();
        for(String s : getConfig().getStringList("bank-notes.lore"))
        {
            lore.add(Chat.color(s.replace("%amount%", String.valueOf(amount)).replace("%player%", player.getName())));
        }
        meta.setLore(lore);
        note.setItemMeta(meta);

        net.minecraft.server.v1_8_R3.ItemStack nbtItem = CraftItemStack.asNMSCopy(note);
        NBTTagCompound compound = (nbtItem.hasTag()) ? nbtItem.getTag() : new NBTTagCompound();
        compound.set("banknote-value", new NBTTagDouble(amount));
        nbtItem.setTag(compound);
        note = CraftItemStack.asBukkitCopy(nbtItem);

        if(player.getInventory().firstEmpty() == -1)
        {
            player.getWorld().dropItem(player.getLocation(), note);
            player.sendMessage(getConfigMessage("dropped-note").replace("%amount%", String.valueOf(amount)));
        }
        else
        {
            player.getInventory().addItem(note);
            if(!silent) player.sendMessage(getConfigMessage("added-note").replace("%amount%", String.valueOf(amount)));
        }

    }

    public String getConfigMessage(String s)
    {
        return Chat.color(getConfig().getString("messages." + s));
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
    }

    public static Economy getEconomy() {
        return econ;
    }

}
