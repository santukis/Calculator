package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MathCalculator implements Calculator {

    private Expression expression;
    private Resolver resolver;

    public MathCalculator(Expression expression, Resolver resolver) {
        this.expression = expression;
        this.resolver = resolver;
    }

    @Override
    public String addSymbol(@NonNull String to, @NonNull String symbol) throws ExpressionException {
        throwsIfSymbolIsInvalid(symbol);

        to = expression.read(to);

        if (isABinaryOperator(symbol) && endsWithBinaryOperator(to)) {
            return replaceSymbol(to, symbol);
        }

        return expression.write(to.concat(symbol));
    }

    private String replaceSymbol(String to, String symbol) {
        if (to.length() > 1) {
            to = removeSymbol(to);
            return to.concat(expression.write(symbol));
        }

        return symbol;
    }

    @Override
    public String removeSymbol(@NonNull String from) {
        from = removeLastSymbol(expression.read(from));

        while (from.endsWith(MathSymbols.FACTORIAL) || from.endsWith(MathSymbols.SQUARE_ROOT)) {
            from = removeLastSymbol(from);
        }

        return from.isEmpty() ? from : expression.write(from);
    }

    @VisibleForTesting
    String removeLastSymbol(String from) {
        if (from.isEmpty()) return from;

        int START_INDEX = 0;
        int END_INDEX = from.length() - 1;

        return from.substring(START_INDEX, END_INDEX);
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

        double result = resolver.resolve(expression.tokenize(from));

        return getFormattedNumber(result);
    }

    private void throwsIfSymbolIsInvalid(String expression) throws ExpressionException {
        for (char symbol : expression.toCharArray()) {
            if (isSymbolInvalid(String.valueOf(symbol))) {
                throw new ExpressionException(String.format("symbol %s is invalid", symbol));
            }
        }
    }

    private boolean isSymbolInvalid(String symbol) {
        return !symbol.matches("([0-9]|[-+x/]|[.]|[()^fr])");
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

    private boolean endsWithBinaryOperator(String expression) {
        expression = expression.trim();
        return expression.endsWith(MathSymbols.ADDITION) || expression.endsWith(MathSymbols.SUBTRACTION) ||
                expression.endsWith(MathSymbols.MULTIPLICATION) || expression.endsWith(MathSymbols.DIVISION) ||
                expression.endsWith(MathSymbols.EXPONENTIATION);
    }

    private String getFormattedNumber(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat formatter = new DecimalFormat("0", symbols);
        formatter.setMaximumFractionDigits(8);
        return formatter.format(value);
    }
}
