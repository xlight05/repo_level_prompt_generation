/*
 * Copyright 2009 Ragupathi Palaniappan Licensed under the Apache License, Version 2.0 (the
 * "License");
 * 
 * You may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.met.j2me.exceptions;

/**
 * Generic class for referring exceptions related to the Storage operations.
 * @author Ragupathi Palaniappan
 * @since Jan 2, 2010
 */
public class StorageException extends Exception
{
    private Throwable throwable;

    /**
     * Returns the throwable.
     * 
     * @return the throwable
     */
    public Throwable getThrowable()
    {
        return throwable;
    }

    /**
     * Sets the throwable.
     * 
     * @param throwable the throwable to set
     */
    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }

    /**
     * 
     */
    public StorageException()
    {
        super("Storage Exception");
    }

    /**
     * 
     */
    public StorageException(String message)
    {
        super(message);
    }

    /**
     * 
     */
    public StorageException(String message, Throwable t)
    {
        super(message);
        this.throwable = t;

    }

}
