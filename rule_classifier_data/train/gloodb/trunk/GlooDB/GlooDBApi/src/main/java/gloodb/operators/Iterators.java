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
package gloodb.operators;

import gloodb.Embedded;
import gloodb.GlooException;
import gloodb.Lazy;
import gloodb.PersistencyAttributes;
import gloodb.Reference;
import gloodb.Repository;

import java.io.Serializable;
import java.util.Collection;

/**
 * Iterator expressions for manipulating collections of persistent objects and references.
 *
 */
public final class Iterators {
	private Iterators() {}
	
	/**
	 * Iterates the collection and applies the iterative expressions.
	 * @param collection The collection to iterate
	 * @param expressions The iterative expressions to apply during iteration.
	 * @throws Exception 
	 */
	public static <T extends Serializable> void iterate(Collection<T> collection, IterativeExpression<?> ...expressions) {
		try {
			new Iterator<T>(collection, expressions).evaluate();
    	} catch(GlooException e) {
    		throw e;
		} catch (Exception e) {
			throw new GlooException(e);
		}
	}

	/**
	 * Flushes all embedded references in the iterated collection.
	 * @param repository Repository
	 * @return A FlushEmbedded expression instance.
	 */
	public static <T extends Serializable> IterativeExpression<Embedded<T>> flush(Repository repository) {
    	return new FlushEmbedded<T>(repository);
    }
	
	/**
	 * Removes all embedded references in the iterated collection.
	 * @return A RemoveEmbedded expression instance.
	 */
	public static <T extends Serializable> IterativeExpression<Embedded<T>> removeEmbedded() {
		return new RemoveEmbedded<T>();
	}
	
	/**
	 * Resets all embedded references in the iterated collection.
	 * @return A ResetEmbedded expression instance.
	 */
	public static <T extends Serializable> IterativeExpression<Embedded<T>> reset() {
		return new ResetEmbedded<T>();
	}

	/**
	 * Sets all embedded references in the iterated collection with 
	 * the values provided.
	 * @param values Values to set.
	 * @return The SetEmbedded expression instance.
	 */
	public static <T extends Serializable> IterativeExpression<Embedded<T>> set(Collection<T> values) {
		return new SetEmbedded<T>(values);
	}


	/**
	 * Fills all embedded references in the target collection with 
	 * the iterated values.
	 * @param containerId The container id.
	 * @param idPrefix The prefix used to generated embedded ids.
	 * @param embeddedValues Embedded values to set.
	 * @return The FillEmbedded expression instance.
	 */
	public static <T extends Serializable> IterativeExpression<T> fill(Serializable containerId, String idPrefix, Collection<Embedded<T>> embeddedValues) {
		return new FillEmbedded<T>(containerId, idPrefix, embeddedValues);
	}
	
	/**
	 * Wraps a collection of input ids as lazy references.
	 * @param target The target lazy reference collection.
	 * @return A WrapAsLazy expression instance.
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Serializable> IterativeExpression<T> wrapIds(Collection<Lazy<T>> target) {
		return new WrapAsLazy(target);
	}
    
	/**
	 * Wraps a collection of objects as lazy references.
	 * @param target The target lazy reference collection.
	 * @return A WrapAsLazy expression instance.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> IterativeExpression<T> wrapObjects(Collection<Lazy<T>> target) {
		return new WrapAsLazy<T>(target) {
			private static final long serialVersionUID = 1L;

			@Override
			public void evaluateIteratively(T object) {
				super.evaluateIteratively((T)PersistencyAttributes.getIdForObject(object));
			}
		};
	}

	/**
	 * Extracts the ids of the referenced objects and returns them in the ids collection.
	 * @param ids The extracted ids.
	 * @return A GetIds expression instance.
	 */
    public static <T extends Serializable> IterativeExpression<Reference<T>> id(Collection<Serializable> ids) {
    	return new GetIds<T, Serializable>(ids);
    }
    
    /**
     * Gets the referenced values in the referenced collection. The references must be pre-fetched.
     * @param referenced The referenced objects.
     * @return A ReferenceGet expression instance.
     */
	public static <T extends Serializable> IterativeExpression<Reference<T>> get(Collection<T> referenced) {
    	return new ReferenceGet<T>(referenced);
    }
    
    /**
     * Fetches the references in the iterated collection.
     * @param repository The repository.
     * @return A ReferenceFetch expression instance.
     */
    public static <T extends Serializable> IterativeExpression<Reference<T>> fetch(Repository repository) {
    	return new ReferenceFetch<T>(repository);
    }
	
	/**
	 * Restores the provided collection of ids into the values collection.
	 * @param repository The repository.
	 * @param values The restored values.
	 * @return A Restore expression instance.
	 */
	public static <I extends Serializable, V extends Serializable> IterativeExpression<I> restore(Repository repository, Collection<V> values) {
    	return new Restore<I, V>(repository, values);
    }
    
    /**
     * Removes all the objects for the ids in the input collection.
     * @param repository The repository.
     * @return A Remove expression instance.
     */
    public static <T extends Serializable> IterativeExpression<T> remove(Repository repository) {
    	return new Remove<T>(repository);
    }
    
    /**
     * Stores all the object provided in the input collection.
     * @param repository The repository.
     * @return A Store expression instance.
     */
    public static <T extends Serializable> IterativeExpression<T> store(Repository repository) {
    	return new Store<T>(repository);
    }
    
    /**
     * Clones the iterated collection into the target collection.
     * @param target The target collection.
     * @return A Clone expression instance.
     */
	public static <T extends Serializable> IterativeExpression<T> copy(Collection<T> target) {
    	return new Clone<T>(target);
    }
}