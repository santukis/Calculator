package com.frikiplanet.calculator_book.domain.operations;

import com.frikiplanet.calculator_book.domain.OperationException;
import com.frikiplanet.calculator_book.domain.Pair;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.When;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnitQuickcheck.class)
public class AdditionPropertyTest {

    private Operation operation;

    @Before
    public void setup() {
        operation = new Addition();
    }

    @Property
    public void calculateShouldReturnRealNumberOrThrowOperationException(@When(seed = 10) Double leftOperand, @When(seed = 10) Double rightOperand) {
        try {
            Pair<Double, Double> operands = new Pair<>(leftOperand, rightOperand);
            System.out.println("left : " + leftOperand + " right: " + rightOperand);
            Double result = operation.calculate(operands);

            assertEquals(leftOperand + rightOperand, result, 0.0);

        } catch (OperationException exception) {

        } catch (Exception exception) {
            fail(exception.getMessage());
        }

    }
}