package com.github.avroovulcan.banknotesplus.cmd;

import com.github.avroovulcan.banknotesplus.BankNotesPlus;
import com.github.avroovulcan.banknotesplus.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author AvroVulcan
 */
public class WithdrawCommand implements CommandExecutor {

    private BankNotesPlus instance;

    public WithdrawCommand(BankNotesPlus instance)
    {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Chat.sendMessage(sender, instance.getConfigMessage("player-only"));
            return true;
        }
        else if (!sender.hasPermission("banknotesplus.withdraw") && !sender.hasPermission("banknotesplus.*")) {
            Chat.sendMessage(sender, instance.getConfigMessage("no-permission"));
            return true;
        }
        else if (args.length != 1) {
            Chat.sendMessage(sender, "&c&l(!) &cInvalid Usage! Use /withdraw <amount>");
            return true;
        }

        Player onlinePlayer = (Player) sender;
        OfflinePlayer player = Bukkit.getOfflinePlayer(onlinePlayer.getUniqueId());

        Double amount;
        try {
            amount = Double.parseDouble(args[0].replace(",", "").replace(".", ""));
        }
        catch (NumberFormatException err) {
            Chat.sendMessage(sender, "&c&l(!) &cInvalid Usage! Use /withdraw <amount>");
            return true;
        }

        if (amount <= 0)
        {
            Chat.sendMessage(sender, "&c&l(!) &cInvalid Usage! Use /withdraw <amount>");
            return true;
        }

        double balance = BankNotesPlus.getEconomy().getBalance(player);
        if (balance < amount && !player.isOp())
        {
            Chat.sendMessage(sender, instance.getConfigMessage("not-enough-money"));
            return true;
        }
        else
        {
            if(!player.isOp()) BankNotesPlus.getEconomy().withdrawPlayer(player, amount);
            instance.generateNote(onlinePlayer, amount, false);
        }
        return true;
    }
}
