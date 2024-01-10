package gloodb.tutorials.bigleague.model;

import gloodb.Repository;
import gloodb.RepositoryInitializer;

public class BigLeagueRepositoryInitializer implements RepositoryInitializer {

	@Override
	public void initialize(Repository repository) {
		if(!repository.contains(Players.class)) repository.create(new Players());
		if(!repository.contains(Clubs.class)) repository.create(new Clubs());
	}

}
