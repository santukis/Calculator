package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;

public class SquareRoot extends Operation {

    public SquareRoot() {
        super(3);
    }

    @Override
    public Double calculate(Pair<Double, Double> operands) {
        throwsIfValuesAreInvalid(operands);

        double aux;
        double squareRoot = operands.right / 2;

        if (operands.right < 0) {
            throw new OperationException();
        }

        do {
            aux = squareRoot;
            squareRoot = (aux + (operands.right / aux)) / 2;

            throwsIfValuesAreInvalid(new Pair<>(squareRoot, 0d));
        }
        while (aux != squareRoot);

        return squareRoot;
    }

    @Override
    public Double getIdentityElement() {
        return 1d;
    }

    @Override
    public boolean isBinaryOperation() {
        return false;
    }
}
