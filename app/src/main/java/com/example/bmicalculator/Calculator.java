package com.example.bmicalculator;

public class Calculator {
    public enum unit {KG, LB, CM, FT}

    ;

    public enum flag {UW, HW, OW, OB}

    public double compute(boolean isMale, double wt, unit wtUnit, double ht, unit htUnit) {
        wt = setWeight(wt, wtUnit);
        ht = setHeight(ht, htUnit);
        return 10000 * wt / (ht * ht);
    }

    ;

    private double setWeight(double wt, unit wtUnit) {
        return (wtUnit != unit.KG) ? (wt *= 2.205) : wt;
    }

    private double setHeight(double ht, unit htUnit) {
        return (htUnit != unit.CM) ? (ht *= 30.48) : ht;
    }

    public Calculator.flag getFlag(double res) {
        Calculator.flag flag = null;
        if (res < 18.5) {
            flag = Calculator.flag.UW;
        } else if (res >= 18.5 && res <= 24.9) {
            flag = Calculator.flag.HW;
        } else if (res >= 25.0 && res <= 29.9) {
            flag = Calculator.flag.OW;
        } else {
            flag = Calculator.flag.OB;
        }
        return flag;
    }

}
