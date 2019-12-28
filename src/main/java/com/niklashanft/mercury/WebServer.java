package com.niklashanft.mercury;

import com.google.gson.Gson;
import com.niklashanft.mercury.user.controllers.AuthController;
import com.niklashanft.mercury.user.controllers.SpotsController;
import com.niklashanft.mercury.user.controllers.UserController;
import com.niklashanft.mercury.user.exception.ModelNotFoundException;
import org.bukkit.plugin.Plugin;
import spark.Request;
import spark.Response;

import java.util.Map;

import static spark.Spark.*;

public class WebServer extends Thread {

    private final Gson gson = new Gson();
    private final Plugin plugin;

    public WebServer(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        //configureSecurity();
        configureCors();
        configureExceptions();

        // Todo Refactor into instance usage
        get("/api/users", UserController::getAllUsers);

        new AuthController().registerRoutes();
        new SpotsController(this.plugin).registerRoutes();

        // Todo move the teleport logic into the user like /api/users/:id/teleport with payload
    }

    // Todo make this happen
    private void configureRequestBody() {
        before((request, response) -> {
            String body = request.body();
            Map<String, String> requestBody = gson.fromJson(body, Map.class);
        });
    }

    private void configureExceptions() {
        exception(ModelNotFoundException.class, (exception, request, response) -> response.status(404));
    }

    private void configureCors() {
        options("/*", (Request request, Response res) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((Request request, Response res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
            res.type("application/json");
        });
    }

    private void configureSecurity() {
        before((request, response) -> {

            // Let pre flight go trough
            if(request.requestMethod().equalsIgnoreCase("OPTIONS")) {
                return;
            }

            // Ignore Login
            if(
                request.requestMethod().equalsIgnoreCase("POST") &&
                request.pathInfo().equalsIgnoreCase("/api/login")
            )
            {
                return;
            }

            if(request.session().attribute("id") == null) { halt(401); }

        });
    }

}
