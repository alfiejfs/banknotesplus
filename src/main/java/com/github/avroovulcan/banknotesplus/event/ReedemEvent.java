package com.github.avroovulcan.banknotesplus.event;

import com.github.avroovulcan.banknotesplus.BankNotesPlus;
import com.github.avroovulcan.banknotesplus.util.Chat;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author AvroVulcan
 */
public class ReedemEvent implements Listener {

    private BankNotesPlus instance;

    public ReedemEvent(BankNotesPlus instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if (!event.getPlayer().hasPermission("banknotesplus.redeem") && !event.getPlayer().hasPermission("banknotesplus.*")) return;
        else if (event.getPlayer().getItemInHand().getType() != Material.PAPER) return;;
        ItemStack original = event.getPlayer().getItemInHand();
        net.minecraft.server.v1_8_R3.ItemStack nbtItem = CraftItemStack.asNMSCopy(original);
        NBTTagCompound compound = (nbtItem.hasTag()) ? nbtItem.getTag() : new NBTTagCompound();
        double value = compound.getDouble("banknote-value");
        if(value <= 0) return;
        else
        {
            event.getPlayer().getInventory().remove(event.getItem());
            OfflinePlayer getting = Bukkit.getOfflinePlayer(event.getPlayer().getUniqueId());
            BankNotesPlus.getEconomy().depositPlayer(getting, value);
            Chat.sendMessage(event.getPlayer(), instance.getConfigMessage("redeemed-note").replace("%value%", String.valueOf(value)));
        }
    }
}
