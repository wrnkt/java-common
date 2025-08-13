package org.tanchee.common.text.similarity;

public interface CharSequenceSimilarityScore<R> extends CharSequenceBiComparison<R> {
    @Override
    R apply(CharSequence left, CharSequence right);
}
