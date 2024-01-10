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
import gloodb.Interceptor;
import gloodb.Lazy;
import gloodb.PostCreate;
import gloodb.PreCreate;
import gloodb.PostRemove;
import gloodb.PostUpdate;
import gloodb.Repository;
import gloodb.associations.AssociationFactory;
import gloodb.associations.OneToManyAssociation;
import gloodb.associations.UniqueIndex;
import gloodb.tutorials.simple.GlooTutorialException;
import gloodb.utils.StringComparator;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.LinkedList;

@Identity
public class Clubs implements Serializable, Cloneable, Interceptor<Serializable> {
    private static final long serialVersionUID = 7753952326282613039L;

    private UniqueIndex<Club, String> clubNames;
    private OneToManyAssociation<Club, Player> clubPlayerAssociation;

    public Clubs() {
        this.clubPlayerAssociation = null;
        this.clubNames = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
            Clubs copy = (Clubs) super.clone();
            copy.clubPlayerAssociation = (OneToManyAssociation<Club, Player>) clubPlayerAssociation.clone();
            copy.clubNames = (UniqueIndex<Club, String>) clubNames.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new GlooTutorialException(e);
        }
    }

    public LinkedList<Lazy<Club>> getClubs() {
        return clubNames.getSortedSet(true);
    }

    public LinkedList<Lazy<Player>> getTeam(Club forClub) {
        return clubPlayerAssociation.getAssociatesForOne(forClub);
    }

    @PreCreate
    void preCreate(final Repository repository) {
        AssociationFactory factory = AssociationFactory.getInstance(repository);
        clubPlayerAssociation = factory.newOneToManyAssociation(Club.class, Player.class);
        clubNames = factory.newUniqueIndex(Club.class, "Clubs.name", new StringComparator());
    }

    @Override
    public void before(Class<? extends Annotation> annotationClass, Repository repository, Serializable clubOrPlayer) {

    }

    @Override
    public void after(Class<? extends Annotation> annotationClass, Repository repository, Serializable clubOrPlayer) throws GlooTutorialException {
        if (clubOrPlayer instanceof Club)
            afterClub(annotationClass, repository, (Club) clubOrPlayer);
        else if (clubOrPlayer instanceof Player)
            afterPlayer(annotationClass, repository, (Player) clubOrPlayer);
        else
            throw new GlooTutorialException(
                    String.format("An unknown object %s was intercepted", clubOrPlayer.getClass().getName()));
    }
    
    @Override
    public String toString() {
        return "Clubs";
    }

    private void afterPlayer(Class<? extends Annotation> annotationClass, Repository repository, Player player) {
        boolean dirty = false;
        if (PostUpdate.class.equals(annotationClass) || PostCreate.class.equals(annotationClass)) {
            Lazy<Club> club = player.getClub();
            dirty = (club.getId() != null) ? clubPlayerAssociation.associate(club, player): clubPlayerAssociation.dissassociateOne(player);             
        } else if (PostRemove.class.equals(annotationClass)) {
            dirty = clubPlayerAssociation.disassociateMany(player);
        } else {
            return;
        }
        if(dirty) repository.update(this);
    }

    private void afterClub(Class<? extends Annotation> annotationClass, Repository repository, Club club) {
        boolean dirty = false;
        if(PostCreate.class.equals(annotationClass)) {
            clubNames.add(club);
            dirty = true;
        } else if (PostRemove.class.equals(annotationClass)) {
            dirty |= clubPlayerAssociation.dissassociateOne(club);
            dirty |= clubNames.remove(club);
        } else {
            return;
        }
        if(dirty) repository.update(this);
    }
}
