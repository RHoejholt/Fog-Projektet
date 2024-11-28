package app.services;


import app.entities.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class Calculator {



    private List<OrderItem> orderItems = new ArrayList<>();
    private int width;
    private int length;


    public Calculator(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public void calcCarport()
    {
        calcPillar();
        calcBeams();
        calcRafters();
    }

    //Stolper. Det engelske ord "Pillar" foretrækkes, da "post" og "pole" har flere betydninger.
    public static void calcPillar()
    {

    }

    //Remme
    public void calcBeams()
    {

    }

    //Spær
    public void calcRafters()
    {

    }





}
