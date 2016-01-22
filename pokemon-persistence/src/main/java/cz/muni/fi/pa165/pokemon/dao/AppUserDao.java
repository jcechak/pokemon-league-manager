package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.AppUser;

/**
 * Interface representing a user
 * 
 * @author Milos Bartak
 */
public interface AppUserDao {
    
    /*
     * Creates user entity in the database.
     * 
     * @param user to be created
     */
    void create(AppUser user);
    
    /*
     * Updates user entity in the database.
     * 
     * @param user to be updated
     */
    void update(AppUser user);
    
    /*
     * Deletes user entity from the database.
     * 
     * @param user to be deleted
     */
    void delete(AppUser user);
    
    /*
     * Finds user in the database by his id.
     * 
     * @param user id
     * @return user
     */
    AppUser getById(Long id);
}
