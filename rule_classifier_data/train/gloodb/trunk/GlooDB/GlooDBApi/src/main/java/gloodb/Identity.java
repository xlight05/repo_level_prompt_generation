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
package gloodb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>The &#64;Identity annotation is used to define the fields or a method which represent the
 * identity of a persistent object. Based on the annotated fields or method, persistent
 * objects can be uniquely identified in the repository.</p>
 *
 * 
 * <p>Identity fields are immutable and should be declared final.</p>
 *
 * Example of a persistent object with identity defined by a single field.
 * <pre>
   class Foo implements Serializable {
      &#64;Identity
      private final String id;

      ...
   }
   </pre>
 *
 * Example of a persistent object with identity defined by multiple fields.
 * <pre>
   class Bar implements Serializable {
      &#64;Identity
      private final String id;

      &#64;Identity(idx=1)
      private final int sequence;
      ...
   }
   </pre>
 *
 * When classes are annotated with &#64;Identity, the annotated type becomes
 * part of the identity. Annotate the class as &#64;Identity to create a singleton
 * (only a single instance of the class can be persisted).
 * 
 * <p>The &#64;Identity annotation is ignored on interfaces. Hence, interfaces should not be used to define
 * object identity.</p>
 * Example of a persistent singleton.
 *
 <pre>
   &#64;Identity
   class Singleton implements Serializable {
      // no identity fields defined within the class
   }
   </pre>
* This is same with the following definition
 <pre>
   class Singleton implements Serializable {
      &#64;Identity
      private final Serializable id = Singleton.class;
   }
   </pre>
* Class level identity is also inheritable and it can be combined with field level identity definitions:
   <pre>
   &#64;Identity(idx=1)
   class Bar extends Singleton {
      &#64;Identity(idx=2)
      private final Serializable barObjId;
   }
   </pre>   
 * The code above is same with:   
   <pre>
   class Bar extends Singleton {
      &#64;Identity
      private final Serializable superId = Singleton.class
   		
      &#64;Identity(idx=1)
      private final Serializable thisId = Bar.class
   
      &#64;Identity(idx=2)
      private final Serializable barObjId;
   }
   </pre>     
 * Alternatively, a single method in the class can be annotated with &#64;Identity. 
 * Method identity cannot be mixed with field and / or class identity.
 * 
 * <pre>
   class Bar extends Singleton {
      private final Serializable barObjId;
      
      &#64;Identity
      public Serializable getId() {
          return barObjId;
      }
   }
   </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE ,ElementType.FIELD, ElementType.METHOD})
@Inherited
public @interface Identity {
	/**
	 * The index of identity field.
	 * @return The field index.
	 */
	int idx() default 0;
}
