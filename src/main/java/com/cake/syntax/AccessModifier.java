/*
 * 05/11/2018 at 20:34:14 ÷.
 * AccessModifiers.java created by Tsvetelin
 */

package com.cake.syntax;


/**
 * 
 * The standard access modifiers
 * 
 * @author Tsvetelin
 *
 */
public enum AccessModifier
{
    /**
     * Visible from the whole project and other projects as well
     */
    GLOBAL ,
    /**
     * Visible from the whole project
     */
    PUBLIC ,
    /**
     * Visible only from the current group
     */
    GROUPSCOPED ,
    /**
     * Visible only from the current scope
     */
    LOCAL , 
    /**
     * For class fields only not visible from outside the class
     */
    PRIVATE
}
