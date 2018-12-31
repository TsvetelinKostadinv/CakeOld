/*
 * 14/12/2018 18:35:27
 * IdentificatorParser.java created by Tsvetelin
 */

package com.cake.syntax.parsers.checkers.identifierChecker;


import java.util.List;

import com.cake.compilation.tokens.Token;
import com.cake.syntax.parsers.checkers.Checker;


/**
 * 
 * Checks the declaration of whatever it is
 * 
 * @author Tsvetelin
 *
 */
public interface DeclarationChecker extends Checker
{

    /**
     * 
     * Checks if the supplied tokens contain an identifier at the specified position
     * and an access modifier at first place
     * 
     * @param tokens
     *            - the sequence of tokens
     * @param expectedPositionOfIdentifier
     *            - the position in question
     * @return - true if the supplied sequence has a access modifier as the first
     *         token and an identifier in the expected place and false otherwise
     */
    public static boolean isCorrectIdentifierDeclaration ( final List< Token > tokens ,
            final int expectedPositionOfIdentifier )
    {

        //@formatter:off
        if( tokens == null
                || tokens.size() == 0
                || tokens.size()<=expectedPositionOfIdentifier
                || expectedPositionOfIdentifier<1 ) return false;
        //@formatter:on

        return tokens.get( 0 ).getTokenType().equals( ACCESS_TYPE )
                && tokens.get( expectedPositionOfIdentifier ).getTokenType().equals( IDENTIFIER_TYPE );
    }

    
    /**
     * 
     * A special case of the <code>isCorrectIdentifierDeclaration(List<Token>, int)</code> for variables
     * 
     * @param tokens
     * @return
     */
    public static boolean isCorrectIdentifierDeclarationForVariable ( List< Token > tokens )
    {
        //@formatter:off
        if( tokens == null
                || tokens.size() < 2 ) return false;
        //@formatter:on

        Token equals = new Token( "=" , OPERATOR_TYPE );

        if ( tokens.contains( equals ) ) tokens = tokens.subList( 0 , tokens.indexOf( equals ) );

        // @formatter:off
        if( tokens.size() < 2 ) return false;
        // @formatter:on

        return checkWithoutType( tokens ) || checkWithType( tokens );

    }


    /**
     * @param declaration
     * @return
     */
    private static boolean checkWithoutType ( final List< Token > declaration )
    {
        return declaration.get( 0 ).getTokenType().equals( ACCESS_TYPE )
                && declaration.get( 1 ).getTokenType().equals( IDENTIFIER_TYPE );
    }


    /**
     * @param declaration
     * @return
     */
    private static boolean checkWithType ( final List< Token > declaration )
    {
        return declaration.get( 0 ).getTokenType().equals( ACCESS_TYPE )
                && declaration.get( 1 ).getTokenType().equals( IDENTIFIER_TYPE )
                && declaration.get( 2 ).getTokenType().equals( IDENTIFIER_TYPE );
    }

}