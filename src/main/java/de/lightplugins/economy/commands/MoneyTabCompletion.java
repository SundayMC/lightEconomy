package de.lightplugins.economy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MoneyTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {


        if(args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("add");
            arguments.add("remove");
            arguments.add("set");
            arguments.add("top");
            arguments.add("show");

            return arguments;
        }

        return null;
    }
}