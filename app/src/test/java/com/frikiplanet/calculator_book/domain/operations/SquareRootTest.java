package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;

@RunWith(JUnitParamsRunner.class)
public class SquareRootTest {

    private Operation operation;

    @Before
    public void setUp() {
        operation = new SquareRoot();
    }

    @Parameters(method = "getValidSquareRootInput")
    @Test
    public void squareRootShouldReturnExpectedValueWhenInputIsReal(
            double radicand, double expectedValue) {

        double result = operation.calculate(new Pair<>(1d, radicand));

        assertThat(result).isWithin(1.0e-3).of(expectedValue);
    }

    private Object[] getValidSquareRootInput() {
        return $(
                $(4, 2),
                $(5, 2.236),
                $(15, 3.872),
                $(121, 11)
        );
    }

    @Parameters(method = "getInvalidSquareRootInput")
    @Test(expected = OperationException.class)
    public void squareRootShouldThrowWhenOperandIsInvalid(double radicand) {
        operation.calculate(new Pair<>(1d, radicand));
    }

    private Object[] getInvalidSquareRootInput() {
        return $(
                $(-3),
                $(Double.POSITIVE_INFINITY),
                $(Double.NaN),
                $(-2.5)
        );
    }

    @Test(expected = OperationException.class)
    public void squareRootShouldThrowsWhenValuesAreNull() {
        operation.calculate(null);
    }

}