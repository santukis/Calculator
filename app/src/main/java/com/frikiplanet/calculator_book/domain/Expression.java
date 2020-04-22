package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

public interface Expression {

   String read(@NonNull String expression);

   String write(@NonNull String expression);

   String[] tokenize(@NonNull String expression);
}
