package com.niklashanft.mercury;

import com.niklashanft.mercury.user.commands.PasswdCommand;
import com.niklashanft.mercury.user.events.UserJoinedServerEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Mercury extends JavaPlugin {

    private final WebServer mercury = new WebServer(this);

    @Override
    public void onEnable() {
        this.getCommand("passwd").setExecutor(new PasswdCommand(this));
        mercury.start();


        getServer().getPluginManager().registerEvents(new UserJoinedServerEvent(), this);
    }

    @Override
    public void onDisable() {
        mercury.interrupt();
    }

}
