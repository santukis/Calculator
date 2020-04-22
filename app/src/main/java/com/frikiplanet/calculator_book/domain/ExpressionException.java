package com.frikiplanet.calculator_book.domain;

public class ExpressionException extends RuntimeException {

   public ExpressionException() {}
   public ExpressionException(String message) {
      super(message);
   }
}
