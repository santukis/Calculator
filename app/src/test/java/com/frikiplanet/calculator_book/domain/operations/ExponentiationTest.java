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
public class ExponentiationTest {

    private Operation algoritm;

    @Before
    public void setUp() throws Exception {
        algoritm = new Exponentiation();
    }

    @Parameters(method = "getValidExponentiationInput")
    @Test
    public void exponentiationShouldReturnExpectedValueWhenInputAreIntegers(
            double base, double exponent, double expectedValue) {

        double result = algoritm.calculate(new Pair<>(base, exponent));

        assertThat(result).isWithin(1e-10).of(expectedValue);
    }

    private Object[] getValidExponentiationInput() {
        return $(
                $(0, 0, 1),
                $(2, 0, 1),
                $(2, 1, 2),
                $(2.3, 5, 64.36343),
                $(-3, 4, 81),
                $(-3, 3, -27),
                $(2, -2, 0.25),
                $(-3, -5, -0.00411522633)
        );
    }

    @Parameters(method = "getInvalidExponentiationInput")
    @Test(expected = OperationException.class)
    public void exponentiationShouldThrowWhenOperandsAreInvalid(
            double base, double exponent) {
        algoritm.calculate(new Pair<>(base, exponent));
    }

    private Object[] getInvalidExponentiationInput() {
        return $(
                $(5, 3.3),
                $(-3, -1.2),
                $(Double.NEGATIVE_INFINITY, 2),
                $(-3, Double.POSITIVE_INFINITY),
                $(Double.NaN, -1),
                $(2, Double.MAX_VALUE),
                $(0, 100_000_000),
                $(1, 100_000_000),
                $(2d, -1024),
                $(2d, 1024)
        );
    }

    @Parameters(method = "getNullInput")
    @Test(expected = OperationException.class)
    public void exponentiationShouldThrowsWhenValuesAreNull(Double base, Double exponent) {
        algoritm.calculate(new Pair<>(base, exponent));
    }

    private Object[] getNullInput() {
        return new Object[]{
                new Object[]{null, 5.0},
                new Object[]{null, null},
                new Object[]{10.0, null}
        };
    }
}