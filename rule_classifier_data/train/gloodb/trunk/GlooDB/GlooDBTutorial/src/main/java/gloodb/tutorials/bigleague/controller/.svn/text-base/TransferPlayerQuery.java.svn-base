package gloodb.tutorials.bigleague.controller;

import java.io.Serializable;
import java.util.Date;

import gloodb.PersistencyAttributes;
import gloodb.Repository;
import gloodb.UpdateQuery;
import gloodb.tutorials.bigleague.model.Player;

@SuppressWarnings("serial")
public class TransferPlayerQuery implements UpdateQuery<Serializable> {
	private final Serializable playerVariant;
	private final Serializable clubVariant;
	private final Date transferDate;
	
	public TransferPlayerQuery(Serializable playerVariant, Serializable clubVariant, Date transferDate) {
		super();
		this.playerVariant = playerVariant;
		this.clubVariant = clubVariant;
		this.transferDate = transferDate;
	}

	@Override
	public Serializable run(Repository repository, Serializable... parameters) {
		Player player = (Player)repository.restore(PersistencyAttributes.getIdFromVariant(Player.class, playerVariant));
		player.transferToClub(transferDate, clubVariant);
		repository.update(player);
		return null;
	}
}
