/*
 * 01/01/2019 18:37:57
 * ReassignmentOperationParser.java created by Tsvetelin
 */

package com.cake.syntax.operations.reassignmentOp.parser;


import java.util.List;

import com.cake.compilation.tokenizer.tokenizers.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.expressions.Expression;
import com.cake.syntax.operations.reassignmentOp.ReassignmentOperator;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.parsers.checkers.expressionsChecker.ExpressionsChecker;
import com.cake.syntax.variables.Variable;

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
        if( sequence.size() < 3 ) return false;
        
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

            ReassignmentOperator op = new ReassignmentOperator( new Variable( varName , null , AccessModifier.LOCAL ) ,
                    expr );

            String address = Block.joinNames( superblock , op );

            return new Pair< String , ReassignmentOperator >( address , op );

        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.syntax.parsers.Parser#parseWithRuntime(com.cake.running.runtime.
     * CakeRuntime, com.cake.syntax.blocks.Block, java.util.List)
     */
    @Override
    public Pair< String , ReassignmentOperator > parseWithRuntime ( CakeRuntime runtime , Block superblock ,
            List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {
            String varName = tokens.get( 0 ).getToken();

            Expression expr = new Expression( tokens.subList( 2 , tokens.size() ) );

            ReassignmentOperator op = new ReassignmentOperator( (Variable) runtime.getElement( varName ) , expr );

            String address = Block.joinNames( superblock , op );

            return new Pair< String , ReassignmentOperator >( address , op );

        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }
    
    public static void main ( String [] args )
    {
        String code = "i = 3*5*2";
        
        ReassignmentOperationParser p = new ReassignmentOperationParser();
        
        System.out.println( p.canParse( new StringTokenizer().tokenize( code ) ) );
        
    }
    
}
