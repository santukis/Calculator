package com.frikiplanet.calculator_book.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MockExpression implements Expression {

   public boolean added = false;
   public boolean removed = false;
   public boolean replaced = false;

   @Override
   public String read(@NonNull String expression) {
      return expression;
   }

   @Override
   public String write(@NonNull String expression) {
      return expression;
   }

   @Override
   public List<String> tokenize(@NonNull String expression) {
      return new ArrayList<>();
   }

   @Override
   public String normalize(@NonNull String expression) {
      return null;
   }

   @Override
   public String addSymbol(@NonNull String expression, @NonNull String symbol) {
      added = true;
      return expression;
   }

   @Override
   public String removeSymbol(@NonNull String expression) {
      removed = true;
      return expression;
   }

   @Override
   public String replaceSymbol(@NonNull String expression, @NonNull String symbol) {
      replaced = true;
      return null;
   }

   @Override
   public boolean containsParenthesis(String expression) {
      return false;
   }

   @Override
   public String getNextParenthesisExpression(String from) {
      return null;
   }

   @Override
   public String replaceParenthesis(String from, String with) {
      return null;
   }
}
