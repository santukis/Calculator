package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.frikiplanet.calculator_book.domain.MathSymbols.FACTORIAL;
import static com.frikiplanet.calculator_book.domain.MathSymbols.FACTORIAL_SCREEN;
import static com.frikiplanet.calculator_book.domain.MathSymbols.SQUARE_ROOT;
import static com.frikiplanet.calculator_book.domain.MathSymbols.SQUARE_ROOT_SCREEN;

/**
 * Manage the MathCalculator expression input and output
 */
public class MathExpression implements Expression {

   @Override
   public String read(@NonNull String expression) {
      return expression.replace(SQUARE_ROOT_SCREEN, SQUARE_ROOT)
              .replace(FACTORIAL_SCREEN, FACTORIAL)
              .replaceAll("\\s", "").trim();
   }

   @Override
   public String write(@NonNull String expression) {
      if (expression.startsWith(MathSymbols.PARENTHESIS_START.concat(SQUARE_ROOT)) ||
              expression.startsWith(MathSymbols.PARENTHESIS_START.concat(FACTORIAL))) {
         expression = expression.substring(1);
      }

      return expression.replaceAll("(?<=[-fr+x/^)])|(?=[-fr+x/^(])", "$0 ")
              .replace(SQUARE_ROOT, SQUARE_ROOT_SCREEN)
              .replace(FACTORIAL, FACTORIAL_SCREEN);
   }

   @Override
   public List<String> tokenize(@NonNull String expression) {
      return new ArrayList<>(Arrays.asList(expression.split("(?=[+x/^rf-])|(?<=[-+x/^rf])")));
   }
}