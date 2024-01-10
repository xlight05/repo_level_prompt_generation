package com.xiaonei.api;

/**
 * 
 * 2008-6-16 上午01:32:44
 * @param <N>
 * @param <V>
 */public class Pair<N, V> implements IPair<N, V> {
    /**
     * 
     */
    public N first;
    /**
     * 
     */
    public V second;

    /**
     * 
     * @param name
     * @param value
     */
    public Pair(N name, V value) {
      this.first = name;
      this.second = value;
    }
    
    /**
     * 
     */
    public void setFirst(N first) {
        this.first = first;
    } 

    /**
     * 
     */
    public N getFirst() {
        return first;
    }

    /**
     * 
     */
    public void setSecond(V second) {
        this.second = second;
    }

    /**
     * 
     */
    public V getSecond() {
        return second;
    }
}
