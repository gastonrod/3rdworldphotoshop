package model.utils;

@FunctionalInterface
public interface QuadFunction<T, U, V, K, R> {
    R apply(T t, U u, V v, K k);
}
