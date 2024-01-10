/*
 * Copyright 2012 Clareity Security, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.clareitysecurity.shibboleth.storage;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.opensaml.util.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * A thread-safe implementation of {@link StorageService} that publishes event when items are added or removed from the
 * service.
 * 
 * An {@link AddEntryEvent} is published after an item has been added to the storage service. A {@link RemoveEntryEvent}
 * is published after an item has been removed from the storage service. These events are published in the root
 * application context, that is the highest ancestor, of the application context presented to this class.
 * 
 * @param <KeyType> object type of the keys
 * @param <ValueType> object type of the values
 */
public class InfinispanStorageService<KeyType, ValueType> implements StorageService<KeyType, ValueType>,
        ApplicationContextAware {

	private final Logger log = LoggerFactory.getLogger(InfinispanStorageService.class);
	public final static String version = "Version 1.0.3 (2012-07-03)";

    /** Spring application context. */
    private ApplicationContext appCtx;

    /** Backing map. */
    private Map<String, Map<KeyType, ValueType>> store;
    
    // The Infinispan cache manager
    private static DefaultCacheManager manager = null;
    // The Infinispan session cache
    private static Cache<Object, Object> sessionCache = null;
    // The special name of the session partition
    private final static String sessionPartition = "session";

    /**
     * Assumptions being made about the Shibboleth storage service:
     * 
     * The partition containing session information is called "session". That is the only
     * partition that we will store in the Infinispan backed cache. Other partitions
     * in use include:
     * 
     *   loginContexts -- contains information during the authentication cycle
     *   replay        -- contains SAML ID values used to detect replay attacks
     *   
     * These and any other partitions will be stored locally only.
     */
    
    /** Constructor. */
    public InfinispanStorageService(String configFile) throws Exception {
    	
    	try {
    		
            String endofline = System.getProperty("line.separator");
            log.info(endofline + "=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=" + endofline + endofline);

	    	log.info(version);
	        store = new ConcurrentHashMap<String, Map<KeyType, ValueType>>();
	        log.info("Configuration file is [{}]", configFile);
	        
	        manager = new DefaultCacheManager(configFile);
	        log.info("Cache manager name = [{}]", manager.getName());
	        
	        sessionCache = manager.getCache(); // we will use only the default cache as configured
	        log.info("Cache name = [{}]", sessionCache.getName());
        
	        log.info(endofline);
	        
    	} catch (Exception e) {
    		log.error("Unable to instantiate Infinispan cache. Exception is [{}]", e.getMessage());
    		log.error("{}", e);
    		
    		throw new Exception("Unable to instantiate Infinispan cache.", e);
    	}
    }

    /**
     * Must be called to free up network resources. We depend on an external entity to call this method so that
     * network resources are freed. If it is not called during shutdown, the process will hang in the background
     * because of open socket resources.
     * 
     * The ClusterFilter class is coded to call this when it is unloaded by the application engine.
     */
    public void cleanup() {
    	log.info("Cleaning up cache resources.");
    	System.out.println("StorageService: Cleaning up cache resources.");
    	//sessionCache.getStatus().
		sessionCache.stop();
		manager.stop();
    }
    
    public String checkStatus(boolean fullStorageCheck) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Checking session cache information ...\r\n");
    	
    	sb.append("Cache size = [" +sessionCache.size() + "]\r\n");
    	
    	if (fullStorageCheck == true) {
    		sb.append("Items in cache:\r\n");
    		Set<Object> keys = sessionCache.keySet();
    		for (Object o: keys) {
    			sb.append("    " + o.toString() + "\r\n");
    		}
    	}
    	
    	return sb.toString();
    }
    
    /** {@inheritDoc} */
    public boolean contains(String partition, Object key) {
        if (partition == null || key == null) {
            return false;
        }

        // check for the special case first
        if (partition.equalsIgnoreCase(sessionPartition) == true) {
        	// look up the data in the Infinispan cache
        	return sessionCache.containsKey(key);
        }
        
        if (store.containsKey(partition)) {
            return store.get(partition).containsKey(key);
        }

        return false;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
	public ValueType get(String partition, Object key) {
        if (partition == null || key == null) {
            return null;
        }

        // check for the special case first
        if (partition.equalsIgnoreCase(sessionPartition) == true) {
        	// look up the data in the Infinispan cache
        	log.trace("retrieving from [session] for key [{}]", key.toString());
        	return (ValueType) sessionCache.get(key);
        }
        
        if (store.containsKey(partition)) {
        	log.trace("retrieving from [{}] for key [{}]", partition, key.toString());
        	ValueType vt = store.get(partition).get(key);
        	return vt;
        }

        return null;
    }

    /** {@inheritDoc} */
    public Iterator<KeyType> getKeys(String partition) {
        return this.new PartitionEntryIterator(partition);
    }

    /** {@inheritDoc} */
    public Iterator<String> getPartitions() {
        return this.new PartitionIterator();
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
	public ValueType put(String partition, KeyType key, ValueType value) {
        if (partition == null || key == null) {
            return null;
        }

        ValueType replacedEntry = null;
        // look for session partition first
        if (partition.equalsIgnoreCase(sessionPartition) == true) {
        	replacedEntry = (ValueType) sessionCache.put(key, value);
	    	log.debug("storing to [{}] for key [{}]", partition, key.toString());
	    	log.debug("value class is [{}]", value.getClass().toString());
        } else {
        	// any other partition is saved locally
	        Map<KeyType, ValueType> partitionMap;
	        synchronized (store) {
	            partitionMap = store.get(partition);
	            if (partitionMap == null) {
	                partitionMap = new ConcurrentHashMap<KeyType, ValueType>();
	                store.put(partition, partitionMap);
	            }
	        }
	    	log.debug("storing to [{}] for key [{}]", partition, key.toString());
	    	log.debug("value class is [{}]", value.getClass().toString());
	    	
	        replacedEntry = partitionMap.put(key, value);
        }
        
	    appCtx.publishEvent(new AddEntryEvent(this, partition, key, value));
        
//        if (log.isTraceEnabled() == true) {
//        	log.trace("Iterating through partition {}", partition);
//        	PartitionEntryIterator pei = new PartitionEntryIterator(partition);
//        	while (pei.hasNext()) {
//        		KeyType kt = pei.next();
//        		log.trace("[{}] = {}", kt.getClass().toString(), kt.toString());
//        		log.trace("object type = [{}]", partitionMap.get(kt).getClass().toString());
//        		log.trace("object = [{}]", partitionMap.get(kt));
//        		log.trace("object = [{}]", ObjectUtils.identityToString(partitionMap.get(kt)));
//        	}
//        	log.trace("Done with iteration.");
//        }
        
        return replacedEntry;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
	public ValueType remove(String partition, KeyType key) {
        if (partition == null || key == null) {
            return null;
        }

        // look for session partition first
        if (partition.equalsIgnoreCase(sessionPartition) == true) {
        	ValueType removedEntry = (ValueType) sessionCache.remove(key);
	    	log.debug("removing from  [session] for key [{}]", key.toString());
            appCtx.publishEvent(new RemoveEntryEvent(this, partition, key, removedEntry));
            return removedEntry;
        } else if (store.containsKey(partition)) {
            ValueType removedEntry = store.get(partition).remove(key);
	    	log.debug("removing from  [{}] for key [{}]", partition, key.toString());
            appCtx.publishEvent(new RemoveEntryEvent(this, partition, key, removedEntry));
            return removedEntry;
        }

        return null;
    }

    /** {@inheritDoc} */
    public void setApplicationContext(ApplicationContext ctx) {
        ApplicationContext rootContext = ctx;
        while (rootContext.getParent() != null) {
            rootContext = rootContext.getParent();
        }
        appCtx = rootContext;
    }

    /** An event indicating an item has been added to an storage service. */
    public static class AddEntryEvent<KeyType, ValueType> extends ApplicationEvent {

        /** Serial version UID. */
        private static final long serialVersionUID = -1939512157260059492L;

        /** Storage service to which the item was added. */
        private StorageService<KeyType, ValueType> storageService;

        /** Storage partition to which the item was added. */
        private String partition;

        /** Key to the added item. */
        private KeyType key;

        /** The added item. */
        private ValueType value;

        /**
         * Constructor.
         * 
         * @param storageService storage service to which an item was added
         * @param partition partition to which the entry was added
         * @param key key of the added item
         * @param value added item
         */
        public AddEntryEvent(StorageService<KeyType, ValueType> storageService, String partition, KeyType key,
                ValueType value) {
            super(storageService);
            this.storageService = storageService;
            this.partition = partition;
            this.key = key;
            this.value = value;
        }

        /**
         * Gets the storage service to which an item was added.
         * 
         * @return storage service to which an item was added
         */
        public StorageService<KeyType, ValueType> getStorageService() {
            return storageService;
        }

        /**
         * Gets the partition to which the entry was added.
         * 
         * @return partition to which the entry was added
         */
        public String getPartition() {
            return partition;
        }

        /**
         * Gets the key of the added item.
         * 
         * @return key of the added item
         */
        public KeyType getKey() {
            return key;
        }

        /**
         * Gets the added item.
         * 
         * @return added item
         */
        public ValueType getValue() {
            return value;
        }
    }

    /** An event indicating an item has been removed from an storage service. */
    public static class RemoveEntryEvent<KeyType, ValueType> extends ApplicationEvent {

        /** Serial version UID. */
        private static final long serialVersionUID = 7414605158323325366L;

        /** Storage service to which the item was removed. */
        private StorageService<KeyType, ValueType> storageService;

        /** Storage partition to which the item was removed. */
        private String partition;

        /** Key to the removed item. */
        private KeyType key;

        /** The removed item. */
        private ValueType value;

        /**
         * Constructor.
         * 
         * @param storageService storage service to which an item was removed
         * @param partition partition to which the entry was removed
         * @param key key of the removed item
         * @param value removed item
         */
        public RemoveEntryEvent(StorageService<KeyType, ValueType> storageService, String partition, KeyType key,
                ValueType value) {
            super(storageService);
            this.storageService = storageService;
            this.partition = partition;
            this.key = key;
            this.value = value;
        }

        /**
         * Gets the storage service to which an item was removed.
         * 
         * @return storage service to which an item was removed
         */
        public StorageService<KeyType, ValueType> getStorageService() {
            return storageService;
        }

        /**
         * Gets the partition to which the entry was removed.
         * 
         * @return partition to which the entry was removed
         */
        public String getPartition() {
            return partition;
        }

        /**
         * Gets the key of the removed item.
         * 
         * @return key of the removed item
         */
        public KeyType getKey() {
            return key;
        }

        /**
         * Gets the removed item.
         * 
         * @return removed item
         */
        public ValueType getValue() {
            return value;
        }
    }

    /** An iterator over the partitions of the storage service. */
    public class PartitionIterator implements Iterator<String> {

        /** Iterator over the partitions in the backing store. */
        private Iterator<String> partitionItr;

        /** Current partition. */
        private String currentParition;

        /** Constructor. */
        public PartitionIterator() {
            partitionItr = store.keySet().iterator();
        }

        /** {@inheritDoc} */
        public boolean hasNext() {
            return partitionItr.hasNext();
        }

        /** {@inheritDoc} */
        public String next() {
            currentParition = partitionItr.next();
            return currentParition;
        }

        /** {@inheritDoc} */
        public void remove() {
            Iterator<KeyType> partitionEntries = getKeys(currentParition);
            while (partitionEntries.hasNext()) {
                partitionEntries.next();
                partitionEntries.remove();
            }
            store.remove(currentParition);
        }
    }

    /** An iterator over the entries of a partition of the storage service. */
    public class PartitionEntryIterator implements Iterator<KeyType> {

        /** Partition on which we are operating. */
        private String partition;

        /** Iterator of keys within the partition. */
        private Iterator<KeyType> keysItr;

        /** Current key within the iteration. */
        private KeyType currentKey;

        /**
         * Constructor.
         * 
         * @param partition partition upon which this iterator operates
         */
        public PartitionEntryIterator(String partition) {
            this.partition = partition;
            keysItr = store.get(partition).keySet().iterator();
        }

        /** {@inheritDoc} */
        public boolean hasNext() {
            return keysItr.hasNext();
        }

        /** {@inheritDoc} */
        public KeyType next() {
            currentKey = keysItr.next();
            return currentKey;
        }

        /** {@inheritDoc} */
        public void remove() {
            InfinispanStorageService.this.remove(partition, currentKey);
        }
    }
}