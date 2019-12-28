package com.niklashanft.mercury.user.controllers;

import com.google.gson.Gson;
import com.niklashanft.mercury.user.exception.ModelNotFoundException;
import com.niklashanft.mercury.user.models.Spot;
import com.niklashanft.mercury.user.services.SpotService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.get;

public class SpotsController {

    private final Gson gson = new Gson();
    private final SpotService spotsService = new SpotService();
    private final Plugin plugin;

    public SpotsController(Plugin plugin) {
        this.plugin = plugin;
    }

    private String getAllSpots(Request request, Response response) {
        List<Spot> spots = spotsService.getAllSpots();

        return gson.toJson(spots);
    }

    private String createSpot(Request request, Response response) {

        Player player = Bukkit.getPlayer("bignick3");

        if(player == null) {
            halt(404);
            return "Not found";
        }

        String body = request.body();
        Map<String, String> requestBody = gson.fromJson(body, Map.class);

        Spot spot = new Spot();

        spot.setDescription(requestBody.get("description"));
        spot.setName(requestBody.get("name"));

        spot.setX(player.getLocation().getX());
        spot.setY(player.getLocation().getY());
        spot.setZ(player.getLocation().getZ());
        spot.setWorldId(player.getWorld().getUID().toString());

        spotsService.save(spot);

        return gson.toJson(spot);

    }

    private String getOneSpot(Request request, Response response) {
        String id = request.params(":id");

        // Todo this is the way i want to work with optionals <3
        Spot spot = spotsService
                .getOneSpot(id)
                .orElseThrow(ModelNotFoundException::new);

        return gson.toJson(spot);
    }

    private String teleportToSpot(Request request, Response response) {
        String id = request.params(":id");
        // Todo this is the way i want to work with optionals <3
        Spot spot = spotsService
                .getOneSpot(id)
                .orElseThrow(ModelNotFoundException::new);

        Player player = Bukkit.getPlayer("bignick3");

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> player.teleport(spot.getLocation()));

        return "";
    }

    public void registerRoutes() {
        post("/api/spots", this::createSpot);
        get("/api/spots", this::getAllSpots);
        get("/api/spots/:id", this::getOneSpot);
        put("/api/spots/:id/teleport", this::teleportToSpot);
    }

}
