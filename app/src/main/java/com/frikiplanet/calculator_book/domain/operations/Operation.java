package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;

public abstract class Operation {

    private final int precedence;
    private int position = -1;

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

    public void setCurrentPosition(int position) {
        this.position = position;
    }

    public int getCurrentPosition() {
        return position;
    }

    public int getRightPosition() {
        return getCurrentPosition() + 1;
    }

    public int getLeftPosition() {
        return getCurrentPosition() - 1;
    }

    public boolean hasHigherPrecedenceThan(Operation otherOperation) {
        return precedence > otherOperation.precedence;
    }
}