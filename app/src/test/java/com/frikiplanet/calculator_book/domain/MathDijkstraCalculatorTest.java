package com.frikiplanet.calculator_book.domain;

import com.frikiplanet.calculator_book.domain.Expression;
import com.frikiplanet.calculator_book.domain.MathCalculator;
import com.frikiplanet.calculator_book.domain.MathExpression;
import com.frikiplanet.calculator_book.domain.MathOperation;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;

@RunWith(JUnitParamsRunner.class)
public class MathDijkstraCalculatorTest {

    @Parameters(method = "calculateData")
    @Test
    public void calculateShouldReturnExpectedExpression(String from, String expected) {
        Expression expression = new MathExpression();
        MathOperation operation = new MathOperation();
        MathCalculator calculator = new MathCalculator(expression, operation);

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