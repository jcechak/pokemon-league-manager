package cz.muni.fi.pa165.pokemon.service;

import java.util.Collection;
import java.util.List;
import org.dozer.Mapper;

/**
 * Service for easier mapping between entities and DTO obejects. Also provides a
 * mean for mapping top level collestions between these two types.
 *
 * @author Jaroslav Cechak
 */
public interface MappingService {

    /**
     * Method for mapping any Collection of some type to List of type T
     *
     * @param <T> type of items in returned list
     * @param sourceCollection collectio to be mapped
     * @param destinationClass class type of items in returned list
     * @return list of all items of source collestion mapped to type T
     */
    <T> List<T> map(Collection<?> sourceCollection, Class<T> destinationClass);

    /**
     * Method for mapping any object to a object of given class
     *
     * @param <T> type of returned item
     * @param sourceObject object to be mapped
     * @param destinationClass class type of returned object
     * @return object that is a result of mapping source object to type T
     */
    <T> T map(Object sourceObject, Class<T> destinationClass);

    /**
     * Gets dozer mapper used for all the mappings
     *
     * @return dozer mapper
     */
    Mapper getMapper();

}
