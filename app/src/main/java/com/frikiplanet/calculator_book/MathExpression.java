package com.frikiplanet.calculator_book;

import androidx.annotation.NonNull;

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
   public String addSymbol(@NonNull String expression, @NonNull String symbol) throws ExpressionException {

      throwsIfSymbolIsInvalid(symbol);

      return write(read(expression).concat(symbol));
   }

   @Override
   public String removeSymbol(@NonNull String expression) {
      expression = read(expression);

      int START_INDEX = 0;
      int END_INDEX = expression.length() - 1;

      return expression.isEmpty() ? expression : write(expression.substring(START_INDEX, END_INDEX));
   }

   @Override
   public String replaceSymbol(@NonNull String expression, @NonNull String symbol) {
      if (expression.length() > 1) {
         expression = removeSymbol(expression);
         return write(expression.concat(symbol));
      }

      return write(symbol);
   }

   public String[] tokenize(@NonNull String expression) throws ExpressionException {

      throwsIfSymbolIsInvalid(expression);

      return expression.split("(?<=[(fr+x/^])|(?=[-fr+x/^)])");
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
}