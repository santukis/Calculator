package com.frikiplanet.calculator_book;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculatorPresenterTest.class,
        MathExpressionTest.class,
        MathOperationTest.class,
        MathCalculatorTest.class
})
public class MathSuite {
}
