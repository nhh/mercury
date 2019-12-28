package com.niklashanft.mercury.user.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import spark.Request;
import spark.Response;

import java.util.stream.Collectors;

public class UserController {

    private static final Gson gson = new Gson();

    public static String getAllUsers(Request request, Response response) {
        response.header("Content-Type", "application/json");

        return gson.toJson(Bukkit.getOnlinePlayers().stream().map(p -> {
            Player player = (Player) p;
            JsonObject jsonPlayer = new JsonObject();

            jsonPlayer.addProperty("username", player.getDisplayName());
            jsonPlayer.addProperty("experienceLevel", player.getLevel());
            jsonPlayer.addProperty("foodLevel", player.getFoodLevel());
            jsonPlayer.addProperty("healthLevel", player.getHealth());

            return jsonPlayer;
        }).collect(Collectors.toList()));

    }

}
