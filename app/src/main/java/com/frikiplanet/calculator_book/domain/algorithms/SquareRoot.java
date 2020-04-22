package com.frikiplanet.calculator_book.domain.algorithms;

import com.frikiplanet.calculator_book.domain.OperationException;

public class SquareRoot implements Algorithm {

    @Override
    public Double calculate(Double radicand) {
        throwsIfValuesAreInvalid(radicand);

        double aux;
        double squareRoot = radicand / 2;

        if (radicand < 0) {
            throw new OperationException();
        }

        do {
            aux = squareRoot;
            squareRoot = (aux + (radicand / aux)) / 2;

            throwsIfValuesAreInvalid(squareRoot);
        }
        while (aux != squareRoot);

        return squareRoot;
    }
}
