package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controller.CarportController;
import app.controller.OrderController;
import app.controller.UserController;
import app.entities.Order;
import app.persistence.ConnectionPool;
import app.services.Calculator;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "datdat2024!!";
    private static final String URL = "jdbc:postgresql://164.90.237.79:5432/%s?currentSchema=public";
    private static final String DB = "postgres";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);


    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(3484);

        // Routing
        app.get("/", ctx -> ctx.render("index.html"));
        UserController.addRoutes(app,connectionPool);
        CarportController.addRoutes(app,connectionPool);
        OrderController.addRoutes(app, connectionPool);
    }
}
