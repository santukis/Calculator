package com.frikiplanet.calculator_book;

public class ExpressionException extends RuntimeException {

   public ExpressionException() {}
   public ExpressionException(String message) {
      super(message);
   }
}
