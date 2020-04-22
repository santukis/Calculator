package com.frikiplanet.calculator_book;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.frikiplanet.calculator_book.algorithms.MathOperation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Stack;

public class MathDijkstraCalculator implements Calculator {

    private Expression expression;
    private MathOperation operation;

    public MathDijkstraCalculator(Expression expression, MathOperation operation) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public String addSymbol(@NonNull String to, @NonNull String symbol) {

        if (isAnOperator(symbol) && endsWithOperator(to)) {
            return expression.replaceSymbol(to, symbol);
        }
        return expression.addSymbol(to, symbol);
    }

    @Override
    public String removeSymbol(@NonNull String from) {
        return expression.removeSymbol(from);
    }

    @Override
    public String calculate(@NonNull String from) throws OperationException, ExpressionException {

        from = expression.read(from);

        while (containsParenthesis(from)) {
            String parenthesis = getParenthesisExpression(from);
            from = replaceParenthesis(from, resolve(parenthesis));
        }

        return resolve(from);
    }

    @VisibleForTesting
    boolean containsParenthesis(String expression) {
        return expression.contains(MathSymbols.PARENTHESIS_START);
    }

    @VisibleForTesting
    String getParenthesisExpression(String from) {
        String parenthesisExpression = getRightmostParenthesis(from);
        return removeParenthesis(parenthesisExpression);
    }

    private String getRightmostParenthesis(String from) {
        int START_INDEX = getParenthesisStartIndex(from);
        int END_INDEX = getParenthesisEndIndex(from);

        return from.substring(START_INDEX, END_INDEX);
    }

    private int getParenthesisStartIndex(String from) {
        return from.lastIndexOf(MathSymbols.PARENTHESIS_START);
    }

    private int getParenthesisEndIndex(String from) {
        int index = from.indexOf(MathSymbols.PARENTHESIS_END, getParenthesisStartIndex(from));

        if (index == -1) {
            return from.length();
        }

        return ++index;
    }

    private String removeParenthesis(String from) {
        return from.replace(MathSymbols.PARENTHESIS_START, MathSymbols.EMPTY_STRING)
                .replace(MathSymbols.PARENTHESIS_END, MathSymbols.EMPTY_STRING).trim();
    }

    @VisibleForTesting
    String replaceParenthesis(String from, String with) {
        with = addOperators(from, with);
        return new StringBuilder(from)
                .replace(getParenthesisStartIndex(from), getParenthesisEndIndex(from), with)
                .toString();
    }

    private String addOperators(String expression, String to) {
        to = addOperatorBeforeParenthesis(expression, to);
        to = addOperatorAfterParenthesis(expression, to);
        return to;
    }

    private String addOperatorBeforeParenthesis(String from, String to) {
        try {
            String previousCharacter = String.valueOf(from.charAt(getParenthesisStartIndex(from) - 1));

            if (!isAnOperator(previousCharacter) && !previousCharacter.equals(MathSymbols.PARENTHESIS_START))
                return MathSymbols.MULTIPLICATION.concat(to);

        } catch (IndexOutOfBoundsException exception) {
            return to;
        }

        return to;
    }

    private String addOperatorAfterParenthesis(String from, String to) {
        try {
            String nextCharacter = String.valueOf(from.charAt(getParenthesisEndIndex(from)));

            if (!isAnOperator(nextCharacter) && !nextCharacter.equals(MathSymbols.PARENTHESIS_END))
                return to.concat(MathSymbols.MULTIPLICATION);

        } catch (IndexOutOfBoundsException exception) {
            return to;
        }

        return to;
    }

    @VisibleForTesting
    String resolve(String from) throws OperationException, ExpressionException {
        if (from.isEmpty()) return "";

        String[] symbols = expression.tokenize(from);
        Stack<String> operators = new Stack<>();
        Stack<Double> numbers = new Stack<>();

        for (int position = symbols.length - 1; position >= 0; position--) {
            String symbol = symbols[position];
            if (isAnOperator(symbol)) operators.push(symbol);
            else {
                Double number = Double.parseDouble(symbol);
                if (number < 0 && position > 0) operators.push(MathSymbols.ADDITION);
                numbers.push(number);
            }
        }

        double result = operation.calculate(operators, numbers);

        return getFormattedNumber(result);
    }

    private boolean isAnOperator(String symbol) {
        return isABinaryOperator(symbol) || isAnUnaryOperator(symbol);
    }

    private boolean isABinaryOperator(String symbol) {
        return symbol.equals(MathSymbols.ADDITION) ||
                symbol.equals(MathSymbols.SUBTRACTION) ||
                symbol.equals(MathSymbols.MULTIPLICATION) ||
                symbol.equals(MathSymbols.DIVISION) ||
                symbol.equals(MathSymbols.EXPONENTIATION);
    }

    private boolean isAnUnaryOperator(String symbol) {
        return symbol.equals(MathSymbols.SQUARE_ROOT) ||
                symbol.equals(MathSymbols.FACTORIAL);
    }

    private boolean endsWithOperator(String expression) {
        expression = expression.trim();
        return expression.endsWith(MathSymbols.ADDITION) || expression.endsWith(MathSymbols.SUBTRACTION) ||
                expression.endsWith(MathSymbols.MULTIPLICATION) || expression.endsWith(MathSymbols.DIVISION) ||
                expression.endsWith(MathSymbols.EXPONENTIATION) || expression.endsWith(MathSymbols.SQUARE_ROOT_SCREEN) ||
                expression.endsWith(MathSymbols.FACTORIAL_SCREEN) || expression.endsWith(MathSymbols.DOT);
    }

    private String getFormattedNumber(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat formatter = new DecimalFormat("0", symbols);
        formatter.setMaximumFractionDigits(8);
        return formatter.format(value);
    }
}
