package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.Pair;

public class Division extends Operation {

    public Division() {
        super(1);
    }

    @Override
    public Double calculate(Pair<Double, Double> operands) {
        throwsIfValuesAreInvalid(operands);

        double result = operands.left / operands.right;

        throwsIfValuesAreInvalid(new Pair<>(result, 0d));

        return result;
    }

    @Override
    public boolean isBinaryOperation() {
        return true;
    }

    @Override
    public Double getIdentityElement() {
        return 1d;
    }
}
