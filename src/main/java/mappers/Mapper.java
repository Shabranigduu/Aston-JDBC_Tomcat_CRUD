package mappers;

public interface Mapper <T, E>{
    E map(T from);
    default E map(T from, E toObject){
        return toObject;
    }
}