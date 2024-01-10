package gloodb.tutorials.bigleague.model;

import gloodb.Lazy;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class PlayerTransfer extends PlayerHistory {
    private static final long serialVersionUID = -1753192419187517381L;

    final Lazy<Club> club;

    public PlayerTransfer(Date date, Serializable clubId) {
        super(date);
        this.club = new Lazy<Club>(clubId);
    }
    
    public Lazy<Club> getClub() {
        return club;
    }
    
    @Override
    public String toString() {
        return String.format("%s: transfer to %s", DateFormat.getDateInstance().format(getDate()), club.toString());
    }

}
