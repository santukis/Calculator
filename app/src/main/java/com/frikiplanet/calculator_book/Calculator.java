package com.frikiplanet.calculator_book;

import androidx.annotation.NonNull;

public interface Calculator {

   String addSymbol(@NonNull String to, @NonNull String symbol);

   String removeSymbol(@NonNull String from);

   String calculate(@NonNull String from);

}
