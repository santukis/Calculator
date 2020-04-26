package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

import java.util.List;

public interface Expression {

    String read(@NonNull String expression);

    String write(@NonNull String expression);

    String normalize(@NonNull String expression);

    List<String> tokenize(@NonNull String expression);

    String addSymbol(@NonNull String expression, @NonNull String symbol);

    String removeSymbol(@NonNull String expression);

    String replaceSymbol(@NonNull String expression, @NonNull String symbol);

    boolean containsParenthesis(String expression);

    String getNextParenthesisExpression(String from);

    String replaceParenthesis(String from, String with);
}
