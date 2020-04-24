package com.frikiplanet.calculator_book.domain;

import com.frikiplanet.calculator_book.domain.operations.NoOperation;
import com.frikiplanet.calculator_book.domain.operations.Operation;
import com.frikiplanet.calculator_book.domain.operations.OperationFactory;

import java.util.List;

public class MathResolver implements Resolver {

    @Override
    public Double resolve(List<String> symbols) {

        while (symbols.size() > 1) {
            Operation operator = new NoOperation();
            int operatorPosition = -1;

            for (int position = 0; position < symbols.size(); position++) {
                String symbol = symbols.get(position);

                if (isNumeric(symbol)) continue;

                Operation currentOperation = OperationFactory.create(symbol);

                if (currentOperation.compareTo(operator) > 0) {
                    operator = currentOperation;
                    operatorPosition = position;
                }
            }

            Pair<Double, Double> operands = extractOperands(operator, operatorPosition, symbols);
            Double result = operator.calculate(operands);
            replaceOperands(operator, result.toString(), operatorPosition, symbols);
        }

        return isNumeric(symbols.get(0)) ? Double.parseDouble(symbols.get(0)) : 0;
    }

    private boolean isNumeric(String symbol) {
        try {
            Double.parseDouble(symbol);
            return true;

        } catch (NumberFormatException | NullPointerException exception) {
            return false;
        }
    }

    private Pair<Double, Double> extractOperands(Operation operator, int operatorOrder, List<String> symbols) {
        Pair<Double, Double> operands = new Pair<>(operator.getIdentityElement(), operator.getIdentityElement());

        if (operatorOrder + 1 < symbols.size()) {
            if (isNumeric(symbols.get(operatorOrder + 1))) {
                operands.right = Double.parseDouble(symbols.get(operatorOrder + 1));

            } else if (operatorOrder + 2 < symbols.size()) {
                operands.right = Double.parseDouble(symbols.get(operatorOrder + 2)) * -1;
            }
        }

        if (operator.isBinaryOperation() && operatorOrder - 1 >= 0) {
            operands.left = Double.parseDouble(symbols.get(operatorOrder - 1));
        }

        return operands;
    }

    private void replaceOperands(Operation operator, String result, int operatorOrder, List<String> symbols) {
        symbols.set(operatorOrder, result);

        if (operatorOrder + 1 < symbols.size()) {
            if (!isNumeric(symbols.get(operatorOrder + 1)) && operatorOrder + 2 < symbols.size()) {
                symbols.remove(operatorOrder + 2);
            }

            symbols.remove(operatorOrder + 1);
        }

        if (operator.isBinaryOperation() && operatorOrder -1 >= 0) {
            symbols.remove(operatorOrder - 1);
        }
    }
}
