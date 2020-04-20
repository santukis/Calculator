package com.frikiplanet.calculator_book;

public class OperationException extends RuntimeException {

   public OperationException() {}
   public OperationException(String message) {
      super(message);
   }
}
