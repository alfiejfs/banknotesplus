package com.github.avroovulcan.banknotesplus.cmd;

import com.github.avroovulcan.banknotesplus.BankNotesPlus;
import com.github.avroovulcan.banknotesplus.util.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author AvroVulcan
 */
public class GiveNoteCommand implements CommandExecutor {

    private BankNotesPlus instance;
    public GiveNoteCommand(BankNotesPlus instance)
    {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!sender.hasPermission("banknotesplus.givenote") && !sender.hasPermission("banknotesplus.*"))
        {
            Chat.sendMessage(sender, instance.getConfigMessage("no-permission"));
            return true;
        }
        else if (args.length != 2) {
            Chat.sendMessage(sender, "&c&l(!) &cInvalid Usage! Use /givenote <player> <amount>");
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            Chat.sendMessage(sender, "&c&l(!) &cThat player cannot be found!");
            return true;
        }

        double amount;
        try
        {
            amount = Double.parseDouble(args[1]);
        }
        catch (NumberFormatException err)
        {
            Chat.sendMessage(sender, "&c&l(!) &cInvalid Usage! Use /givenote <player> <amount>");
            return true;
        }

        if(amount <= 0)
        {
            Chat.sendMessage(sender, "&c&l(!) &cInvalid Usage! Use /givenote <player> <amount>");
            return true;
        }

        instance.generateNote(player, amount, true);
        Chat.sendMessage(sender, instance.getConfigMessage("gave-note").replace("%value%", String.valueOf(amount)).replace("%player%", player.getName()));
        Chat.sendMessage(player, instance.getConfigMessage("received-note").replace("%value%", String.valueOf(amount)).replace("%player%", sender.getName()));

        return true;
    }
}
