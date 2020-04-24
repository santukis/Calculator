package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

import java.util.List;

public interface Expression {

   String read(@NonNull String expression);

   String write(@NonNull String expression);

   List<String> tokenize(@NonNull String expression);
}
