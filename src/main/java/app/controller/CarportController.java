package app.controller;

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
        showSpærOgRemForm(ctx,dbConnection);
        // Render the form with dynamic options
        ctx.render("dimensions.html");
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
            // Query the database to get the available 'bredde' and 'længde' options using getDimensionOptions
            List<String> breddeOptions = getDimensionOptions("bredde", "dimensioner_bredde", dbConnection);
            List<String> længdeOptions = getDimensionOptions("længde", "dimensioner_længde", dbConnection);

            // Add the options to the context so Thymeleaf can access them
            ctx.attribute("breddeOptions", breddeOptions);
            ctx.attribute("længdeOptions", længdeOptions);
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
    // Might be irrelevant code, but probably not
    private static void submitForm(Context ctx, ConnectionPool dbConnection) {
        // Receive the form parameters (bredde and længde)
        String bredde = ctx.formParam("bredde");
        String længde = ctx.formParam("længde");
        String tagMateriale = ctx.formParam("tagMateriale");

        // Add simple validation for dimensions
        if (bredde == null || længde == null) {
            ctx.attribute("message", "Venligst vælg både bredde og længde.");
            showAllForms(ctx, dbConnection);
        } else if (tagMateriale == null) {
            ctx.attribute("message", "Venligst vælg tag materiale.");
            showAllForms(ctx, dbConnection);
        }
        // Process the dimensions (e.g., save to session or database)
        // Can be changed away from session and made to send to database instead, to store orders.
        ctx.sessionAttribute("bredde", bredde);
        ctx.sessionAttribute("længde", længde);
        // ctx.redirect("/nextpage");  // Redirect to another page for further action
    }

    private static void showSpærOgRemForm(Context ctx, ConnectionPool dbConnection) {
        try {
            // Query the database to get the available column and table
            List<String> spærOgRemOptions = getDimensionOptions("materiale", "spær_og_rem", dbConnection);

            // Add the options to the context so Thymeleaf can access them
            ctx.attribute("spærOgRemOptions", spærOgRemOptions);
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error fetching Spær og Rem materials: " + e.getMessage());
        }
    }
}


