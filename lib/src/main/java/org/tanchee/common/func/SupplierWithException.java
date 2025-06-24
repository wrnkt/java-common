package org.tanchee.common.func;

@FunctionalInterface
public interface SupplierWithException<T> {
    T get() throws Exception;
}
