package com.niklashanft.mercury.user.events;

import com.mongodb.MongoClient;
import com.niklashanft.mercury.user.models.User;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserJoinedServerEvent implements Listener {

    private final Morphia morphia = new Morphia();

    @EventHandler
    public void createUserIfNotExists(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        morphia.mapPackage("com.niklashanft.mercury.user.models");
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "mercury");

        User user = datastore.createQuery(User.class)
                .field("playerId").equal(player.getUniqueId()).first();

        if(user != null) {
            player.sendMessage("Welcome, back hero!");
            return;
        }

        user = new User();

        user.setDisplayName(player.getDisplayName());
        user.setPlayerId(player.getUniqueId());
        user.setActive(true);

        datastore.save(user);

        player.sendMessage("Welcome, hero! We just created you in our database! You can now login with your password: 123456789");

    }

}
