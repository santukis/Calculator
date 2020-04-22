package com.frikiplanet.calculator_book.domain.algorithms;

public class Division implements Algorithm {

    @Override
    public Double calculate(Double left, Double right) {
        throwsIfValuesAreInvalid(left, right);

        double result = left / right;

        throwsIfValuesAreInvalid(result);

        return result;
    }
}
