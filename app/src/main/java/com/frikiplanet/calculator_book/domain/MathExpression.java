package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
              .replaceAll("\\s", "");
   }

   @Override
   public String write(@NonNull String expression) {
      return expression.replaceAll("(?<=[-fr+x/^)])|(?=[-fr+x/^(])", "$0 ")
              .replace(SQUARE_ROOT, SQUARE_ROOT_SCREEN)
              .replace(FACTORIAL, FACTORIAL_SCREEN);
   }

   @Override
   public String normalize(@NonNull String expression) {
      StringBuilder normalizedExpression = new StringBuilder(expression);
      Pattern pattern = Pattern.compile("(?=[\\d|\\)||\\.][\\(|r|f])|(?=[\\)][\\d|\\.])");
      Matcher matcher = pattern.matcher(expression);

      int offset = 0;

      while (matcher.find()) {
         normalizedExpression.insert(matcher.start() + ++offset, MathSymbols.MULTIPLICATION);
      }

      return normalizedExpression.toString();
   }

   @Override
   public List<String> tokenize(@NonNull String expression) {
      return new ArrayList<>(Arrays.asList(expression.split("(?=[+x/^rf-])|(?<=[-+x/^rf])")));
   }

   @Override
   public String addSymbol(@NonNull String expression, @NonNull String symbol) {
      throwsIfSymbolIsInvalid(symbol);
      return expression.concat(symbol);
   }

   @Override
   public String removeSymbol(@NonNull String expression) {
      expression = removeLastSymbol(expression);
      while (expression.endsWith(MathSymbols.FACTORIAL) || expression.endsWith(MathSymbols.SQUARE_ROOT)) {
         expression = removeLastSymbol(expression);
      }
      return expression;
   }

   @VisibleForTesting
   String removeLastSymbol(String from) {
      if (from.isEmpty()) return from;

      int START_INDEX = 0;
      int END_INDEX = from.length() - 1;

      return from.substring(START_INDEX, END_INDEX);
   }

   @Override
   public String replaceSymbol(@NonNull String expression, @NonNull String symbol) {
      if (expression.length() > 1) {
         expression = removeSymbol(expression);
         return addSymbol(expression, symbol);
      }
      return symbol;
   }

   @Override
   public boolean containsParenthesis(String expression) {
      return expression.contains(MathSymbols.PARENTHESIS_START);
   }

   @Override
   public String getNextParenthesisExpression(String from) {
      String parenthesisExpression = getRightmostParenthesis(from);
      return removeParenthesis(parenthesisExpression);
   }

   private String getRightmostParenthesis(String from) {
      int START_INDEX = getParenthesisStartIndex(from);
      int END_INDEX = getParenthesisEndIndex(from);

      return from.substring(START_INDEX, END_INDEX);
   }

   private int getParenthesisStartIndex(String from) {
      return from.lastIndexOf(MathSymbols.PARENTHESIS_START);
   }

   private int getParenthesisEndIndex(String from) {
      int index = from.indexOf(MathSymbols.PARENTHESIS_END, getParenthesisStartIndex(from));

      if (index == -1) {
         return from.length();
      }

      return ++index;
   }

   private String removeParenthesis(String from) {
      return from.replace(MathSymbols.PARENTHESIS_START, MathSymbols.EMPTY_STRING)
              .replace(MathSymbols.PARENTHESIS_END, MathSymbols.EMPTY_STRING).trim();
   }

   @Override
   public String replaceParenthesis(String from, String with) {
      return new StringBuilder(from)
              .replace(getParenthesisStartIndex(from), getParenthesisEndIndex(from), with)
              .toString();
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