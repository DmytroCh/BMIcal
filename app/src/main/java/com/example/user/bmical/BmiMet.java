package com.example.user.bmical;

/**
 * Created by User on 12.03.2018.
 */

public class BmiMet extends BMI{
    private final static int CORRECTED_VALUE = 100;

    public BmiMet(double weight, double height){
        super(weight, height);
    }

    @Override
    public double countBMI() {
        return getWeight() / ((getHeight() / CORRECTED_VALUE) * (getHeight() / CORRECTED_VALUE));
    }
}
