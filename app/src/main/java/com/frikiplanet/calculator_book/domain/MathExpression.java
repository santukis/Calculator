package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manage the MathCalculator expression input and output
 */
public class MathExpression implements Expression {

   private static final String SQUARE_ROOT = "r";
   private static final String FACTORIAL = "f";
   private static final String SQUARE_ROOT_SCREEN = "sqrt";
   private static final String FACTORIAL_SCREEN = "fact";

   @Override
   public String read(@NonNull String expression) {
      return expression.replace(SQUARE_ROOT_SCREEN, SQUARE_ROOT)
              .replace(FACTORIAL_SCREEN, FACTORIAL)
              .replaceAll("\\s", "").trim();
   }

   @Override
   public String write(@NonNull String expression) {
      return expression.replaceAll("(?<=[-fr+x/^)])|(?=[-fr+x/^(])", "$0 ")
              .replace(SQUARE_ROOT, SQUARE_ROOT_SCREEN)
              .replace(FACTORIAL, FACTORIAL_SCREEN);
   }

   @Override
   public List<String> tokenize(@NonNull String expression) {
      return new ArrayList<>(Arrays.asList(expression.split("(?=[+x/^rf-])|(?<=[+x/^rf])")));
   }
}