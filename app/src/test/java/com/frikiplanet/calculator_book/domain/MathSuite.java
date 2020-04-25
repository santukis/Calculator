package com.frikiplanet.calculator_book.domain;

import com.frikiplanet.calculator_book.domain.operations.AdditionTest;
import com.frikiplanet.calculator_book.domain.operations.DivisionTest;
import com.frikiplanet.calculator_book.domain.operations.ExponentiationTest;
import com.frikiplanet.calculator_book.domain.operations.FactorialTest;
import com.frikiplanet.calculator_book.domain.operations.MultiplicationTest;
import com.frikiplanet.calculator_book.domain.operations.SquareRootTest;
import com.frikiplanet.calculator_book.domain.operations.SubtractionTest;
import com.frikiplanet.calculator_book.presentation.CalculatorPresenterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculatorPresenterTest.class,
        MathExpressionTest.class,
        MathResolverTest.class,
        MathCalculatorTest.class,
        AdditionTest.class,
        DivisionTest.class,
        ExponentiationTest.class,
        FactorialTest.class,
        MultiplicationTest.class,
        SquareRootTest.class,
        SubtractionTest.class
})
public class MathSuite {
}
