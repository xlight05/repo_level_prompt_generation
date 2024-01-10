package dovetaildb.dbservice;

import java.io.File;

import java.io.Serializable;

public interface DbServiceFactory extends Serializable {
	DbService makeDbService(File home, DbService prevService);
}
