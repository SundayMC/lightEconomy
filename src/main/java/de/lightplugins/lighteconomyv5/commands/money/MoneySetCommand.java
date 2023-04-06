package de.lightplugins.lighteconomyv5.commands.money;

import de.lightplugins.lighteconomyv5.database.querys.MoneyTableAsync;
import de.lightplugins.lighteconomyv5.enums.MessagePath;
import de.lightplugins.lighteconomyv5.enums.PermissionPath;
import de.lightplugins.lighteconomyv5.master.Main;
import de.lightplugins.lighteconomyv5.utils.SubCommand;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class MoneySetCommand extends SubCommand {

    Main plugin;
    public MoneySetCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "set a specified amount of Money to the targets Account";
    }

    @Override
    public String getSyntax() {
        return "/money set [playername]";
    }

    @Override
    public boolean perform(Player player, String[] args) {

        if(args.length != 3) {
            Main.util.sendMessage(player, MessagePath.WrongCommand.getPath());
            return true;
        }

        if(!Main.economyImplementer.hasAccount(args[1])) {
            Main.util.sendMessage(player, MessagePath.PlayerNotExists.getPath());
            return false;
        }

        if(!player.hasPermission(PermissionPath.MoneySet.getPerm())) {
            Main.util.sendMessage(player, MessagePath.NoPermission.getPath());
            return false;
        }

        try {
            double amount = Double.parseDouble(args[2]);

            if(amount < 0) {
                Main.util.sendMessage(player, MessagePath.OnlyPositivNumbers.getPath());
                return true;
            }

            MoneyTableAsync moneyTableAsync = new MoneyTableAsync(plugin);

            moneyTableAsync.playerBalance(args[1]).thenAccept(result -> {

                moneyTableAsync.setMoney(args[1], amount).thenAccept(success -> {
                    Main.util.sendMessage(player, MessagePath.MoneySetPlayer.getPath()
                            .replace("#currency#", Main.economyImplementer.currencyNameSingular())
                            .replace("#target#", args[1])
                            .replace("#amount#", Main.util.formatDouble(amount))
                    );
                });
            });

        } catch (NumberFormatException e) {
            Main.util.sendMessage(player, MessagePath.NotANumber.getPath());
            return true;
        }

        return false;
    }
}
