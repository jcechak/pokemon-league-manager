package cz.muni.fi.pa165.pokemon.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

/**
 * Implementation of mapping service that provides mapping for DTO objects and
 * entities
 *
 * @author Jaroslav Cechak
 */
@Service
public class MappingServiceImpl implements MappingService {
    
    @Inject
    private Mapper mapper;

    @Override
    public <T> List<T> map(Collection<?> sourceCollection, Class<T> destinationClass) {
        List<T> destinationList = new LinkedList<>();
        for (Object o : sourceCollection) {
            destinationList.add(mapper.map(o, destinationClass));
        }
        
        return destinationList;
    }

    @Override
    public <T> T map(Object sourceObject, Class<T> destinationClass) {
        return mapper.map(sourceObject, destinationClass);
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }

}
