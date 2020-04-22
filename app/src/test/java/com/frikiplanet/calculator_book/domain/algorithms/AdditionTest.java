package com.frikiplanet.calculator_book.domain.algorithms;

import com.frikiplanet.calculator_book.domain.OperationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class AdditionTest {

    private Algorithm algorithm;

    @Before
    public void setup() {
        algorithm = new Addition();
    }

    @Parameters(method = "getValidAdditionInput")
    @Test
    public void additionShouldReturnExpectedValueWhenOperandsAreReal(
            double operand1, double operand2, double expectedValue) {
        //ACT OR WHEN
        double result = algorithm.calculate(operand1, operand2);

        //ASSERT OR THEN
        assertEquals(String.format("Result %1s should be equal to expectedValue %2s", result, expectedValue),
                expectedValue, result, 0.0);
    }


    private Object[] getValidAdditionInput() {
        return new Object[]{
                new Object[]{1, 1, 2},
                new Object[]{2, -2, 0},
                new Object[]{Double.MIN_VALUE, 1, 1},
                new Object[]{3.5, 2.7, 6.2},
                new Object[]{6.0, 4.0, 10.0},
                new Object[]{4.0, 4.0, 8.0}};
    }

    @Parameters(method = "getInvalidInput")
    @Test(expected = OperationException.class)
    public void additionShouldThrowsWhenValuesAreInvalid(Double operand1, Double operand2) {
        algorithm.calculate(operand1, operand2);
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
    public void additionShouldThrowsWhenValuesAreNull(Double operand1, Double operand2) {
        algorithm.calculate(operand1, operand2);
    }

    private Object[] getNullInput() {
        return new Object[]{
                new Object[]{null, 5.0},
                new Object[]{null, null},
                new Object[]{10.0, null}
        };
    }
}