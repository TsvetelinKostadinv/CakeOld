/*
 * 01/01/2019 19:00:50
 * ReturnOperatorParser.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.returnOp.parser;


import java.util.List;

import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.operators.returnOp.ReturnOperator;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.interpreter.syntax.parsers.checkers.expressionChecker.ExpressionsChecker;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class ReturnOperatorParser extends Parser< ReturnOperator > implements Checker
{

    private static final Token RETURN_OPERATOR = new Token( "return" , KEYWORD_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        if ( !checkList( sequence , 2 ) ) return false;
        
        boolean returnInPlace = sequence.get( 0 ).equals( RETURN_OPERATOR );
        boolean exprCorrect = ExpressionsChecker.isCorrectExpression( sequence.subList( 1 , sequence.size() ) );

        return returnInPlace && exprCorrect;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , ReturnOperator > parse ( Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {
            Expression formula = new Expression( tokens.subList( 1 , tokens.size() ) );
            
            ReturnOperator op = new ReturnOperator( formula );
             
//            System.out.println( "In return op parser || Got op: " + op.toString() );
            String address = Block.joinNames( superblock , op );
            
            return new Pair< String , ReturnOperator >( address , op );
            
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }
    
}
