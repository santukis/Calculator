package com.frikiplanet.calculator_book.domain;

public class Pair<Left, Right> {

    public Left left;
    public Right right;

    public Pair(Left left, Right right) {
        this.left = left;
        this.right = right;
    }
}
