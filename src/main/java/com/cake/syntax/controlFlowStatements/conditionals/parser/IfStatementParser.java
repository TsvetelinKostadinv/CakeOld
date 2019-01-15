/*
 * 06/01/2019 12:32:44
 * IfStatementParser.java created by Tsvetelin
 */

package com.cake.syntax.controlFlowStatements.conditionals.parser;


import java.util.List;

import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.blocks.parser.BlockParser;
import com.cake.syntax.controlFlowStatements.conditionals.IfStatement;
import com.cake.syntax.expressions.Expression;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.checkers.Checker;
import com.cake.syntax.parsers.checkers.expressionsChecker.ExpressionsChecker;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class IfStatementParser extends Parser< IfStatement > implements Checker
{

    /**
     * 
     */
    public static final Token OPENING_CURLY_BRACE = new Token( "{" , OPERATOR_TYPE );

    public static final Token IF_KEYWORD_TOKEN = new Token( "if" , KEYWORD_TYPE );

    public static final Token OPENING_ROUND_BRACE_TOKEN = new Token( "(" , OPERATOR_TYPE );

    public static final Token CLOSING_ROUND_BRACE_TOKEN = new Token( ")" , OPERATOR_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        if ( !checkList( sequence , 6 ) ) return false;

        boolean ifKeyword = sequence.get( 0 ).equals( IF_KEYWORD_TOKEN );
        boolean openingRoundBrace = sequence.get( 1 ).equals( OPENING_ROUND_BRACE_TOKEN );

        if ( ! ( ifKeyword && openingRoundBrace ) ) return false;

        int closingRoundBraceIndex = sequence.indexOf( CLOSING_ROUND_BRACE_TOKEN );

        if ( closingRoundBraceIndex == -1 ) return false;

        boolean expressionCorrect = ExpressionsChecker.isCorrectExpression(
                sequence.subList( sequence.indexOf( OPENING_ROUND_BRACE_TOKEN ) + 1 , closingRoundBraceIndex ) );

        List< Token > body = sequence.subList( sequence.indexOf( OPENING_CURLY_BRACE ) , sequence.size() );

        boolean bodyCorrect = new BlockParser().canParse( body );

        return ifKeyword && openingRoundBrace && expressionCorrect && bodyCorrect;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , IfStatement > parse ( Block superblock , List< Token > sequence )
    {
        if ( this.canParse( sequence ) )
        {
            Expression condition = new Expression(
                    sequence.subList( 2 , sequence.indexOf( CLOSING_ROUND_BRACE_TOKEN ) ) );

            Block body = new BlockParser()
                    .parse( superblock , sequence.subList( sequence.indexOf( OPENING_CURLY_BRACE ) , sequence.size() ) )
                    .getValue();

            IfStatement op = new IfStatement( condition , body , superblock );

            String address = Block.joinNames( superblock , op );

            return new Pair< String , IfStatement >( address , op );
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
    public Pair< String , IfStatement > parseWithRuntime ( CakeRuntime runtime , Block superblock ,
            List< Token > sequence )
    {
        return this.parse( superblock , sequence );
    }

}
