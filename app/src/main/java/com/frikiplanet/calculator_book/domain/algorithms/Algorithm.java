package com.frikiplanet.calculator_book.domain.algorithms;

import com.frikiplanet.calculator_book.domain.OperationException;

public interface Algorithm {

   default Double calculate(Double left, Double right) { return -1d; }
   default Double calculate(Double operand) { return -1d; }

    default void throwsIfValuesAreInvalid(Double... values) throws OperationException {
        for (Double value : values) {
            if (value == null || value == Double.MAX_VALUE || Double.isInfinite(value) || Double.isNaN(value))
                throw new OperationException();
        }
    }
}