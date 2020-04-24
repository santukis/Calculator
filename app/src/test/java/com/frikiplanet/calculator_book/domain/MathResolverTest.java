package com.frikiplanet.calculator_book.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MathResolverTest {

    private Resolver resolver;

    @Before
    public void setUp() {
        resolver = new MathResolver();
    }


    @Parameters(method = "calculateData")
    @Test
    public void calculateShouldReturnExpectedExpression(String[] from, Double expected) {
        assertThat(resolver.resolve(new ArrayList<>(Arrays.asList(from)))).isEqualTo(expected);
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
                new Object[] {new String[]{"-", "5.3"}, -5.3d},
                new Object[] {new String[]{"2", "+", "3", "+"}, 5d},
                new Object[] {new String[]{"5.7", "+", "2.2", "-"}, 7.9d},
                new Object[] {new String[]{"2", "x", "-3", "+"}, -6d},
                new Object[] {new String[]{"2", "+", "-3", "x"}, -1d},
                new Object[] {new String[]{"6", "/", "3", "+"}, 2d},
                new Object[] {new String[]{"6", "+", "3", "x"}, 9d},
                new Object[] {new String[]{"2", "+", "2", "x"}, 4d},
                new Object[] {new String[]{"f", "4", "x", "r"}, 24d},
                new Object[] {new String[]{"2", "+", "2", "x", "3"}, 8d},
                new Object[] {new String[]{"2", "/", "2", "x", "3"}, 3d},
                new Object[] {new String[]{"15", "/", "3", "x", "4"}, 20d},
                new Object[] {new String[]{"-9", "x", "-4"}, 36d},
                new Object[] {new String[]{"-9", "x", "-", "4"}, 36d},
                new Object[] {new String[]{"4", "x", "2", "^", "3"}, 32d},
                new Object[] {new String[]{"f", "4", "x", "r", "9"}, 72d},
                new Object[] {new String[]{"f", "4", "x", "r", "9"}, 72d},
                new Object[] {new String[]{"2", "^", "3", "x", "f"}, 8d},
                new Object[] {new String[]{"2", "^", "3", "x", "f", "3"}, 48d},
                new Object[] {new String[]{"2", "x", "3", "+", "r", "49", "x"}, 13d},
                new Object[] {new String[]{"25", "/", "5", "x", "3", "+", "r", "81", "x", "2", "^", "4", "+", "f", "3", "/", "3"}, 161d},
        };
    }
}