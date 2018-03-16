package com.example.user.bmical;

/**
 * Created by User on 02.03.2018.
 */

public final class Config {
    //Metrical system
        //Set min and max values of the height in cm
        final static double MINHEIGHT_CM=140;
        final static double MAXHEIGHT_CM=250;

        //Set min and max values of the weight in kg
        final static double MINWEIGHT_KG=40;
        final static double MAXWEIGHT_KG=300;

    //Imperial system
        final static String[] COUNTRIES={"USA", "GBR"};
        //Set min and max values of the height in in
        final static double IN = 0.0254; //1 in=0.0254 m
        final static double MINHEIGHT_IN = (MINHEIGHT_CM / 100) / IN;
        final static double MAXHEIGHT_IN = (MAXHEIGHT_CM / 100) / IN;

        //Set min and max values of the weight in lb
        final static double LB = 0.45359237; //1 lb = 0,45359237kg
        final static double MINWEIGHT_LB = MINWEIGHT_KG / LB;
        final static double MAXWEIGHT_LB = MAXWEIGHT_KG / LB;

    //Border values of BMI
        final static double MINBMI = MINWEIGHT_KG / ((MAXHEIGHT_CM / 100) * (MAXHEIGHT_CM / 100));
        final static double MAXBMI = MAXWEIGHT_KG / ((MINHEIGHT_CM / 100) * (MINHEIGHT_CM / 100));
}
