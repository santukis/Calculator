package com.frikiplanet.calculator_book.domain.operations;

public class OperationFactory {

    public static Operation create(String operator) {
        switch (operator) {
            case "+": return new Addition();
            case "-": return new Subtraction();
            case "x": return new Multiplication();
            case "/": return new Division();
            case "^": return new Exponentiation();
            case "f": return new Factorial();
            case "r": return new SquareRoot();
            default: return new NoOperation();
        }
    }
}
