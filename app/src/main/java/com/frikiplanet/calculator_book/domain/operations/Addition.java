package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.Pair;

public class Addition extends Operation {

    public Addition() {
        super(0);
    }

    @Override
    public Double calculate(Pair<Double, Double> operands) {
        throwsIfValuesAreInvalid(operands);

        return operands.left + operands.right;
    }

    @Override
    public Double getIdentityElement() {
        return 0d;
    }

    @Override
    public boolean isBinaryOperation() {
        return true;
    }
}
