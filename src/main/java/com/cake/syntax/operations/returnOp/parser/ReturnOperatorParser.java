/*
 * 01/01/2019 19:00:50
 * ReturnOperatorParser.java created by Tsvetelin
 */

package com.cake.syntax.operations.returnOp.parser;


import java.util.List;

import com.cake.compilation.tokenizer.tokenizers.StringTokenizer;
import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.operations.returnOp.ReturnOperator;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.parsers.checkers.expressionsChecker.ExpressionsChecker;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;
import com.cake.utils.expressions.evaluation.ExpressionEvaluator;

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
        if ( sequence == null || sequence.size() < 2 ) return false;
        
        System.out.println( sequence );
        
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
            Value returnVal = ExpressionEvaluator.evaluate( null , tokens.subList( 1 , tokens.size() ) );

            Variable returnVar = new Variable( null , returnVal , AccessModifier.PRIVATE );
            
            ReturnOperator op = new ReturnOperator( returnVar );
            
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
            Value returnVal = ExpressionEvaluator.evaluate( runtime , tokens.subList( 1 , tokens.size() ) );

            Variable returnVar = new Variable( null , returnVal , AccessModifier.PRIVATE );
            
            ReturnOperator op = new ReturnOperator( returnVar );
            
            String address = Block.joinNames( superblock , op );
            
            return new Pair< String , ReturnOperator >( address , op );
            
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }
    
}
