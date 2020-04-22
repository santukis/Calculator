package com.frikiplanet.calculator_book.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MathOperationTest {

    private Operation operation;

    @Before
    public void setUp() {
        operation = new MathOperation();
    }


    @Parameters(method = "calculateData")
    @Test
    public void calculateShouldReturnExpectedExpression(String[] from, Double expected) {
        assertThat(operation.calculate(from)).isEqualTo(expected);
    }

    private Object[] calculateData() {
        return new Object[]{
                new Object[] {new String[]{""}, 0d},
                new Object[] {new String[]{"-"}, 0d},
                new Object[] {new String[]{"1"}, 1d},
                new Object[] {new String[]{"-5"}, -5d},
                new Object[] {new String[]{"r"}, 0d},
                new Object[] {new String[]{"f"}, 0d},
                new Object[] {new String[]{"2.3", "+"}, 2.3d},
                new Object[] {new String[]{"2.3", "-"}, 2.3d},
                new Object[] {new String[]{"2.3", "x"}, 2.3d},
                new Object[] {new String[]{"2.3", "/"}, 2.3d},
                new Object[] {new String[]{"2.3", "^"}, 2.3d},
                new Object[] {new String[]{"r", "9"}, 3d},
                new Object[] {new String[]{"f", "5"}, 120d},
                new Object[] {new String[]{"2", "+", "3", "+"}, 5d},
                new Object[] {new String[]{"2", "x", "-3", "+"}, -6d},
                new Object[] {new String[]{"6", "/", "3", "+"}, 2d},
                new Object[] {new String[]{"6", "+", "3", "x"}, 9d},
                new Object[] {new String[]{"2", "+", "2", "x"}, 4d},
        };
    }

}