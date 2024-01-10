package gloodb.tutorials.bigleague.model;

import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

import org.junit.Test;

import gloodb.Lazy;
import gloodb.PersistencyAttributes;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import gloodb.utils.RepositorySysoutInterceptor;
import gloodb.utils.Sequence;

public class PlayerTest {
    private static final Repository repository;

    static {
        repository = new RepositorySysoutInterceptor()
            .setRepository(RepositoryFactory.createRepository("target/UnitTests/Tutorials"));
    }

    @Test
    public void testNew() {
        Player player = new Player(Sequence.getNextId(repository, Player.class), "Maradona");
        assertEquals("Maradona", player.getName());
        assertNull(player.getClub().getId());
        assertNotNull(player.getId());
    }

    @Test
    public void testCreate() {
        Players players = (Players) repository.restore(Players.class);
        int playerNo = players.size();
        
        Serializable id = Sequence.getNextId(repository, Player.class);
        Player player = new Player(id, "Maradona");
        player = repository.create(player);
        assertEquals("Maradona", player.getName());
        
        players = (Players)repository.restore(Players.class);
        assertEquals(playerNo + 1, players.size());
        
        repository.remove(player.getId());
    }

    @Test
    public void testSetClub() {
        Club club1 = repository.create(new Club(Sequence.getNextId(repository, Club.class)).setName("Real"));
        assertEquals("Real", club1.getName());
        Club club2 = repository.create(new Club(Sequence.getNextId(repository, Club.class)).setName("Inter"));
        
        Serializable id = Sequence.getNextId(repository, Player.class);
        Player player = new Player(id, "Ronaldo");
        player = repository.create(player);
        assertEquals("Ronaldo", player.getName());
        
        player.transferToClub(new Date(), club1);
        player = repository.update(player);   
        
        Clubs clubs = (Clubs) repository.restore(Clubs.class);
        LinkedList<Lazy<Player>> players  = clubs.getTeam(club1);
        assertTrue(players.contains(new Lazy<Player>(player.getId())));
        
        player.transferToClub(new Date(), club2);
        player = repository.update(player);

        clubs = (Clubs) repository.restore(Clubs.class);
        players  = clubs.getTeam(club1);
        assertFalse(players.contains(new Lazy<Player>(player.getId())));
        players  = clubs.getTeam(club2);
        assertTrue(players.contains(new Lazy<Player>(player.getId())));
         
        id = Sequence.getNextId(repository, Player.class);
        player = new Player(id, "Maradona");
        player.getClub().setReference(club1.getId());
        player = repository.create(player);
        
        players = ((Players) repository.restore(Players.class)).getNameSortedPlayers(true);
        
        repository.remove(player.getId());
    }
    
    @Test 
    public void benchmarkVariant() {
        long start, end;
        Serializable id;
        Player player = new Player(Sequence.getNextId(repository, Player.class), "Test");
        
        start = System.nanoTime();
        id = PersistencyAttributes.getIdFromVariant(Player.class, player);
        end = System.nanoTime();
        System.out.println(String.format("Id from object: %d microseconds", (end - start)/1000));
        
        Lazy<Player> reference = new Lazy<Player>(id);
        start = System.nanoTime();
        id = PersistencyAttributes.getIdFromVariant(Player.class, reference);
        end = System.nanoTime();
        System.out.println(String.format("Id from lazy: %d microseconds", (end - start)/1000));
    
        start = System.nanoTime();
        PersistencyAttributes.getIdFromVariant(Player.class, id);
        end = System.nanoTime();
        System.out.println(String.format("Id from serializable: %d microseconds", (end - start)/1000));
    
    }
}
