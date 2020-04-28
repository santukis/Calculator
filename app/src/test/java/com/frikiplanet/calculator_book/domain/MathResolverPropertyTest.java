package com.frikiplanet.calculator_book.domain;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnitQuickcheck.class)
public class MathResolverPropertyTest {

    @Property
    public void tokenizeShouldReturnAtLeastOneElement(List<String> symbols) {
        MathResolver resolver = new MathResolver();
        try{
            Double result = resolver.resolve(symbols);

        } catch (OperationException exception) {

        }
    }
}