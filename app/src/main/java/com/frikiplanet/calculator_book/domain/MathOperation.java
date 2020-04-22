package com.frikiplanet.calculator_book.domain;

import com.frikiplanet.calculator_book.domain.algorithms.Addition;
import com.frikiplanet.calculator_book.domain.algorithms.Exponentiation;
import com.frikiplanet.calculator_book.domain.algorithms.Division;
import com.frikiplanet.calculator_book.domain.algorithms.Factorial;
import com.frikiplanet.calculator_book.domain.algorithms.Multiplication;
import com.frikiplanet.calculator_book.domain.algorithms.SquareRoot;

import java.util.EmptyStackException;
import java.util.Stack;

public class MathOperation implements Operation {

    @Override
    public Double calculate(String[] symbols) {
        Pair<Stack<String>, Stack<Double>> components = separateOperatorsFromOperands(symbols);
        double result = 0;

        try {

            result = components.right.pop();

            do {
                String operator = components.left.pop();

                if (MathSymbols.ADDITION.equals(operator)) result = new Addition().calculate(result, components.right.pop());
                else if (MathSymbols.MULTIPLICATION.equals(operator)) result = new Multiplication().calculate(result, components.right.pop());
                else if (MathSymbols.DIVISION.equals(operator)) result = new Division().calculate(result, components.right.pop());
                else if (MathSymbols.EXPONENTIATION.equals(operator)) result = new Exponentiation().calculate(result, components.right.pop());
                else if (MathSymbols.SQUARE_ROOT.equals(operator)) result = new SquareRoot().calculate(components.right.isEmpty() ? result : components.right.pop());
                else if (MathSymbols.FACTORIAL.equals(operator)) result = new Factorial().calculate(components.right.isEmpty() ? result : components.right.pop());

            } while (!components.right.isEmpty());

        } catch (EmptyStackException exception) {
            exception.printStackTrace();
        }

        return result;
    }

    private Pair<Stack<String>, Stack<Double>> separateOperatorsFromOperands(String[] symbols) {
        Stack<String> operators = new Stack<>();
        Stack<Double> numbers = new Stack<>();

        for (int position = symbols.length - 1; position >= 0; position--) {
            String symbol = symbols[position];

            if (isNumeric(symbol)) {
                Double number = Double.parseDouble(symbol);
                if (number < 0 && position > 0) operators.push(MathSymbols.ADDITION);
                numbers.push(number);
            }
            else operators.push(symbol);
        }

        return new Pair<>(operators, numbers);
    }

    private boolean isNumeric(String symbol) {
        try {
            Double.parseDouble(symbol);
            return true;

        } catch (NumberFormatException | NullPointerException exception) {
            return false;
        }
    }
}
