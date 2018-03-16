package com.example.user.bmical;

/**
 * Created by User on 12.03.2018.
 */

public class BmiMet extends BMI{

    public BmiMet(double weight, double height){
        super(weight, height);
    }

    @Override
    public double countBMI() {
        return getWeight() / ((getHeight() / 100) * (getHeight() / 100));
    }
}
