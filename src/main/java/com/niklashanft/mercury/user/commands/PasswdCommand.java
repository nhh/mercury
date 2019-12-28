package com.niklashanft.mercury.user.commands;

import com.mongodb.MongoClient;
import com.niklashanft.mercury.user.models.User;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PasswdCommand implements CommandExecutor {

    private final Morphia morphia = new Morphia();

    private final Plugin plugin;

    public PasswdCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!command.getName().equalsIgnoreCase("passwd")) {
            return false;
        }

        Player player = (Player) sender;

        morphia.mapPackage("com.niklashanft.mercury.user.models");

        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");

        User user = datastore.createQuery(User.class)
                .field("playerId").equal(player.getUniqueId()).first();

        user.setPassword(args[0]);

        datastore.save(user);

        player.sendMessage("We' successfully set your new password to " + args[0]);

        return true;
    }

}
