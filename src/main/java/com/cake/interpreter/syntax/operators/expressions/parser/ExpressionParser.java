/*
 * 02/03/2019 23:24:30
 * ExpressionParser.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.expressions.parser;

import java.util.List;

import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.ParsersContainer;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.interpreter.syntax.parsers.checkers.expressionChecker.ExpressionsChecker;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;

/**
 * @author Tsvetelin
 *
 */
public class ExpressionParser extends Parser< Expression > implements Checker
{

    private static final Token EQUALS_TOKEN = new Token( "=" , OPERATOR_TYPE );

    /* (non-Javadoc)
     * @see com.cake.interpreter.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        boolean containsEquals = sequence.contains( EQUALS_TOKEN );
        boolean containsKeywords = sequence.stream().map( x -> x.getTokenType() ).filter( x -> x.equals( KEYWORD_TYPE ) ).findFirst().isPresent();
        return ExpressionsChecker.isCorrectExpression( sequence ) 
                && !containsEquals && !containsKeywords
                && ! ParsersContainer.METHOD_INVOCATION_OPERATOR_PARSER.canParse( sequence );
    }

    /* (non-Javadoc)
     * @see com.cake.interpreter.syntax.parsers.Parser#parse(com.cake.interpreter.syntax.blocks.Block, java.util.List)
     */
    @Override
    public Pair< String , Expression > parse ( Block superblock , List< Token > sequence )
    {
        if( this.canParse( sequence ))
        {
            Expression expr = new Expression( sequence );
            return new Pair< String , Expression >( Block.joinNames( superblock , expr ) , expr );
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence!" );
    }

}
