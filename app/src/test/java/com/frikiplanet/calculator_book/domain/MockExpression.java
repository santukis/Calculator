package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

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
   public List<String> tokenize(@NonNull String expression) {
      return new ArrayList<>();
   }

   @Override
   public String normalize(@NonNull String expression) {
      return null;
   }
}
