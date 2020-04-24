package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.Pair;

public class Multiplication extends Operation {

    public Multiplication() {
        super(1);
    }

    @Override
    public Double calculate(Pair<Double, Double> operands) {
        throwsIfValuesAreInvalid(operands);

        double result = operands.left * operands.right;

        throwsIfValuesAreInvalid(new Pair<>(result, 0d));

        return result;
    }

    @Override
    public Double getIdentityElement() {
        return 1d;
    }

    @Override
    public boolean isBinaryOperation() {
        return true;
    }
}
