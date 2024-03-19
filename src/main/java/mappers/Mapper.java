package mappers;

public interface Mapper<T, E> {
    E map(T from);
}