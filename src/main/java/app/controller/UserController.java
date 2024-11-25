package app.controller;

import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        //We are just adding routing required from Javalin to make the program work.
        app.get("/createuser", ctx -> ctx.render("createuser.html"));
        app.post("/createuser", ctx -> createUser(ctx, dbConnection));
    }

    private static void createUser(Context ctx, ConnectionPool dbConnection) {
        // Receiving the information from the HTML
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String confirmPassword = ctx.formParam("confirmpassword");
        // Simple email check because we want valid emails.. This could and should be change to something that will check from REAL and valid emails.
        if (username == null || !username.contains("@") || !username.contains(".")) {
            ctx.attribute("message", "Venligst indtast en gyldig e-mail-adresse.");
            ctx.render("createuser.html");

        } else if (password == null || confirmPassword == null) {
            // We are checking if the user filled both password fields.
            // Because we want to make sure that the user wrote the intended password
            ctx.attribute("message", "Venligst udfyld dit kodeord i begge felter.");
            ctx.render("createuser.html");
        } else if (passwordCheck(ctx, password, confirmPassword)) {
            try {
                // Actually creating a user when all checks passed.
                username = username.toLowerCase();
                UserMapper.createUser(username, password, dbConnection);
                ctx.attribute("message", "du er nu oprettet");
            } catch (DatabaseException e) {
                // Catching errors when a username is already in use
                // Because we do not want duplicate usernames in our database.
                // We added a unique constraint in the database and we have to handle the error here.
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
    private static boolean passwordCheck(Context ctx, String password, String confirmPassword) {

        //Checks if the passwords match at all. Proceeds with code if true.
        if (!password.equals(confirmPassword)) {
            ctx.attribute("message", "Kodeord matcher ikke. Prøv igen");
            return false;
        } else {
            return true;
        }
    }
}
