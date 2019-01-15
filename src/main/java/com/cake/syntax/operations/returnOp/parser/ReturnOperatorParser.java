/*
 * 01/01/2019 19:00:50
 * ReturnOperatorParser.java created by Tsvetelin
 */

package com.cake.syntax.operations.returnOp.parser;


import java.util.List;

import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.expressions.Expression;
import com.cake.syntax.operations.returnOp.ReturnOperator;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.parsers.checkers.expressionsChecker.ExpressionsChecker;
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


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.syntax.parsers.Parser#parseWithRuntime(com.cake.running.runtime.
     * CakeRuntime, com.cake.syntax.blocks.Block, java.util.List)
     */
    @Override
    public Pair< String , ReturnOperator > parseWithRuntime ( CakeRuntime runtime , Block superblock ,
            List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {
            Expression formula = new Expression( tokens.subList( 1 , tokens.size() ) );
            
            ReturnOperator op = new ReturnOperator( formula );
            
            String address = Block.joinNames( superblock , op );
            
            return new Pair< String , ReturnOperator >( address , op );
            
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }
    
}
