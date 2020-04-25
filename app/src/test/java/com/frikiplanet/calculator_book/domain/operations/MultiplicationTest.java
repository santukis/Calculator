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
public class MultiplicationTest {

    private Operation operation;

    @Before
    public void setUp() {
        operation = new Multiplication();
    }

    @Parameters(method = "getValidMultiplicationInput")
    @Test
    public void calculateShouldReturnExpectedValueWhenOperandsAndResultAreReal(
            double operand1, double operand2, double expectedValue) {
        double result = operation.calculate(new Pair<>(operand1, operand2));

        //Truth
        assertThat(result).isWithin(1.0e-10).of(expectedValue);
    }

    private Object[] getValidMultiplicationInput() {
        return $(
                $(1, 1, 1),
                $(12, 0, 0),
                $(-0.3, 3, -0.9),
                $(-2, -3.2, 6.4)
        );
    }

    @Parameters(method = "getInvalidMultiplicationInput")
    @Test(expected = OperationException.class)
    public void calculateShouldThrowsWhenOperandsOrResultAreInvalid(
            double operand1, double operand2) {
        operation.calculate(new Pair<>(operand1, operand2));
    }

    private Object[] getInvalidMultiplicationInput() {
        return $(
                $(Double.NaN, -1),
                $(-12.4, Double.POSITIVE_INFINITY),
                $(Double.NEGATIVE_INFINITY, -0.8),
                $(Math.pow(2, 1000), Math.pow(2, 1000)),
                $(Math.pow(2, 1023), 2),
                $(Math.pow(2, 1024), 0)
        );
    }

    @Parameters(method = "getNullInput")
    @Test(expected = OperationException.class)
    public void calculateShouldThrowsWhenValuesAreNull(Double operand1, Double operand2) {
        operation.calculate(new Pair<>(operand1, operand2));
    }

    private Object[] getNullInput() {
        return new Object[]{
                new Object[]{null, 5.0},
                new Object[]{null, null},
                new Object[]{10.0, null}
        };
    }
}