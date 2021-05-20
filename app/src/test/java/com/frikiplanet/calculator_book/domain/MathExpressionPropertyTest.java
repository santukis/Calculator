package com.frikiplanet.calculator_book.domain;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.When;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(JUnitQuickcheck.class)
public class MathExpressionPropertyTest {

    @Property
    public void tokenizeShouldReturnAtLeastOneElement(@When(seed = 1) String expression) {
        MathExpression mathExpression = new MathExpression();
        List<String> result = mathExpression.tokenize(expression);
        System.out.println(expression);
        assertFalse(result.isEmpty());
    }

    @Property
    public void readShouldReturnExpressionWithNoSpaces(@When(seed = 1) String expression) {
        MathExpression mathExpression = new MathExpression();
        String result = mathExpression.read(expression);
        System.out.println(expression);
        assertTrue(result.matches("^\\S*$"));
    }
}