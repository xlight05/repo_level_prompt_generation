package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Handshake extends Model
{
    public enum Status {
        WAITING_APPROVAL, ACCEPTED, REJECTED, STARTED, CANCELLED, DONE, UNKNOWN, IGNORED
    }

    @OneToOne
    public Offer offer;

    @OneToOne
    public Request request;

    public Long offererId;
    public Long requesterId;

    @Required
    @Temporal(TemporalType.TIMESTAMP)
    public Date creationDate;

    // @Required
    // @Temporal(TemporalType.TIMESTAMP)
    // public Date actualStartDate;

    // @Required
    // @Temporal(TemporalType.TIMESTAMP)
    // public Date actualEndDate;

    @Required
    @Enumerated(EnumType.STRING)
    public Status status;

    public Integer ratingOfOfferer;
    public Integer ratingOfRequester;

    public Boolean offererStart;
    public Boolean requesterStart;

    public Boolean isOriginallyAnOffer;
    
    @OneToMany(mappedBy="handshake", cascade=CascadeType.ALL)
    public List<Comment> comments;
}
