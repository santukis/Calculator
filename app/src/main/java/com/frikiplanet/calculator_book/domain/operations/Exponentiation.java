package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;

public class Exponentiation extends Operation {

    public Exponentiation() {
        super(2);
    }

    @Override
    public Double calculate(Pair<Double, Double> operands) {
        throwsIfValuesAreInvalid(operands);

        if (operands.right > Double.MAX_EXPONENT || operands.right < Double.MIN_EXPONENT)
            throw new OperationException("Exponent out of scope");

        if ((operands.left == 0 || operands.left == 1) && operands.right != 0)
            return operands.left;

        double result = 1;
        boolean expoNegative = operands.right < 0;

        if (expoNegative)
            operands.right = -operands.right;

        while (operands.right != 0) {
            result = new Multiplication().calculate(new Pair<>(result, operands.left));
            operands.right--;
        }

        return expoNegative ? (1 / result) : result;
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
