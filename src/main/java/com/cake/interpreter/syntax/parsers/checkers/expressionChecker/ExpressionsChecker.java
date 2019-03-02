/*
 * 14/12/2018 20:41:23
 * ExpressionsChecker.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.parsers.checkers.expressionChecker;


import java.util.List;

import org.mariuszgromada.math.mxparser.Expression;

import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.tokenization.tokens.Token;


/**
 * @author Tsvetelin
 *
 */
public interface ExpressionsChecker extends Checker
{

    /**
     * 
     * Checks the syntax of the expression
     * 
     * @param tokens
     */
    public static boolean isCorrectExpression ( List< Token > tokens )
    {
        // @formatter:off
        if( tokens == null
                || tokens.size() < 1 ) return false;
        // @formatter:on

        Token equals = new Token( "=" , OPERATOR_TYPE );

        if ( tokens.contains( equals ) ) tokens = tokens.subList( tokens.indexOf( equals ) + 1 , tokens.size() );
        if ( tokens.indexOf( equals ) != tokens.lastIndexOf( equals ) ) return false;

        if ( tokens.size() == 1 )
        {
            return tokens.get( 0 ).getTokenType().equals( IDENTIFIER_TYPE )
                    || tokens.get( 0 ).getTokenType().equals( STRING_LITERAL_TYPE )
                    || tokens.get( 0 ).getTokenType().equals( NUMBER_LITERAL_TYPE )
                    || tokens.get( 0 ).getTokenType().equals( INTEGER_NUMBER_LITERAL_TYPE )
                    || tokens.get( 0 ).getTokenType().equals( REAL_NUMBER_LITERAL_TYPE )
                    || tokens.get( 0 ).getTokenType().equals( BOOLEAN_LITERAL_TYPE );
        } else
        {
            StringBuilder expr = new StringBuilder();

            for ( Token token : tokens )
            {
                if ( token.getTokenType().equals( IDENTIFIER_TYPE ) )
                {
                    expr.append( 'a' );

                } else
                {
                    expr.append( token.getToken() );
                }

            }
            
            return new Expression( expr.toString() ).checkLexSyntax();
        }

    }

}