/*
 * 01/01/2019 15:48:01
 * LambdaChecker.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.parsers.checkers.lambdaChecker;


import java.util.List;

import com.cake.interpreter.syntax.blocks.parser.BlockParser;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.tokenization.tokens.Token;


/**
 * @author Tsvetelin
 *
 */
public interface LambdaChecker extends Checker
{

    static final Token OPENING_BRACE = new Token( "(" , OPERATOR_TYPE );

    static final Token CLOSING_BRACE = new Token( ")" , OPERATOR_TYPE );

    static final Token COMMA_OPERATOR = new Token( "," , OPERATOR_TYPE );

    static final Token POINTER_OPERATOR = new Token( "->" , OPERATOR_TYPE );


    public static boolean correctLambda ( final List< Token > lambda )
    {

        boolean correctParamDeclr = correctParameterDeclaration( lambda );

        int pointerIndex = lambda.indexOf( POINTER_OPERATOR );

        boolean pointerCorrect = checkPointerOperator( lambda );

        List< Token > body = lambda.subList( pointerIndex + 1 , lambda.size() );

        boolean bodyCorrect = new BlockParser().canParse( body );

        return correctParamDeclr && pointerCorrect && bodyCorrect;
    }


    /**
     * @param lambda
     */
    public static boolean correctParameterDeclaration ( final List< Token > lambda )
    {
        if( lambda.size() < 2 ) return false;
        boolean startOfParameters = lambda.get( 0 ).equals( OPENING_BRACE );

        int closingBraceIndex = lambda.indexOf( CLOSING_BRACE );

        if ( !startOfParameters || closingBraceIndex == -1 ) return false;

        List< Token > parameterList = lambda.subList( 1 , closingBraceIndex );

        for ( int i = 2 ; i < closingBraceIndex-1 ; i += 3 )
        {
            if ( !parameterList.get( i ).equals( COMMA_OPERATOR ) ) { return false; }
        }

        return true;
    }


    static boolean checkPointerOperator ( final List< Token > lambda )
    {
        if ( !correctParameterDeclaration( lambda ) ) { return false; }

        int closingBraceIndex = lambda.indexOf( CLOSING_BRACE );

        return lambda.get( closingBraceIndex + 1 ).equals( POINTER_OPERATOR );

    }

}