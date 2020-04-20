package com.frikiplanet.calculator_book;

import androidx.annotation.NonNull;

public interface Expression {

   String read(@NonNull String expression);

   String write(@NonNull String expression);

   String addSymbol(@NonNull String expression, @NonNull String symbol);

   String removeSymbol(@NonNull String expression);

   String replaceSymbol(@NonNull String expression, @NonNull String symbol);

   String[] tokenize(@NonNull String expression);
}
