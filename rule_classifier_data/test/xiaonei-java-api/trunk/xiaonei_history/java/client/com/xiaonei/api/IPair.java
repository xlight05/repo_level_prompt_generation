
package com.xiaonei.api;

public interface IPair<N, V> {
    public void setFirst(N first);
    public N getFirst();
    public void setSecond(V second);
    public V getSecond();
}
