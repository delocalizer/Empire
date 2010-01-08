/*
 * Copyright (c) 2009-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.empire.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>Field level annotation to specify which RDF property a field and its value map to.</p>
 * <p>
 * Usage:
 * <code><pre>
 * &#64;Namespaces({"", "http://xmlns.com/foaf/0.1/",
 *			 "foaf", "http://xmlns.com/foaf/0.1/",
 * 		     "dc", "http://purl.org/dc/elements/1.1/"})
 * public class MyClass {
 *  ...
 * 	&#64;RdfProperty("foaf:firstName")
 *  public String firstName;
 * }
 * </pre></code>
 * </p>
 *
 * @author Michael Grove
 * @since 0.1
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RdfProperty {

	/**
	 * The URI value (or qname) of the RDF property the field or method is mapped to
	 * @return the property URI
	 */
	public String value();

	/**
	 * Where or not to process muliple values in a collection as an rdf:List
	 * @return True to process values as an rdf:List, false to process them as multiple assertions on the property.
	 * Default value is false.
	 */
	public boolean isList() default false;
}
