package org.tanchee.common.math;

import java.util.stream.Stream;

public class Math {

    public static boolean isFactorOfAny(Integer test, Integer factors) {
        return Stream.of(factors).anyMatch((factor) -> (test % factor == 0));
    }

    public static boolean isFactorOfAll(Integer test, Integer factors) {
        return Stream.of(factors).allMatch((factor) -> (test % factor == 0));
    }

}
