package com.frikiplanet.calculator_book.domain;

import com.frikiplanet.calculator_book.presentation.CalculatorPresenterTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculatorPresenterTest.class,
        MathExpressionTest.class,
        MathResolverTest.class,
        MathCalculatorTest.class
})
public class MathSuite {
}
