/*
 * 01/01/2019 18:37:57
 * ReassignmentOperationParser.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.reassignmentOp.parser;


import java.util.List;

import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.operators.reassignmentOp.ReassignmentOperator;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.interpreter.syntax.parsers.checkers.expressionChecker.ExpressionsChecker;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class ReassignmentOperationParser extends Parser< ReassignmentOperator > implements Checker
{

    private static final Token EQUALS = new Token( "=" , OPERATOR_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        if ( !checkList( sequence , 3 ) ) return false;

        boolean assigneeCorrect = sequence.get( 0 ).getTokenType().equals( IDENTIFIER_TYPE );

        boolean equalsInPlace = sequence.get( 1 ).equals( EQUALS );

        List< Token > afterEquals = sequence.subList( 2 , sequence.size() );

        boolean exprCorrect = ExpressionsChecker.isCorrectExpression( afterEquals );

        return assigneeCorrect && equalsInPlace && exprCorrect;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , ReassignmentOperator > parse ( Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {

            String varName = tokens.get( 0 ).getToken();

            Expression expr = new Expression( tokens.subList( 2 , tokens.size() ) );

            ReassignmentOperator op = new ReassignmentOperator( varName , expr );

            String address = Block.joinNames( superblock , op );

            return new Pair< String , ReassignmentOperator >( address , op );

        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }

}
