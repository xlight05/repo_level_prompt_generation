package gloodb.tutorials.bigleague.model;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import gloodb.Lazy;
import gloodb.Repository;
import gloodb.tutorials.bigleague.model.Club;
import gloodb.tutorials.bigleague.model.Clubs;
import gloodb.tutorials.bigleague.model.Player;
import gloodb.tutorials.bigleague.model.Players;
import gloodb.utils.Sequence;

public class BigLeagueUtils {
    public static void cleanupClubs(Repository repository) {
    	Clubs clubs = (Clubs) repository.restore(Clubs.class);
    	for(Lazy<Club> clubRef: clubs.getClubs(true)) {
    		repository.remove(clubRef.getId());
    	}
    }
    
    public static Map<String, Club> createClubs(Repository repository, String...names) {
    	Map<String, Club> result = new LinkedHashMap<String, Club>();
    	for(String name: names) {
    		result.put(name, repository.create(new Club(Sequence.getNextId(repository, Club.class)).setName(name)));
    	}
    	return result;
    }
    
    public static void cleanupPlayers(Repository repository) {
    	Players players = (Players)repository.restore(Players.class);
    	for(Lazy<Player> playerRef: players.getPlayersSortedByName(true)) {
    		repository.remove(playerRef.getId());
    	}
    }
    
    public static Map<String, Player> createPlayers(Repository repository, String ...names) {
    	Map<String, Player> result = new LinkedHashMap<String, Player>();
    	for(String name: names) {
    		result.put(name, repository.create(new Player(Sequence.getNextId(repository, Player.class), name)));
    	}
    	return result;
    }
    
    public static void assignPlayersToTeams(Repository repository, Map<String, Club> clubs, Map<String, Player> players) {
    	Iterator<Club> clubIterator = clubs.values().iterator();
    	for(Player player: players.values()) {
    		if(!clubIterator.hasNext()) {
    	    	clubIterator = clubs.values().iterator();
    		}
			Club club = (Club)repository.restore(clubIterator.next().getId());
			player = (Player)repository.restore(player.getId());
			player.transferToClub(new Date(), club);
			repository.update(player);
    	}
    }
}
