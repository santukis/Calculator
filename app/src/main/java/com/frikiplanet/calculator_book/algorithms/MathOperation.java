package com.frikiplanet.calculator_book.algorithms;

import com.frikiplanet.calculator_book.MathSymbols;

import java.util.EmptyStackException;
import java.util.Stack;

public class MathOperation {

    public double calculate(Stack<String> operators, Stack<Double> numbers) {
        double result = 0;

        try {

            result = numbers.pop();

            do {
                String operator = operators.pop();

                if (MathSymbols.ADDITION.equals(operator)) result = new Addition().calculate(result, numbers.pop());
                else if (MathSymbols.MULTIPLICATION.equals(operator)) result = new Multiplication().calculate(result, numbers.pop());
                else if (MathSymbols.DIVISION.equals(operator)) result = new Division().calculate(result, numbers.pop());
                else if (MathSymbols.EXPONENTIATION.equals(operator)) result = new Exponentiation().calculate(result, numbers.pop());
                else if (MathSymbols.SQUARE_ROOT.equals(operator)) result = new SquareRoot().calculate(result);
                else if (MathSymbols.FACTORIAL.equals(operator)) result = new Factorial().calculate(result);

            } while (!numbers.isEmpty());

        } catch (EmptyStackException exception) {
            exception.printStackTrace();
        }

        return result;
    }

}
