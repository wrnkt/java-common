package org.tanchee.common.text.similarity;

import java.util.function.BiFunction;

public interface CharSequenceBiComparison<R> extends BiFunction<CharSequence, CharSequence, R> {
    @Override
    R apply(CharSequence left, CharSequence right);
}
