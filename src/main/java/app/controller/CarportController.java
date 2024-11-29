package app.controller;

import app.exception.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.persistence.*;

import java.util.List;

public class CarportController {
    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        app.get("/dimensions", ctx -> showDimensionsForm(ctx, dbConnection));  // Show the form with dynamic options
        app.post("/submit-dimensions", ctx -> submitDimensions(ctx, dbConnection));  // Handle the form submission (Used to send data to another page)
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

        // Render the form with dynamic options
        ctx.render("dimensions.html");
    }

    // Method to call getDimensionOptions to get the available options for a column and table
    private static List<String> getDimensionOptions(String column, String table, ConnectionPool dbConnection) throws DatabaseException {
        // Use the existing method to fetch dimension options
        return CarportMapper.getDimensionOptions(column, table);
    }

    // Handle the form submission
    // Might be irrelevant code
    private static void submitDimensions(Context ctx, ConnectionPool dbConnection) {
        // Receive the form parameters (bredde and længde)
        String bredde = ctx.formParam("bredde");
        String længde = ctx.formParam("længde");

        // Add simple validation for dimensions
        if (bredde == null || længde == null) {
            ctx.attribute("message", "Please select both dimensions.");
            ctx.render("dimensions.html");
        } else {
            // Process the dimensions (e.g., save to session or database)
            // Can be changed away from session and made to send to database instead, to store orders.
            ctx.sessionAttribute("bredde", bredde);
            ctx.sessionAttribute("længde", længde);
            // ctx.redirect("/nextpage");  // Redirect to another page for further action
        }
    }
}

