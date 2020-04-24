package com.frikiplanet.calculator_book.domain;

import java.util.List;

public interface Resolver {

    Double resolve(List<String> symbols);
}
