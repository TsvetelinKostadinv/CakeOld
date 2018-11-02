/*
 * 25/10/2018 at 16:11:23
 * BaseTypes.java created by Tsvetelin
 */

package com.cake.tokens.types;

/**
 * 
 * The identificators of the basic token types
 * 
 * @author Tsvetelin
 *
 */
public enum BaseTypesIdentificators
{
    EMPTY ( "" ) ,
    OPERATOR ( "OPERATOR") ,
    IDENTIFIER ( "IDENTIFIER") ,
    STRING_LITERAL ( "STRING_LITERAL") ,
    NUMBER_LITERAL( "NUMBER_LITERAL") ,
    INTEGER_NUMBER_LITERAL( "INTEGER_LITERAL") ,
    REAL_NUMBER_LITERAL( "REAL_LITERAL") ;
    
    
    private String value = null;
    
    /**
     * Private constructor so no one else builds a type
     */
    private BaseTypesIdentificators ( String holder )
    {
        this.value = holder;
    }
    
    /**
     * @return the value
     */
    public String getValue ()
    {

        return value;
    }
    
}