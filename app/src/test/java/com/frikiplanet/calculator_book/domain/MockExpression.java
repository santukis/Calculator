package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

public class MockExpression implements Expression {

   @Override
   public String read(@NonNull String expression) {
      return null;
   }

   @Override
   public String write(@NonNull String expression) {
      return null;
   }

   @Override
   public String[] tokenize(@NonNull String expression) {
      return new String[0];
   }
}
