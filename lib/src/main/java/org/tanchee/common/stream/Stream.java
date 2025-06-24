package org.tanchee.common.stream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tanchee.common.tuple.Pair;

public interface Stream {

    public static <A, B> java.util.stream.Stream<Pair<A, B>> zip
    (
        java.util.stream.Stream<A> streamA, 
        java.util.stream.Stream<B> streamB
    ) {
        Iterator<A> iterA = streamA.iterator();
        Iterator<B> iterB = streamB.iterator();

        List<Pair<A, B>> list = new ArrayList<>();

        while (iterA.hasNext() && iterB.hasNext()) {
            A elemA = iterA.next();
            B elemB = iterB.next();
            list.add(new Pair<A,B>(elemA, elemB));
        }
        return list.stream();
    }

}
