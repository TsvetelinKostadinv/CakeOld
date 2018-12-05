/*
 * 05/12/2018 at 16:43:21 ÷.
 * IgnoreParser.java created by Tsvetelin
 */

package com.cake.syntax.parsers;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention ( CLASS )
@Target ( TYPE )
/**
 * 
 * Annotates that this type should be ignored if found by <code>ParserContainer</code>
 * 
 * @author Tsvetelin
 *
 */
public @interface IgnoreParser
{

}
