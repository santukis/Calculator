package com.frikiplanet.calculator_book.domain.operations;


import com.frikiplanet.calculator_book.domain.Pair;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

class J5AdditionTest {

    private Operation operation;

    @BeforeEach
    public void setup() {
        operation = new Addition();
    }

    @ParameterizedTest
    @MethodSource("geValidAdditionInput")
    public void calculateShouldReturnExpectedValueWhenOperandsAreReal(
            double operand1, double operand2, double expectedValue) {

        double result = operation.calculate(new Pair<>(operand1, operand2));

        assertEquals(String.format("Result %1s should be equal to expectedValue %2s", result, expectedValue),
                expectedValue, result, 0.0);
    }

    private static Stream<Arguments> geValidAdditionInput() {
        return Stream.of(
                Arguments.of(1, 1, 2),
                Arguments.of(2, -2, 0),
                Arguments.of(2, -2, 0)
        );
    }
}