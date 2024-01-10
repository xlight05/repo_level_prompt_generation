/*******************************************************************************
 * Copyright (c) Dino Octavian.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 *  Contributors:
 *      Dino Octavian - initial API and implementation
 *******************************************************************************/
package gloodb.utils;

import gloodb.GlooException;
import gloodb.Identity;
import gloodb.PersistencyAttributes;
import gloodb.Query;
import gloodb.QueryResult;
import gloodb.Repository;
import gloodb.Version;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Class implementing persistent sequences. The programmer can use Sequence to 
 * automatically generate object identities for any persistent class. The 
 * returned identities have of the Pair<Class, BigInteger> type.
 */
@Identity
public class Sequence implements Serializable, Cloneable {
	private static final long serialVersionUID = -4776478367948855337L;
	
	@Identity(idx=1)
    private final Class<? extends Serializable> forClass;
	
    @Version
    private BigInteger value;

    /**
     * Creates a sequence for the given class.
     * @param forClass The class the sequence generates ids for.
     */
    public Sequence(Class<? extends Serializable> forClass) {
        this.forClass = forClass;
        this.value = BigInteger.ZERO;
    }

    /**
     * Returns the next id for the given class.
     * @param repository The repository.
     * @param forClass The class to generate an id for.
     * @return The next id in sequence.
     */
    public static Serializable getNextId(final Repository repository, final Class<? extends Serializable> forClass) {
        QueryResult<Serializable> result = repository.query(new Query<Serializable>() {
			private static final long serialVersionUID = 6269202030751477963L;

			public Serializable run(Repository injectedRepository, Serializable... parameters) {
                Serializable id = PersistencyAttributes.getIdForObject(new Sequence(forClass));
                Sequence sequence = (Sequence) injectedRepository.restore(id);
                if (sequence == null) {
                    sequence = new Sequence(forClass);
                    sequence = injectedRepository.create(sequence);
                }
                BigInteger value = sequence.value;
                injectedRepository.update(sequence);
                return PersistencyAttributes.getId(forClass, value);
            }
			
			@Override
			public String toString() {
			    return String.format("getNextId for %s", forClass.getName());
			}
        });
        return result.getResult();
    }
 
    /**
     * Returns the class the id is associated with.
     * @return The class the id is associated with.
     */
    public Class<? extends Serializable> getForClass() {
		return forClass;
	}

	@Override
    public String toString() {
        return String.format("Sequence for %s", this.forClass.getName());
    }
	
	@Override
	public Object clone() {
		try {
			Sequence copy = (Sequence) super.clone();
			copy.value = new BigInteger(value.toString());
			return copy;
		} catch(CloneNotSupportedException e) {
			throw new GlooException(e);
		}
	}
}
