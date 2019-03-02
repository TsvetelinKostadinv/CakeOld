/*
 * 08/01/2019 11:34:44
 * WhileLoopParser.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.controlFlowStatements.loops.whileLoop.parser;


import java.util.List;

import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.blocks.parser.BlockParser;
import com.cake.interpreter.syntax.controlFlowStatements.loops.whileLoop.WhileLoop;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.interpreter.syntax.parsers.checkers.expressionChecker.ExpressionsChecker;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class WhileLoopParser extends Parser< WhileLoop > implements Checker
{

    public static final Token WHILE_KEYWORD_TOKEN = new Token( "while" , KEYWORD_TYPE );

    public static final Token OPENING_ROUND_BRACE = new Token( "(" , OPERATOR_TYPE );

    public static final Token CLOSING_ROUND_BRACE = new Token( ")" , OPERATOR_TYPE );

    public static final Token OPENING_CURLY_BRACE = new Token( "{" , OPERATOR_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        if ( !checkList( sequence , 6 ) ) return false;

        boolean keyword = sequence.get( 0 ).equals( WHILE_KEYWORD_TOKEN );
        boolean openingRoundBrace = sequence.get( 1 ).equals( OPENING_ROUND_BRACE );

        // System.out.println( "In while parser || is the keyword there: " + keyword );
        // System.out.println( "In while parser || is the opening round brace there: " +
        // openingRoundBrace );

        int closingRoundBraceIndex = sequence.indexOf( CLOSING_ROUND_BRACE );

        if ( closingRoundBraceIndex == -1 ) return false;

        // System.out.println( "In while parser || the closing is also there");

        boolean conditionCorrect = ExpressionsChecker
                .isCorrectExpression( sequence.subList( 2 , closingRoundBraceIndex ) );

        // System.out.println( "In while parser || Condition is: " + sequence.subList( 2
        // , closingRoundBraceIndex ) );
        // System.out.println( "In while parser || Condition correct: " +
        // conditionCorrect );

        boolean bodyCorrect = new BlockParser()
                .canParse( sequence.subList( closingRoundBraceIndex + 1 , sequence.size() ) );

        return keyword && openingRoundBrace && conditionCorrect && bodyCorrect;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , WhileLoop > parse ( Block superblock , List< Token > sequence )
    {
        if ( this.canParse( sequence ) )
        {
            List< Token > expression = sequence.subList( 2 , sequence.indexOf( CLOSING_ROUND_BRACE ) );

            Expression expr = new Expression( expression );

            Block body = new BlockParser()
                    .parse( superblock , sequence.subList( sequence.indexOf( OPENING_CURLY_BRACE ) , sequence.size() ) )
                    .getValue();

            WhileLoop loop = new WhileLoop( expr , body , superblock );

            String address = Block.joinNames( superblock , loop );

            return new Pair< String , WhileLoop >( address , loop );
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }
}
