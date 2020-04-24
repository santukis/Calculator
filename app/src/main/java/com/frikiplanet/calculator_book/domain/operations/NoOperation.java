package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.Pair;

public class NoOperation extends Operation {

    public NoOperation() {
        super(-1);
    }

    @Override
    public Double calculate(Pair<Double, Double> operands) {
        return 0d;
    }

    @Override
    public Double getIdentityElement() {
        return 0d;
    }

    @Override
    public boolean isBinaryOperation() {
        return false;
    }
}
