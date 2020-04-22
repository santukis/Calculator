package com.frikiplanet.calculator_book.algorithms;


public class Addition implements Algorithm {

    @Override
    public Double calculate(Double left, Double right) {
        throwsIfValuesAreInvalid(left, right);

        return left + right;
    }
}
