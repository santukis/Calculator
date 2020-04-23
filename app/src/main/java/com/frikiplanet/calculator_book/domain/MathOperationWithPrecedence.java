package com.frikiplanet.calculator_book.domain;

import com.frikiplanet.calculator_book.domain.algorithms.Addition;
import com.frikiplanet.calculator_book.domain.algorithms.Division;
import com.frikiplanet.calculator_book.domain.algorithms.Exponentiation;
import com.frikiplanet.calculator_book.domain.algorithms.Factorial;
import com.frikiplanet.calculator_book.domain.algorithms.Multiplication;
import com.frikiplanet.calculator_book.domain.algorithms.SquareRoot;
import com.frikiplanet.calculator_book.domain.algorithms.Subtraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MathOperationWithPrecedence implements Operation {

    @Override
    public Double calculate(String[] symbols) {

        List<String> symbolsList = new ArrayList<>(Arrays.asList(symbols));

        while (symbolsList.size() > 1) {
            int operatorPosition = -1;
            int highestPrecedence = -1;

            for (int position = 0; position < symbolsList.size(); position++) {
                String symbol = symbolsList.get(position);

                if (isNumeric(symbol)) continue;

                int precedence = -1;

                switch (symbol) {
                    case "+":
                    case "-":
                        precedence = 0;
                        break;
                    case "x":
                    case "/":
                        precedence = 1;
                        break;
                    case "^":
                        precedence = 2;
                        break;
                    case "f":
                    case "r":
                        precedence = 3;
                }

                if (highestPrecedence < precedence) {
                    highestPrecedence = precedence;
                    operatorPosition = position;
                }
            }

            String operator = symbolsList.get(operatorPosition);

            if ("f".equals(operator) || "r".equals(operator)) {
                Double rightOperand;
                Double result;

                if (operatorPosition + 1 < symbolsList.size()) {
                    rightOperand = Double.parseDouble(symbolsList.get(operatorPosition + 1));
                } else {
                    //Elemento neutro
                    rightOperand = 1d;
                }

                if ("f".equals(operator)) {
                    result = new Factorial().calculate(rightOperand);
                } else {
                    result = new SquareRoot().calculate(rightOperand);
                }

                symbolsList.set(operatorPosition, result.toString());

                if (operatorPosition + 1 < symbolsList.size()) {
                    symbolsList.remove(operatorPosition + 1);
                }

            } else {
                Double rightOperand;
                Double leftOperand;
                Double result;

                if (operatorPosition + 1 < symbolsList.size()) {
                    rightOperand = Double.parseDouble(symbolsList.get(operatorPosition + 1));

                } else {
                    rightOperand = "+".equals(operator) || "-".equals(operator) ? 0d : 1d;
                }

                if (operatorPosition - 1 >= 0) {
                    leftOperand = Double.parseDouble(symbolsList.get(operatorPosition - 1));

                } else {
                    leftOperand = "+".equals(operator) || "-".equals(operator) ? 0d : 1d;
                }

                switch (operator) {
                    case "+":
                        result = new Addition().calculate(leftOperand, rightOperand);
                        break;
                    case "-":
                        result = new Subtraction().calculate(leftOperand, rightOperand);
                        break;
                    case "x":
                        result = new Multiplication().calculate(leftOperand, rightOperand);
                        break;
                    case "/":
                        result = new Division().calculate(leftOperand, rightOperand);
                        break;
                    case "^":
                        result = new Exponentiation().calculate(leftOperand, rightOperand);
                        break;
                    default:
                        result = -1d;
                }

                symbolsList.set(operatorPosition, result.toString());

                if (operatorPosition + 1 < symbolsList.size()) {
                    symbolsList.remove(operatorPosition + 1);
                }

                if (operatorPosition - 1 >= 0) {
                    symbolsList.remove(operatorPosition - 1);
                }
            }
        }

        try {
            return Double.parseDouble(symbolsList.get(0));
        } catch (Exception exception) {
            return 0d;
        }

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
