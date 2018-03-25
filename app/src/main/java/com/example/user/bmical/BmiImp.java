package com.example.user.bmical;

/**
 * Created by User on 13.03.2018.
 */

public class BmiImp extends BMI {
    private static final int CORRECTED_VALUE = 703;

    public BmiImp(double weight, double height){
        super(weight, height);
    }

    @Override
    public double countBMI() {
        return (getWeight() / (getHeight() * getHeight())) * CORRECTED_VALUE;
    }
}
