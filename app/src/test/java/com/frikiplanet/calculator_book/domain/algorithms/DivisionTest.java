package com.frikiplanet.calculator_book.domain.algorithms;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class DivisionTest {

    private Algorithm algorithm;

    @Before
    public void setUp() throws Exception {
        algorithm = new Division();
    }

    @Parameters(method = "getValidDivisionInput")
    @Test
    public void divisionShouldReturnExpectedValueWhenOperandsAreReal(
            double operand1, double operand2, double expectedValue) {

        double result = algorithm.calculate(operand1, operand2);

        Truth.assertThat(result).isWithin(1.0e-10).of(expectedValue);
    }

    private Object[] getValidDivisionInput() {
        return $(
                $(6, 3, 2),
                $(3, 2, 1.5),
                $(33, -5, -6.6),
                $(0.3, -0.25, -1.2)
        );
    }

    @Parameters(method = "getInvalidDivisionInput")
    @Test(expected = OperationException.class)
    public void divisionShouldThrowWhenOperandsOrResultAreInvalid(
            Double operand1, Double operand2) {

        algorithm.calculate(operand1, operand2);
    }

    private Object[] getInvalidDivisionInput() {
        return $(
                $(Double.MAX_VALUE, 1d),
                $(1.2d, Double.NaN),
                $(Double.POSITIVE_INFINITY, 0.1d),
                $(-12d, Double.NEGATIVE_INFINITY),
                $(12d, 0d)
        );
    }

    @Parameters(method = "getNullInput")
    @Test(expected = OperationException.class)
    public void divisionShouldThrowsWhenValuesAreNull(Double operand1, Double operand2) {
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