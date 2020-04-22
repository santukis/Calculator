package com.frikiplanet.calculator_book.domain.algorithms;

import com.frikiplanet.calculator_book.domain.OperationException;

public class Factorial implements Algorithm {

    @Override
    public Double calculate(Double operand) {
        throwsIfValuesAreInvalid(operand);

        if (operand < 0)
            throw new OperationException("result is not valid");

        double result = 1;

        while (operand != 0) {
            result = new Multiplication().calculate(result, operand);
            operand--;
        }

        return result;
    }
}
