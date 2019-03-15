/*
 * 05/01/2019 13:07:45
 * MethodInvokationParser.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.methodInvocation.parser;


import java.util.ArrayList;
import java.util.List;

import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.operators.methodInvocation.MethodInvocationOperator;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class MethodInvokationOperatorParser extends Parser< MethodInvocationOperator > implements Checker
{

    private static final Token OPENING_BRACE = new Token( "(" , OPERATOR_TYPE );

    private static final Token CLOSING_BRACE = new Token( ")" , OPERATOR_TYPE );

    private static final Token COMMA = new Token( "," , OPERATOR_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        if ( !checkList( sequence , 3 ) ) return false;

        boolean first = sequence.get( 0 ).getTokenType().equals( IDENTIFIER_TYPE );

        boolean openingBrace = sequence.get( 1 ).equals( OPENING_BRACE );
        boolean closingBrace = sequence.get( sequence.size() - 1 ).equals( CLOSING_BRACE );

        if ( ! ( first && openingBrace && closingBrace ) ) return false;
        
        return true;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , MethodInvocationOperator > parse ( Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {
//            System.out.println( this.getClass() + " || Parsing: " + tokens );
            String methodName = tokens.get( 0 ).getToken();

            List< Token > paramList = tokens.subList( 2 , tokens.size()-1 );
            
//            System.out.println( this.getClass() + " || Param List: " + paramList );
            
            List< Expression > expressions = new ArrayList<>();
            
            List< Token > currentExpression = new ArrayList<>();
            for( Token t : paramList )
            {
                if(t.equals( COMMA ))
                {
                    expressions.add( new Expression( currentExpression ) );
                    currentExpression.clear();
                }else {
                    currentExpression.add( t );
                }
            }
            
            expressions.add( new Expression( currentExpression ) );
            
//            System.out.println( this.getClass() + " || Expressions: " + expressions );
            
            MethodInvocationOperator op = new MethodInvocationOperator( methodName , expressions.toArray( new Expression[0] ) );

            String address = Block.joinNames( superblock , op );

            return new Pair< String , MethodInvocationOperator >( address , op );

        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }

}
