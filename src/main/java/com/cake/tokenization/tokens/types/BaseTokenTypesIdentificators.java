/*
 * 25/10/2018 at 16:11:23
 * BaseTypes.java created by Tsvetelin
 */

package com.cake.tokenization.tokens.types;


/**
 * 
 * The identificators of the basic token types
 * 
 * @author Tsvetelin
 *
 */
public enum BaseTokenTypesIdentificators
{
  //@formatter:off
    EMPTY ( "" ) ,
    OPERATOR ( "OPERATOR") ,
    IDENTIFIER ( "IDENTIFIER") ,
    STRING_LITERAL ( "STRING_LITERAL") ,
    NUMBER_LITERAL( "NUMBER_LITERAL") ,
    INTEGER_NUMBER_LITERAL( "INTEGER_LITERAL") ,
    REAL_NUMBER_LITERAL( "REAL_LITERAL"), 
    BOOLEAN_LITERAL( "BOOLEAN_LITERAL" ),
    ACCESS_MODIFIER( "ACCESS_MODIFIER" ),
    KEYWORD( "KEYWORD" );
    //@formatter:on

    private final String value;


    /**
     * Private constructor so no one else builds a type
     */
    private BaseTokenTypesIdentificators ( String holder )
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
