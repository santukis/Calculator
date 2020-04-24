package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;

@RunWith(JUnitParamsRunner.class)
public class FactorialTest {

    private Operation operation;

    @Before
    public void setUp() {
        operation = new Factorial();
    }

    @Parameters(method = "getValidFactorialInput")
    @Test
    public void factorialShouldReturnExpectedValueWhenOperandIsNatural(
            double operand, double expectedValue) {

        double result = operation.calculate(new Pair<>(1d, operand));

        assertThat(result).isEqualTo(expectedValue);
    }

    private Object[] getValidFactorialInput() {
        return $(
                $(0, 1),
                $(1, 1),
                $(2, 2),
                $(3, 6),
                $(4, 24),
                $(5, 120),
                $(6, 720),
                $(10, 3_628_800)
        );
    }

    @Parameters(method = "getInvalidFactorialInput")
    @Test
    public void factorialShouldThrowsWhenOperandIsInvalid(double operand) {

        try {
            operation.calculate(new Pair<>(1d, operand));
            Assert.fail("factorial should throws an Exception/Error");

        } catch (OperationException exception) {
            assertThat(exception).isNotNull();
            System.out.println("OperationException has been thrown");

        }
    }

    private Object[] getInvalidFactorialInput() {
        return $(
                $(Double.MAX_VALUE),
                $(Double.NaN),
                $(Double.POSITIVE_INFINITY),
                $(Double.NEGATIVE_INFINITY),
                $(1_000_000),
                $(-1),
                $(2.3),
                $(-2.3)
        );
    }

    @Test(expected = OperationException.class)
    public void factorialShouldThrowsWhenValuesAreNull() {
        operation.calculate(null);
    }
}