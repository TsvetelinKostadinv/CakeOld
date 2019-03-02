/*
 * 08/12/2018 16:29:26
 * CommandsSegregator.java created by Tsvetelin
 */

package com.cake.interpreter.utils.commmandSegregation;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.declaration.parser.VariableDeclarationParser;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.ParsersContainer;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.interpreter.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;
import com.cake.tokenization.tokens.Token;


/**
 * 
 * Separates commands
 * 
 * @author Tsvetelin
 *
 */
public class CommandsSegregator implements Segregator , Checker
{

    public static final ParsersContainer PARSERS_CONTAINER = ParsersContainer.INSTANCE;
    private static final Token EQUALS_TOKEN = new Token( "=" , OPERATOR_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.utils.commmandSegregation.Segregator#segregateCodeIntoSyntaxElements
     * (com.cake.syntax.blocks.Block, java.util.List)
     */
    @Override
    public List< SyntaxElement > segregateCodeIntoSyntaxElements ( Block superBlockOfSequence , List< Token > sequence )
            throws MisplacedConstruct
    {
        List< SyntaxElement > res = new LinkedList<>();
        
        
        List< Token > currentCommand = new ArrayList<>();
        for ( int i = 0 ; i < sequence.size() ; i++ )
        {
            Token t = sequence.get( i );
            currentCommand.add( t );
            List< Parser< ? > > parsers = PARSERS_CONTAINER.getParserFor( currentCommand );
            if ( parsers.size() == 1 )
            {
                if ( parsers.get( 0 ) instanceof VariableDeclarationParser 
                        && (
                                sequence.get( i+1 ).equals( EQUALS_TOKEN )
                                ||
                                sequence.get( i+1 ).getTokenType().equals( IDENTIFIER_TYPE )
                                ) )
                {
                    continue;
                }else {
                    res.add( parsers.get( 0 ).parse( superBlockOfSequence , currentCommand ).getValue() );
                    currentCommand.clear();
                }
            }
        }
        return res;
    }
    
    
}
