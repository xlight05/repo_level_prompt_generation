package gloodb.tutorials.bigleague.view;

import java.io.Serializable;
import java.util.Set;

import gloodb.Lazy;
import gloodb.tutorials.bigleague.model.Club;
import gloodb.tutorials.bigleague.model.Player;

public interface ClubServices {
	Serializable createClub(Club club);
	Lazy<Club> findClubByName(String clubName, boolean fetch) throws CannotFindClubException;
	Set<Lazy<Club>> getNameSortedClubSet(boolean ascending, boolean fetch);
	Set<Lazy<Player>> getTeamForClub(Serializable forClub, boolean fetch) throws CannotFindClubException;
}
