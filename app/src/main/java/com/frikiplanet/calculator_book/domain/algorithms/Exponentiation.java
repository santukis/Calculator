package com.frikiplanet.calculator_book.domain.algorithms;

import com.frikiplanet.calculator_book.domain.OperationException;

public class Exponentiation implements Algorithm {

    @Override
    public Double calculate(Double base, Double exponent) {
        throwsIfValuesAreInvalid(base, exponent);

        if (exponent > Double.MAX_EXPONENT || exponent < Double.MIN_EXPONENT)
            throw new OperationException("Exponent out of scope");

        if ((base == 0 || base == 1) && exponent != 0)
            return base;

        double result = 1;
        boolean expoNegative = exponent < 0;

        if (expoNegative)
            exponent = -exponent;

        while (exponent != 0) {
            result = new Multiplication().calculate(result, base);
            exponent--;
        }

        return expoNegative ? (1 / result) : result;
    }
}
