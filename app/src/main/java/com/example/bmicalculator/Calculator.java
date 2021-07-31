package com.example.bmicalculator;

public class Calculator {
    public enum unit {KG, LB, CM, FT};

    public double compute(boolean isMale, double wt, unit wtUnit  , double ht, unit htUnit ) {
       wt = setWeight(wt,wtUnit);
       ht = setHeight(ht, htUnit);
        return 10000*wt / (ht * ht);
    };

    private double setWeight(double wt, unit wtUnit) {
      return (wtUnit != unit.KG)? (wt *= 2.205): wt;
   }
    private double setHeight(double ht, unit htUnit) {
        return (htUnit != unit.CM)? (ht /= 30.48): ht;
    }
    


}
