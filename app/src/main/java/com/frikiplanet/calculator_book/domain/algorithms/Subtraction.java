package com.frikiplanet.calculator_book.domain.algorithms;

public class Subtraction implements Algorithm {

    @Override
    public Double calculate(Double left, Double right) {
        throwsIfValuesAreInvalid(left, right);

        return left - right;
    }
}
