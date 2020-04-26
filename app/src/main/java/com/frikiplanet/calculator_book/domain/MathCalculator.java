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
        to = expression.read(to);

        if (isABinaryOperator(symbol) && endsWithBinaryOperator(to)) {
            to = expression.replaceSymbol(to, symbol);

        } else {
            to = expression.addSymbol(to, symbol);
        }

        return expression.write(to);
    }

    @Override
    public String removeSymbol(@NonNull String from) {
        from = expression.read(from);
        from = expression.removeSymbol(from);
        return expression.write(from);
    }

    @Override
    public String calculate(@NonNull String from) throws OperationException, ExpressionException {
        from = expression.normalize(expression.read(from));

        while (expression.containsParenthesis(from)) {
            String parenthesis = expression.getNextParenthesisExpression(from);
            from = expression.replaceParenthesis(from, resolve(parenthesis));
        }

        return resolve(from);
    }

    @VisibleForTesting
    String resolve(String from) throws OperationException, ExpressionException {
        if (from.isEmpty()) return "";

        double result = resolver.resolve(expression.tokenize(from));

        return getFormattedNumber(result);
    }

    private boolean isABinaryOperator(String symbol) {
        return symbol.equals(MathSymbols.ADDITION) ||
                symbol.equals(MathSymbols.SUBTRACTION) ||
                symbol.equals(MathSymbols.MULTIPLICATION) ||
                symbol.equals(MathSymbols.DIVISION) ||
                symbol.equals(MathSymbols.EXPONENTIATION);
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
