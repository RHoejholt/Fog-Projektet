package app.controller;

import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        app.get("/createuser", ctx -> ctx.render("createuser.html"));
        app.post("/createuser", ctx -> createUser(ctx, dbConnection));
    }

    private static void createUser(Context ctx, ConnectionPool dbConnection) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String confirmPassword = ctx.formParam("confirmpassword");
        // Simple email check
        if (username == null || !username.contains("@") || !username.contains(".")) {
            ctx.attribute("message", "Venligst indtast en gyldig e-mail-adresse.");
            ctx.render("createuser.html");
        } else if (password == null || confirmPassword == null) {
            ctx.attribute("message", "Venligst udfyld dit kodeord i begge felter.");
            ctx.render("createuser.html");
            try {
                username = username.toLowerCase();
                UserMapper.createUser(username, password, dbConnection);
            } catch (DatabaseException e) {
                if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                    ctx.attribute("message", "Brugernavnet er allerede i brug. Prøv et andet.");
                } else {
                    ctx.attribute("message", e.getMessage());
                }
                ctx.render("createuser.html");
            }
        } else {
            ctx.render("createuser.html");
        }
    }
}
