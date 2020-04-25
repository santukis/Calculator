package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;

public abstract class Operation implements Comparable<Operation> {

    private int precedence;

    public Operation(int precedence) {
        this.precedence = precedence;
    }

    public abstract Double getIdentityElement();

    public abstract boolean isBinaryOperation();

    public abstract Double calculate(Pair<Double, Double> operands) throws OperationException;

    protected void throwsIfValuesAreInvalid(Pair<Double, Double> operands) throws OperationException {
        if (operands == null || isValueInvalid(operands.left) || isValueInvalid(operands.right)) {
            throw new OperationException();
        }
    }

    private boolean isValueInvalid(Double value) {
        return value == null || value == Double.MAX_VALUE || Double.isInfinite(value) || Double.isNaN(value);
    }

    @Override
    public int compareTo(Operation other) {
        return Integer.compare(precedence, other.precedence);
    }
}