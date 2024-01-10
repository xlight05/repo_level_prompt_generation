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
package gloodb;

/**
 * GlooDB runtime exception.
 */
public class GlooException extends RuntimeException {
    private static final long serialVersionUID = 2719882340871513499L;

    private String messagePrefix;

    /**
     * Creates an exception object with null as a detailed message and cause.
     */
    public GlooException() {
        super();
    }

    /**
     * Creates an exception object with a detailed message.
     * 
     * @param message
     *            The detail message.
     */
    public GlooException(String message) {
        super(message);
    }

    /**
     * Creates an exception object with a detailed message and a cause.
     * 
     * @param message
     *            The detail message.
     * @param cause
     *            The cause of this exception.
     */
    public GlooException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception object with a cause.
     * 
     * @param cause
     *            The cause of this exception.
     */
    public GlooException(Throwable cause) {
        super(cause);
    }

    /**
     * It throws the original exception which caused this GlooException.
     * 
     * @throws Exception
     *             The original exception or this (if this is the initial
     *             exception).
     */
    public void unpackAndThrow() throws Exception {
        throw unpack();
    }

    /**
     * It returns the original exception which caused this GlooException.
     * 
     * @throws Exception
     *             The original exception or this (if this is the initial
     *             exception).
     */
    public Exception unpack() {
        Throwable cause = getCause();
        if (!(cause != null && (cause instanceof Exception)))
            return this;

        if (cause instanceof GlooException) {
            return ((GlooException) cause).unpack();
        } else {
            return (Exception) cause;
        }
    }

    /**
     * Adds a message prefix to this exception. The prefix is added at 
     * the beginning of the exception message.
     * @param prefix The message prefix.
     */
    public void addMessagePrefix(String prefix) {
        if (this.messagePrefix == null) {
            this.messagePrefix = prefix;
        } else {
            this.messagePrefix = String.format("%s; %s", this.messagePrefix, prefix);
        }
    }

    /**
     * Returns the exception as a string. If a message prefix was specified,
     * this is added at the beginning of returned string.
     * @return The exception as a string.
     */
    @Override
    public String toString() {
        return (messagePrefix != null ? String.format("%s\ncaused by: %s", messagePrefix, super.toString()) : super.toString());
    }
}
