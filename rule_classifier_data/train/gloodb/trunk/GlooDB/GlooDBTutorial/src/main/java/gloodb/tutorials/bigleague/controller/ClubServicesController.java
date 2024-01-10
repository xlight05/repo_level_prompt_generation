package gloodb.tutorials.bigleague.controller;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import gloodb.Lazy;
import gloodb.PersistencyAttributes;
import gloodb.ReadOnlyQuery;
import gloodb.Repository;
import gloodb.UpdateQuery;
import gloodb.tutorials.bigleague.model.Club;
import gloodb.tutorials.bigleague.model.Clubs;
import gloodb.tutorials.bigleague.model.Player;
import gloodb.tutorials.bigleague.view.CannotFindClubException;
import gloodb.tutorials.bigleague.view.ClubServices;
import gloodb.utils.TransactionController;

public class ClubServicesController extends TransactionController implements ClubServices {

	public ClubServicesController(Repository repository) {
		super(repository);
	}

	@Override
	public Serializable createClub(final Club club) {
		return run(TxMode.New,  new UpdateQuery<Serializable>(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Serializable run(Repository repository, Serializable... parameters) {
				return repository.create(club).getId();
			}
		}).getResult();
	}

	@Override
	public LinkedHashSet<Lazy<Club>> getNameSortedClubSet(final boolean ascending, final boolean fetch) {
		return run(TxMode.Same, new ReadOnlyQuery<LinkedHashSet<Lazy<Club>>>(){
			private static final long serialVersionUID = 1L;

			@Override
			public LinkedHashSet<Lazy<Club>> run(Repository repository, Serializable... parameters) {
				return Clubs.getNameSortedClubSet(repository, ascending, fetch);
			}
		}).getResult();
	}

	@Override
	public Lazy<Club> findClubByName(final String clubName, final boolean fetch) throws CannotFindClubException {
		return run(TxMode.Same, new ReadOnlyQuery<Lazy<Club>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Lazy<Club> run(Repository repository, Serializable... parameters) throws CannotFindClubException {
				return Clubs.findClubByName(repository, clubName, fetch);
			} 
		}).throwSpecifiedExceptionIfAny(CannotFindClubException.class)
		  .getResult();
	}

	@Override
	public Set<Lazy<Player>> getTeamForClub(final Serializable forClub, final boolean fetch) throws CannotFindClubException {
		return run(TxMode.Same, new ReadOnlyQuery<LinkedHashSet<Lazy<Player>>>() {
			private static final long serialVersionUID = 1L;

			@Override
			public LinkedHashSet<Lazy<Player>> run(Repository repository, Serializable... parameters) throws Exception {
				return Clubs.getTeamForClub(repository, PersistencyAttributes.getIdFromVariant(Club.class, forClub), fetch);
			}
		}).throwSpecifiedExceptionIfAny(CannotFindClubException.class)
		  .getResult();
	}

}
