package com.example.user.bmical;

/**
 * Created by User on 12.03.2018.
 */

public abstract class BMI {
    private double weight;
    private double height;

    public BMI(double weight, double height){
        this.weight = weight;
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public abstract double countBMI();
}
