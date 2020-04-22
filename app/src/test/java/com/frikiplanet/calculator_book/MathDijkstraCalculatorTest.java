package com.frikiplanet.calculator_book;

import com.frikiplanet.calculator_book.algorithms.MathOperation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class MathDijkstraCalculatorTest {

    @Parameters(method = "calculateData")
    @Test
    public void calculateShouldReturnExpectedExpression(String from, String expected) {
        Expression expression = new MathExpression();
        MathOperation operation = new MathOperation();
        MathDijkstraCalculator calculator = new MathDijkstraCalculator(expression, operation);

        assertThat(calculator.calculate(from)).isEqualTo(expected);
    }

    private Object[] calculateData() {
        return $(
                $("(2+(3x2)", "8"),
                $("(2", "2"),
                $("(-5", "-5"),
                $("(-", "0"),
                $("-5+7(3", "6"),
                $("sqrt(3x3)(3+2)", "15"),
                $("-9(-3)", "27"),
                $("fact(3+3-1)", "120"),
                $("-5 + -5", "-10"),
                $("8-9-6+3(5", "-20")
        );
    }

}