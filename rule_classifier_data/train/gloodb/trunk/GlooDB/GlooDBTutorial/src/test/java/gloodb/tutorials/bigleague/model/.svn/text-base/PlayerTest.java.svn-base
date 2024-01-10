package gloodb.tutorials.bigleague.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gloodb.Lazy;
import gloodb.PersistencyAttributes;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;

import gloodb.utils.RepositorySysoutInterceptor;
import gloodb.utils.Sequence;

import static gloodb.tutorials.bigleague.model.BigLeagueUtils.*;

public class PlayerTest {
    private static final Repository repository;

    static {
        repository = new RepositorySysoutInterceptor()
            .setRepository(RepositoryFactory.createRepository("target/UnitTests/Tutorials", new BigLeagueRepositoryInitializer()));
    }

    @Before
    public void setUp() {
    	cleanupClubs(repository);
    	cleanupPlayers(repository);
    }
    
    @After
    public void tearDown() {
    	cleanupClubs(repository);
    	cleanupPlayers(repository);
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
    	Map<String, Club> clubs = createClubs(repository, "Real", "Inter");
        
    	// Create a new player
        Serializable id = Sequence.getNextId(repository, Player.class);
        Player player = new Player(id, "Ronaldo");
        player = repository.create(player);
        assertEquals("Ronaldo", player.getName());
        
        // Transfer the new player to a club. 
        player.transferToClub(new Date(), clubs.get("Real"));
        player = repository.update(player);   
        
        // The player should show on the team after the transfer.
        Clubs clubSet = (Clubs) repository.restore(Clubs.class);
        LinkedHashSet<Lazy<Player>> players  = clubSet.getTeam(clubs.get("Real"));
        assertTrue(players.contains(new Lazy<Player>(player.getId())));

        // Transfer the player to another club
        player.transferToClub(new Date(), clubs.get("Inter"));
        player = repository.update(player);

        // The player should show up only on his new club (only).
        clubSet = (Clubs) repository.restore(Clubs.class);
        players  = clubSet.getTeam(clubs.get("Real"));
        assertFalse(players.contains(new Lazy<Player>(player.getId())));
        players  = clubSet.getTeam(clubs.get("Inter"));
        assertTrue(players.contains(new Lazy<Player>(player.getId())));
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
    
    @Test
    public void testEquals() {
    	Player p1 = new Player("1", null);
    	Player p2 = new Player("2", null);
    	
    	assertThat(p1.equals(p2), is(false));
    	assertThat(p1.equals(null), is(false));
    	assertThat(p1.equals("bla"), is(false));
    	
    	assertThat(p1.equals(p1), is(true));
    	assertThat(p1.equals(new Player("1", "bla")), is(true));

    }
    
    @Test
    public void testHashCode() {
    	Player p1 = new Player("1", null);
    	Player p2 = new Player("2", null);
    	
    	assertThat(p1.hashCode(), is(not(p2.hashCode())));
    	assertThat(p1.hashCode(), is(not("bal".hashCode())));
    	
    	assertThat(p1.hashCode(), is(new Player("1", "bla").hashCode()));
    }
}
