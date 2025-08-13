package org.tanchee.common.text;

import java.util.HashMap;
import java.util.Map;

public final class TermFrequency {

    public static enum Gram {
        UNI_GRAM(1),
        BI_GRAM(2),
        TRI_GRAM(3),
        QUA_GRAM(4);

        private final int value;

        Gram(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private TermFrequency() {}

    public static Map<CharSequence, Integer> getTermFrequencyMap(CharSequence s, Gram nGram) {
        Map<CharSequence, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < s.length() - 1; i++) {
            CharSequence gram = s.subSequence(i, i + nGram.getValue());
            freqMap.put(gram, freqMap.getOrDefault(gram, 0) + 1);
        }
        return freqMap;
    }

    public static Map<CharSequence, Integer> getTermFrequencyMap(CharSequence s) {
        return getTermFrequencyMap(s, Gram.BI_GRAM);
    }
}
