package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

import com.frikiplanet.calculator_book.domain.operations.NoOperation;
import com.frikiplanet.calculator_book.domain.operations.Operation;
import com.frikiplanet.calculator_book.domain.operations.OperationFactory;

import java.util.List;

public class MathResolver implements Resolver {

    @Override
    public Double resolve(@NonNull List<String> symbols) {
        while (symbols.size() > 1) {
            Operation operator = getOperatorWithHigherPrecedence(symbols);

            Pair<Double, Double> operands = extractOperands(operator, symbols);
            Double result = operator.calculate(operands);
            replaceOperandsByResult(operator, result.toString(), symbols);
        }

        return convertSymbolToReal(symbols);
    }

    private Operation getOperatorWithHigherPrecedence(List<String> symbols) {
        Operation operator = new NoOperation();

        for (int position = 0; position < symbols.size(); position++) {
            String symbol = symbols.get(position);

            if (isNumeric(symbol)) continue;

            Operation currentOperation = OperationFactory.create(symbol);

            if (currentOperation.hasHigherPrecedenceThan(operator)) {
                operator = currentOperation;
                operator.setCurrentPosition(position);
            }
        }

        return operator;
    }

    private boolean isNumeric(String symbol) {
        try{
            convertToNumber(symbol);
            return true;
        } catch (OperationException exception) {
            return false;
        }
    }

    private Double convertToNumber(String operand) {
        try {
            return Double.parseDouble(operand);

        } catch (NumberFormatException | NullPointerException exception) {
            throw new OperationException(String.format("operand %s is not a real number", operand));
        }
    }

    private Pair<Double, Double> extractOperands(Operation operator, List<String> symbols) {
        Pair<Double, Double> operands = new Pair<>(operator.getIdentityElement(), operator.getIdentityElement());

        operands.right = extractRightOperand(operator, symbols);
        operands.left = extractLeftOperand(operator, symbols);

        return operands;
    }

    private Double extractRightOperand(Operation operator, List<String> symbols) {
        if (hasRightOperand(operator, symbols.size())) {
            if (!isNumeric(getRightSymbol(symbols, operator)) && operator.getCurrentPosition() + 2 < symbols.size()) {
                return convertToNumber(symbols.get(operator.getCurrentPosition() + 2)) * -1;

            } else {
                return convertToNumber(symbols.get(operator.getRightPosition()));
            }
        }
        return operator.getIdentityElement();
    }

    private Double extractLeftOperand(Operation operator, List<String> symbols) {
        if (hasLeftOperand(operator) && operator.isBinaryOperation()) {
            return convertToNumber(symbols.get(operator.getLeftPosition()));
        }
        return operator.getIdentityElement();
    }

    private void replaceOperandsByResult(Operation operator, String result, List<String> symbols) {
        symbols.set(operator.getCurrentPosition(), result);
        removeRightOperandIfRequired(operator, symbols);
        removeLeftOperandIfRequired(operator, symbols);
    }

    private void removeRightOperandIfRequired(Operation operator, List<String> symbols) {
        if (hasRightOperand(operator, symbols.size())) {
            if (!isNumeric(getRightSymbol(symbols, operator)) && operator.getCurrentPosition() + 2 < symbols.size()) {
                symbols.remove(operator.getCurrentPosition() + 2);
            }

            symbols.remove(operator.getRightPosition());
        }
    }

    private void removeLeftOperandIfRequired(Operation operator, List<String> symbols) {
        if (hasLeftOperand(operator) && operator.isBinaryOperation()) {
            symbols.remove(operator.getLeftPosition());
        }
    }

    private boolean hasRightOperand(Operation operation, int size) {
        return operation.getRightPosition() < size;
    }

    private boolean hasLeftOperand(Operation operation) {
        return operation.getLeftPosition() >= 0;
    }

    private String getRightSymbol(List<String> symbols, Operation operation) {
        return symbols.get(operation.getRightPosition());
    }

    private Double convertSymbolToReal(List<String> symbols) {
        String symbol = getFirstSymbolOrNull(symbols);
        return isNumeric(symbol) ? convertToNumber(symbol) : 0;
    }

    private String getFirstSymbolOrNull(List<String> symbols) {
        return symbols == null || symbols.isEmpty() ? null : symbols.get(0);
    }
}