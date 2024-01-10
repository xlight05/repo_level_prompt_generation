package pl.edu.agh.iosr.ftpserverremote.command;

import java.util.List;
import pl.edu.agh.iosr.DataAccessException;
import pl.edu.agh.iosr.ftpserverremote.data.Entity;
import pl.edu.agh.iosr.ftpserverremote.data.Server;
import pl.edu.agh.iosr.ftpserverremote.dataaccess.EntityNotFoundException;

/**
 * Interface for DataServices performing CRUD operations on Entities
 * 
 * @author Agnieszka Janowska
 */
public interface DataService {
    
    public void create(Entity entity) throws DataAccessException;
    
    public void delete(Entity entity) throws DataAccessException;
    
    public void update(Entity entity) throws DataAccessException;
    
    public Entity getById(long id) throws EntityNotFoundException, DataAccessException;
    
    public Entity getByName(String name) throws DataAccessException;
    
    public List<Entity> getAll() throws DataAccessException;
    
    public List<Entity> getByServer(Server server) throws EntityNotFoundException, DataAccessException;
}
