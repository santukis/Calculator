package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;

public class Factorial extends Operation {

    public Factorial() {
        super(3);
    }

    @Override
    public Double calculate(Pair<Double, Double> operands) {
        throwsIfValuesAreInvalid(operands);

        if (operands.right < 0)
            throw new OperationException("result is not valid");

        double result = 1;

        while (operands.right != 0) {
            result = new Multiplication().calculate(new Pair<>(result, operands.right));
            operands.right--;
        }

        return result;
    }

    @Override
    public boolean isBinaryOperation() {
        return false;
    }

    @Override
    public Double getIdentityElement() {
        return 1d;
    }
}
