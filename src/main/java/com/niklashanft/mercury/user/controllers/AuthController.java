package com.niklashanft.mercury.user.controllers;

import com.google.gson.Gson;
import com.niklashanft.mercury.user.exception.ModelNotFoundException;
import com.niklashanft.mercury.user.models.User;
import com.niklashanft.mercury.user.services.UserService;
import spark.Request;
import spark.Response;

import java.util.Map;

import static spark.Spark.halt;
import static spark.Spark.post;

public class AuthController {

    // Todo wrap whole Bukkit.getPlayer things into BukkitService for Optional support
    private final Gson gson = new Gson();
    private final UserService userService = new UserService();

    // Todo https://akveo.github.io/nebular/
    // Todo Push Notifications:
    // Someone bought your things
    // Someone wrote you
    // https://capacitor.ionicframework.com/docs/guides/push-notifications-firebase/

    private String login(Request request, Response response) {
        String body = request.body();

        Map<String, String> b = gson.fromJson(body, Map.class);

        System.out.println(b.get("username"));
        System.out.println(b.get("password"));

        User user = this.userService
                .getOneByDisplayName(b.get("username"))
                .orElseThrow(ModelNotFoundException::new);

        if(user.getPassword() == null) {
            halt(400);
            return "You must set your password first via /passwd";
        }

        if( !user.getPassword().equals(b.get("password")) ) {
            halt(401);
            return "Username or password is wrong!";
        }

        request.session(true);
        request.session().attribute("id", user.getId());

        return gson.toJson(b);
    }

    public void registerRoutes() {
        post("/api/login", this::login);
    }

}
