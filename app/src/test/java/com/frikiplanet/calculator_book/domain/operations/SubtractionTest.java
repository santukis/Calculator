package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class SubtractionTest {

    private Operation operation;

    @Before
    public void setUp() {
        operation = new Subtraction();
    }

    @Parameters(method = "getValidSubtractionInput")
    @Test
    public void calculateShouldReturnExpectedValueWhenOperandsAreReal(
            double operand1, double operand2, double expectedValue) {
        double result = operation.calculate(new Pair<>(operand1, operand2));

        assertEquals(String.format("Result %1s should be equal to expectedValue %2s", result, expectedValue),
                result, expectedValue, 0.00001);
    }

    private Object[] getValidSubtractionInput() {
        return $(
                $(1, 1, 0),
                $(-1, -1, 0),
                $(-1, 1, -2),
                $(3.2, 2.5, 0.7)
        );
    }

    @Parameters(method = "getInvalidInput")
    @Test(expected = OperationException.class)
    public void calculateShouldThrowsWhenOperandsAreInvalid(double operand1, double operand2) {
        operation.calculate(new Pair<>(operand1, operand2));
    }

    private Object[] getInvalidInput() {
        return new Object[]{
                new Object[]{12d, Double.MAX_VALUE},
                new Object[]{Double.POSITIVE_INFINITY, 1d},
                new Object[]{-12.3d, Double.NEGATIVE_INFINITY},
                new Object[]{Double.NaN, 12d},
                new Object[]{Math.pow(2, 1024), -1d}
        };
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