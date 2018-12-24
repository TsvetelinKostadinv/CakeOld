/*
 * 14/12/2018 18:47:47
 * Checker.java created by Tsvetelin
 */

package com.cake.syntax.parsers.checkers;


import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;


/**
 * 
 * All interfaces extending this one have access to it's fields.
 * These interfaces check something of importance to the parsers. 
 * 
 * @author Tsvetelin
 *
 */
public interface Checker
{

    final static TokenTypesContainer TYPES_CONT = TokenTypesContainer.INSTANCE;

    final static TokenTypesContainer.TokenTypeHolder ACCESS_TYPE = TYPES_CONT
            .getTypeForIdentifier( BaseTokenTypesIdentificators.ACCESS_MODIFIER.getValue() );
    
    final static TokenTypesContainer.TokenTypeHolder OPERATOR_TYPE = TYPES_CONT
            .getTypeForIdentifier( BaseTokenTypesIdentificators.OPERATOR.getValue() );
    
    final static TokenTypesContainer.TokenTypeHolder KEYWORD_TYPE = TYPES_CONT
            .getTypeForIdentifier( BaseTokenTypesIdentificators.KEYWORD.getValue() );
    
    final static TokenTypesContainer.TokenTypeHolder IDENTIFIER_TYPE = TYPES_CONT
            .getTypeForIdentifier( BaseTokenTypesIdentificators.IDENTIFIER.getValue() );
    
    final static TokenTypesContainer.TokenTypeHolder STRING_LITERAL_TYPE = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.STRING_LITERAL.getValue() );

    final static TokenTypesContainer.TokenTypeHolder NUMBER_LITERAL_TYPE = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.NUMBER_LITERAL.getValue() );

    final static TokenTypesContainer.TokenTypeHolder INTEGER_NUMBER_LITERAL_TYPE = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.INTEGER_NUMBER_LITERAL.getValue() );

    final static TokenTypesContainer.TokenTypeHolder REAL_NUMBER_LITERAL_TYPE = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.REAL_NUMBER_LITERAL.getValue() );

    final static TokenTypesContainer.TokenTypeHolder BOOLEAN_LITERAL_TYPE = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.BOOLEAN_LITERAL.getValue() );

}
