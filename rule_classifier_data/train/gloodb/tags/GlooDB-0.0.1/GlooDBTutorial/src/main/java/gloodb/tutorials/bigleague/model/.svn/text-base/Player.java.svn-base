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

import gloodb.Cloner;
import gloodb.Embedded;
import gloodb.Identity;
import gloodb.Intercepted;
import gloodb.Lazy;
import gloodb.PersistencyAttributes;
import gloodb.PreCreate;
import gloodb.PreRemove;
import gloodb.PreUpdate;
import gloodb.Repository;
import gloodb.SortingCriteria;
import gloodb.Version;
import gloodb.operators.Expression;
import gloodb.operators.FlushEmbedded;
import gloodb.operators.Remove;
import gloodb.tutorials.simple.GlooTutorialException;
import gloodb.utils.Equality;
import gloodb.utils.Equality.Operator;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Intercepted({Players.class, Clubs.class})
public class Player implements Serializable, Cloneable {
     private static final long serialVersionUID = -6506088479530421512L;

    private final Serializable id;
    
    @Version
    long version;
    
    private final String name;
    
    private Lazy<Club> club;
    
    private LinkedList<Embedded<PlayerHistory>> history;
    
    public Player(Serializable id, String name) {
        super();
        this.id = id;
        this.version = 0l;
        this.name = name;
        this.club = new Lazy<Club>((Serializable)null);
        this.history = new LinkedList<Embedded<PlayerHistory>>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        try {
            Player copy = (Player) super.clone();
            copy.club = Cloner.deepCopy(club);
            copy.history = (LinkedList<Embedded<PlayerHistory>>)history.clone();
            return copy;
        } catch (CloneNotSupportedException cnse) {
            throw new GlooTutorialException(cnse);
        }
    }

    @Identity
    public Serializable getId() {
        return id;
    }

    @SortingCriteria("Player.name")
    public String getName() {
        return name;
    }

    public Lazy<Club> getClub() {
        return club ;
    }
    
    public void transferToClub(Date fromDate, Serializable variant) {
        Serializable clubId = PersistencyAttributes.getIdFromVariant(Club.class, variant);
        club.setReference(clubId);
        
        PlayerTransfer transfer = new PlayerTransfer(fromDate, clubId);
        history.add(new Embedded<PlayerHistory>(id, "history").set(transfer));
    }
    
    public List<Lazy<PlayerHistory>> getPlayerHistory() {
        final LinkedList<Lazy<PlayerHistory>> playerHistory = new LinkedList<Lazy<PlayerHistory>>();
        Expression.iterate(history, new Expression(){
            @SuppressWarnings("unchecked")
            @Override
            public Expression evaluate(Serializable value) {
                Embedded<PlayerHistory> embeddedValue = (Embedded<PlayerHistory>) value;
                playerHistory.add(new Lazy<PlayerHistory>(embeddedValue.getId()));
                return null;
            }});
        return playerHistory;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, name: %s", id.toString(), name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        return Equality.check(this, obj, new Operator<Player>(){
            @Override
            public boolean check(Player left, Player right) {
                Serializable leftId = left.id;
                Serializable rightId = right.id;
                if(leftId == rightId) return true;
                if(rightId == null) return false;
                if(leftId == null) return false;
                return leftId.equals(rightId);
            }});
    }
    
    @PreCreate
    @PreUpdate
    void preCreateOrUpdate(Repository repository) {
        Expression.iterate(this.history, new FlushEmbedded<PlayerHistory>(repository));
    }
    
    @PreRemove
    void preRemove(Repository repository) {
        Expression.iterate(this.history,
                new Remove(repository),
                new FlushEmbedded<PlayerHistory>(repository));
    }
}
