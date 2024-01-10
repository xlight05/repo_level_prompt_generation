/*
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * Contributors:
 *      Dino Octavian - initial API and implementation
 */
package gloodb.tutorials.bigleague.model;

import gloodb.Identity;
import gloodb.Lazy;
import gloodb.PostCreate;
import gloodb.PreCreate;
import gloodb.PostRemove;
import gloodb.PostUpdate;
import gloodb.Repository;
import gloodb.associations.AssociationFactory;
import gloodb.associations.OneToManyAssociation;
import gloodb.associations.UniqueIndex;
import gloodb.operators.ExpressionedInterceptor;
import gloodb.tutorials.bigleague.view.CannotFindClubException;
import gloodb.utils.StringComparator;

import static gloodb.operators.Iterators.*;


import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;

/**
 * The Clubs interceptor and singleton class. 
 * Clubs maintains the name sorted club set and the player to club association.
 * The identity of the singleton instance is the Clubs class itself.  
 *
 */
@Identity
public class Clubs extends ExpressionedInterceptor implements Serializable, Cloneable {
    private static final long serialVersionUID = 7753952326282613039L;

    /**
     * Returns the club set sorted by club names.
     * @param repository The repository.
     * @param ascending True to sort in ascending order, false to sort in descending order.
     * @param fetch True to pre-fetch all club lazy references. False otherwise.
     * @return The set of club lazy references in the specified order. If the fetch parameter is set to true, the lazy references are
     * already fetched from the repository.
     */
	public static LinkedHashSet<Lazy<Club>> getNameSortedClubSet(Repository repository, boolean ascending, boolean fetch) {
    	Clubs clubs = (Clubs) repository.restore(Clubs.class);
    	LinkedHashSet<Lazy<Club>> result = clubs.getClubs(ascending);
    	if(fetch) iterate(result, fetch(repository));
		return result;
	}
	
	/**
	 * Finds a club from a club name.
	 * @param repository The repository.
	 * @param clubName The club name.
	 * @param fetch True to pre-fetch the lazy reference.
	 * @return The club lazy reference.
	 * @throws CannotFindClubException If a club with the give name doesn't exist.
	 */
	public static Lazy<Club> findClubByName(Repository repository, String clubName, boolean fetch) throws CannotFindClubException {
    	Clubs clubs = (Clubs)repository.restore(Clubs.class);
    	Lazy<Club> result = clubs.findClubForName(clubName);
    	if(result.getId() == null) throw new CannotFindClubException();
    	if(fetch)  result.fetch(repository);
    	return result;
	}

	/**
	 * Returns the club's player line-up. 
	 * @param repository The repository.
	 * @param forClub The club variant (can be the club's id, a lazy club reference or a club object).
	 * @param fetch True to pre-fetch the player lazy references.
	 * @return A set of player lazy references.
	 * @throws CannotFindClubException If the specified club does not exist.
	 */
	public static LinkedHashSet<Lazy<Player>> getTeamForClub(Repository repository, Serializable forClub, boolean fetch) throws CannotFindClubException {
		if(!repository.contains(forClub)) throw new CannotFindClubException();
		Clubs clubs = (Clubs)repository.restore(Clubs.class);
		LinkedHashSet<Lazy<Player>> team = clubs.getTeam(forClub);
		if(fetch) iterate(team, fetch(repository));
		return team;
	}
    
	/**
	 * The unique index use to sort club by name.
	 */
    private UniqueIndex<Club, String> clubNames;
    /**
     * The player to club association.
     */
    private OneToManyAssociation<Club, Player> clubPlayerAssociation;

    /** 
     * Create the clubs interceptor instance.
     */
    @SuppressWarnings({ "serial", "unchecked" })
	public Clubs() {
        this.clubPlayerAssociation = null;
        this.clubNames = null;
        
        // Player interceptors. 
        // Invokes the updatePlayersClubAssociation after post create and post update of a player instance.
        this.adviceAfter(new InterceptorMethod<Player>() {
			@Override
			protected void evaluate(Player player, Class<? extends Annotation> clazz, Repository repository) throws Exception {
				updatePlayersClubAssociation(repository, player);
			}}, PostCreate.class, PostUpdate.class);
        
        // Invokes the removePlayerFromClubAssociation after post remove of a player instance.
        this.adviceAfter(new InterceptorMethod<Player>() {
			@Override
			protected void evaluate(Player player, Class<? extends Annotation> clazz, Repository repository) throws Exception {
				removePlayerFromClubAssociation(repository, player);
			}}, PostRemove.class);

        // Club interceptors.
        // Invokes the addClubToNameIndex after post create of a club instance.
        this.adviceAfter(new InterceptorMethod<Club>() {
			@Override
			protected void evaluate(Club club, Class<? extends Annotation> clazz, Repository repository) throws Exception {
				addClubToNameIndex(repository, club);
			}}, PostCreate.class);

        // Invokes the removeClubFromNameIndexs after post remove of a club instance.
        this.adviceAfter(new InterceptorMethod<Club>() {
			@Override
			protected void evaluate(Club club, Class<? extends Annotation> clazz, Repository repository) throws Exception {
				removeClubFromNameIndex(repository, club);
			}}, PostRemove.class);

    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        Clubs copy = (Clubs) super.clone();
        copy.clubPlayerAssociation = (OneToManyAssociation<Club, Player>) clubPlayerAssociation.clone();
        copy.clubNames = (UniqueIndex<Club, String>) clubNames.clone();
        return copy;
    }

    /**
     * Returns the set of club lazy references, sorted by name.
     * @param ascending True to sort ascending.
     * @return The set of club lazy references sorted in the required order.
     */
    public LinkedHashSet<Lazy<Club>> getClubs(boolean ascending) {
        return clubNames.getSortedSet(new LinkedHashSet<Lazy<Club>>(), ascending);
    }

    /**
     * Returns the player line-up for the given club.
     * @param forClub The club id, lazy reference or club instance.
     * @return The set of player lazy references.
     */
    public LinkedHashSet<Lazy<Player>> getTeam(Serializable forClub) {
        return clubPlayerAssociation.getAssociatesForOne(new LinkedHashSet<Lazy<Player>>(), forClub);
    }

    /**
     * Initializes the club / player association and the name index before creating the
     * clubs instance.
     * @param repository The repository.
     */
    @PreCreate
    void preCreate(final Repository repository) {
        AssociationFactory factory = AssociationFactory.getInstance(repository);
        clubPlayerAssociation = factory.newOneToManyAssociation(Club.class, Player.class);
        clubNames = factory.newUniqueIndex(Club.class, "Clubs.name", new StringComparator());
    }
    
    @Override
    public String toString() {
        return "Clubs";
    }
    
    /**
     * Returns a lazy reference to the club having the provided name. The lub id is null if no club is found 
     * for the given name. 
     * @param name The club name.
     * @return The club lazy reference. The club id is null if no club is found for the given name.
     */
    public Lazy<Club> findClubForName(String name) {
    	return clubNames.findBySortKey(name);
    }
    
    /**
     * Updates the club player association. If following the updates the association has changed,
     * this instance is updated in order to persist the changes. 
     * @param repository The repository.
     * @param player The player instance.
     */
    private void updatePlayersClubAssociation(Repository repository, Player player) {
		boolean dirty = false;
        Lazy<Club> club = player.getClub();
        dirty = (club.getId() != null) ? clubPlayerAssociation.associate(club, player): clubPlayerAssociation.dissassociateOne(player);
        if(dirty) repository.update(this);
    	
    }
    
    /**
     * Removes the player from the club player association. If following the remove the association has changed,
     * this instance i updated in order to persist the changes.
     * @param repository The repository.
     * @param player The player instance.
     */
    private void removePlayerFromClubAssociation(Repository repository, Player player) {
        if(clubPlayerAssociation.disassociateMany(player)) repository.update(this);
    }
    
    /**
     * Updates the club name index and updates this instance to persist the index changes. 
     * @param repository The repository.
     * @param club The club instance.
     */
    private void addClubToNameIndex(Repository repository, Club club) {
		clubNames.add(club);
		repository.update(this);
    }
    
    /**
     * Updates the club name index. If following the updates the index has changed,
     * this instance is updated in order to persist the changes. 
     * @param repository The repository.
     * @param club The club instance.
     */
    private void removeClubFromNameIndex(Repository repository, Club club) {
        boolean dirty = clubPlayerAssociation.dissassociateOne(club);
        dirty |= clubNames.remove(club);
        if(dirty) repository.update(this);
    }
}
