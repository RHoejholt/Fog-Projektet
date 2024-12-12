package app.controller;

import app.entities.User;
import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.persistence.*;

import java.util.List;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        app.get("/dimensions", ctx -> showAllForms(ctx, dbConnection));  // Show the form with dynamic options
        app.post("/dimensions", ctx -> submitForm(ctx, dbConnection));  // Handle the form submission (Used to send data to another page)
    }

    private static void showAllForms(Context ctx, ConnectionPool dbConnection) {
        showDimensionsForm(ctx, dbConnection);
        showTagMaterialeForm(ctx, dbConnection);
        showSkurForm(ctx, dbConnection);
        showSpaerOgRemForm(ctx,dbConnection);
        // Render the form with dynamic options
        ctx.render("dimensions.html");
    }
    private static void showSkurForm(Context ctx, ConnectionPool dbConnection) {
        try {
            // Query the database to get the available column and table
            List<String> skurOptions = getDimensionOptions("skur", "skur", dbConnection);

            // Add the options to the context so Thymeleaf can access them
            ctx.attribute("skurOptions", skurOptions);
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error fetching skur: " + e.getMessage());
        }
    }
    private static void showTagMaterialeForm(Context ctx, ConnectionPool dbConnection) {
        try {
            // Query the database to get the available column and table
            List<String> materialeOptions = getDimensionOptions("materiale", "tag_materiale", dbConnection);

            // Add the options to the context so Thymeleaf can access them
            ctx.attribute("tagMaterialeOptions", materialeOptions);
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error fetching materiale: " + e.getMessage());
        }
    }

    // Method to show the dimensions form
    private static void showDimensionsForm(Context ctx, ConnectionPool dbConnection) {
        try {
            // Query the database to get the available 'bredde' and 'laengde' options using getDimensionOptions
            List<String> breddeOptions = getDimensionOptions("bredde", "dimensioner_bredde", dbConnection);
            List<String> laengdeOptions = getDimensionOptions("laengde", "dimensioner_laengde", dbConnection);

            // Add the options to the context so Thymeleaf can access them
            ctx.attribute("breddeOptions", breddeOptions);
            ctx.attribute("laengdeOptions", laengdeOptions);
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error fetching dimensions: " + e.getMessage());
        }
    }

    // Method to call getDimensionOptions to get the available options for a column and table
    private static List<String> getDimensionOptions(String column, String table, ConnectionPool dbConnection) throws DatabaseException {
        // Use the existing method to fetch dimension options
        return CarportMapper.getDimensionOptions(column, table, dbConnection);
    }

    // Handle the form submission
    private static void submitForm(Context ctx, ConnectionPool dbConnection) {
        // Receive the form parameters
        String bredde = ctx.formParam("bredde");
        String laengde = ctx.formParam("laengde");
        String tagMateriale = ctx.formParam("tagMateriale");
        String skur = ctx.formParam("skur");
        String spaerOgRem = ctx.formParam("spaer_og_rem");

        // Add simple validation for dimensions
        if (bredde == null || laengde == null || tagMateriale == null || skur == null || spaerOgRem == null) {
            ctx.attribute("message", "Venligst udfyld alle felter.");
            showAllForms(ctx, dbConnection);
            return;
        }
        try {
            // Retrieve the logged-in user's ID from the session
            User userId = ctx.sessionAttribute("currentUser");
            if (userId == null) {
                ctx.attribute("message", "Du skal være logget ind for at afgive en ordre.");
                showAllForms(ctx, dbConnection);
                return;
            }

            // Send data to database
            CarportMapper.submitFormToDatabase(bredde, laengde, tagMateriale, skur, spaerOgRem, userId, dbConnection);

            // Success message
            ctx.attribute("message", "Tak for din ordre. En besked er nu sendt til sælgeren og du vil blive kontaktet snarest. Husk at tjekke din email og spam folder");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Fejl: " + e.getMessage());
        }

        // Render the form again with success/error message
        showAllForms(ctx, dbConnection);
    }

    private static void showSpaerOgRemForm(Context ctx, ConnectionPool dbConnection) {
        try {
            // Query the database to get the available column and table
            List<String> spaerOgRemOptions = getDimensionOptions("materiale", "spaer_og_rem", dbConnection);

            // Add the options to the context so Thymeleaf can access them
            ctx.attribute("spaerOgRemOptions", spaerOgRemOptions);
        } catch (DatabaseException e) {
            ctx.attribute("message", "Fejl på at finde Spær og Rem materialer: " + e.getMessage());
        }
    }
}


