package gloodb.tutorials.bigleague.model;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import gloodb.Lazy;
import gloodb.Repository;
import gloodb.tutorials.RepositoryFactory;
import gloodb.tutorials.bigleague.view.CannotFindClubException;
import gloodb.utils.RepositorySysoutInterceptor;
import gloodb.utils.Sequence;

import static gloodb.tutorials.bigleague.model.BigLeagueUtils.*;

public class ClubTest {
    private static final Repository repository;

    static {
        repository = new RepositorySysoutInterceptor()
            .setRepository(RepositoryFactory.createRepository("target/UnitTests/Tutorials", new BigLeagueRepositoryInitializer()));
    }

    @Before
    public void setUp() {
    	cleanupClubs(repository);
    }
    
    @After
    public void tearDown() {
    	cleanupClubs(repository);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testCreateAndRemoveClub() {
    	repository.create(new Club(Sequence.getNextId(repository, Club.class)).setName("Real"));
    	repository.create(new Club(Sequence.getNextId(repository, Club.class)).setName("Inter"));

    	Clubs clubsObj = (Clubs)repository.restore(Clubs.class);
    	Club club = clubsObj.findClubForName("Real").fetch(repository).get();
    	assertThat(club.getName(), is(equalTo("Real")));
    	
    	Set<Lazy<Club>> clubSet = clubsObj.getClubs(true);
    	assertThat(clubSet.size(), is(2));
    	Lazy<Club> [] clubs = clubSet.toArray(new Lazy[0]);
    	assertThat(clubs[0].fetch(repository).get().getName(), is(equalTo("Inter")));
    	assertThat(clubs[1].fetch(repository).get().getName(), is(equalTo("Real")));
    	
    	repository.remove(club.getId());
    	clubsObj = (Clubs)repository.restore(Clubs.class);
    	Lazy<Club> clubRef = clubsObj.findClubForName("Real");
    	assertThat(clubRef.getId(), is(nullValue()));
    }
    
    
    @Test
    public void testEquals() {
    	Club p1 = new Club("1");
    	Club p2 = new Club("2");
    	
    	assertThat(p1.equals(p2), is(false));
    	assertThat(p1.equals(null), is(false));
    	assertThat(p1.equals("bla"), is(false));
    	
    	assertThat(p1.equals(p1), is(true));
    	assertThat(p1.equals(new Club("1")), is(true));

    }
    
    @Test
    public void testHashCode() {
    	Club p1 = new Club("1");
    	Club p2 = new Club("2");
    	
    	assertThat(p1.hashCode(), is(not(p2.hashCode())));
    	assertThat(p1.hashCode(), is(not("bal".hashCode())));
    	
    	assertThat(p1.hashCode(), is(new Club("1").hashCode()));
    }
    
    @Test
    public void testFindClubByNameQuery() throws CannotFindClubException {
    	createClubs(repository, "Real", "Inter");

    	// Test finding by name
    	Club club = Clubs.findClubByName(repository, "Real", true).get();
    	assertThat(club.getName(), is(equalTo("Real")));
    	
    	club = Clubs.findClubByName(repository, "Inter", true).get();
    	assertThat(club.getName(), is(equalTo("Inter")));

    	try {
	    	club = Clubs.findClubByName(repository, "Not there", true).get();
	    	fail();
    	} catch (CannotFindClubException e) {
    		// Expected
    	}
    }

    @Test
    public void testFetchFindClubByNameQuery() throws CannotFindClubException {
    	createClubs(repository, "Real", "Inter");

    	// Fetching is off
    	Lazy<Club> club = Clubs.findClubByName(repository, "Real", false);
    	assertThat(club.get(), is(nullValue()));
    	assertThat(club.fetch(repository).get().getName(), is(equalTo("Real")));

    	// Fetching is on
    	club = Clubs.findClubByName(repository, "Real", true);
    	assertThat(club.get(), is(notNullValue()));
    	assertThat(club.get().getName(), is(equalTo("Real")));   
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testFindAllClubsQuery() {
    	createClubs(repository, "Real", "Inter");
    	
    	// Test ascending sorting
    	Set<Lazy<Club>> clubSet = Clubs.getNameSortedClubSet(repository, true, true);
    	assertThat(clubSet.size(), is(2));
    	Lazy<Club> [] clubs = clubSet.toArray(new Lazy[0]);
    	assertThat(clubs[0].get().getName(), is(equalTo("Inter")));
    	assertThat(clubs[1].get().getName(), is(equalTo("Real")));

    	// Test descending sorting
    	clubSet = Clubs.getNameSortedClubSet(repository, false, true);
    	clubs = clubSet.toArray(new Lazy[0]);
    	assertThat(clubSet.size(), is(2));
    	assertThat(clubs[0].get().getName(), is(equalTo("Real")));
    	assertThat(clubs[1].get().getName(), is(equalTo("Inter")));
    }
    
    
    @SuppressWarnings("unchecked")
	@Test
    public void testFetchFindAllClubsQuery() {
    	createClubs(repository, "Real", "Inter");
    	
    	// Test lazy fetching
    	Set<Lazy<Club>> clubSet = Clubs.getNameSortedClubSet(repository, false, false);
    	assertThat(clubSet.size(), is(2));
    	// Assert lazy values in the list are not fetched.
    	Lazy<Club> [] clubs = clubSet.toArray(new Lazy[0]);
    	assertThat(clubs[0].get(), is(nullValue()));
    	assertThat(clubs[1].get(), is(nullValue()));
    	
    	assertThat(clubs[0].fetch(repository).get().getName(), is(equalTo("Real")));
    	assertThat(clubs[1].fetch(repository).get().getName(), is(equalTo("Inter")));

    	
    	// Test lazy fetching on
    	clubSet = Clubs.getNameSortedClubSet(repository, false, true);
    	assertThat(clubSet.size(), is(2));
    	// Assert lazy values in the list are fetched.
    	clubs = clubSet.toArray(new Lazy[0]);
    	assertThat(clubs[0].get(), is(notNullValue()));
    	assertThat(clubs[1].get(), is(notNullValue()));
    	
    	assertThat(clubs[0].get().getName(), is(equalTo("Real")));
    	assertThat(clubs[1].get().getName(), is(equalTo("Inter")));
    }

}
