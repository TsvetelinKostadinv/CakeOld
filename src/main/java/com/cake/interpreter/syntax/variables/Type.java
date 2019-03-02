/*
 * 06/12/2018 at 16:31:49 ÷.
 * BaseTypes.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.variables;


import com.cake.tokenization.tokens.types.BaseTokenTypesIdentificators;
import com.cake.tokenization.tokens.types.TokenTypesContainer.TokenTypeHolder;


/**
 * @author Tsvetelin
 *
 */
public enum Type
{
    NUMBER , INTEGER , REAL , BOOLEAN , STRING , OTHER_TYPE;

    public static Type getTypeFor ( TokenTypeHolder tokenType )
    {
        if ( tokenType.getIdentifier().equals( BaseTokenTypesIdentificators.NUMBER_LITERAL.getValue() ) ) 
            return NUMBER;
        if ( tokenType.getIdentifier().equals( BaseTokenTypesIdentificators.INTEGER_NUMBER_LITERAL.getValue() ) )
            return INTEGER;
        if ( tokenType.getIdentifier().equals( BaseTokenTypesIdentificators.REAL_NUMBER_LITERAL.getValue() ) ) 
            return REAL;
        if ( tokenType.getIdentifier().equals( BaseTokenTypesIdentificators.BOOLEAN_LITERAL.getValue() ) ) 
            return BOOLEAN;
        if ( tokenType.getIdentifier().equals( BaseTokenTypesIdentificators.STRING_LITERAL.getValue() ) ) 
            return STRING;

        return OTHER_TYPE;
    }

}
