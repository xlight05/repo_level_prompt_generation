package gloodb.utils;

import gloodb.GlooException;

/**
 * Exception thrown when the application tries to update the repository
 * from a read only context. For example, this occurs when trying to update
 * from a ReadOnlyQuery. Use a Query or UpdateQuery instead.
 */
public class DeadLockException extends GlooException {
	private static final long serialVersionUID = -4068448743062495753L;

	/**
	 * Default constructor.
	 */
	public DeadLockException() {
		super();
	}

	/**
	 * Creates an exception with a detailed message.
	 * @param message The mesage.
	 */
	public DeadLockException(String message) {
		super(message);
	}

	/**
	 * Creates an exception with a cause and detailed message.
	 * @param message The detailed message.
	 * @param cause The cause.
	 */
	public DeadLockException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates an exception with a cause.
	 * @param cause The cause.
	 */
	public DeadLockException(Throwable cause) {
		super(cause);
	}
}
