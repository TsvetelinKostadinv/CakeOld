/*
 * 24/02/2019 22:15:35
 * BlockParser.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.blocks.parser;


import java.util.List;

import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.parsers.Parser;
import com.cake.interpreter.syntax.parsers.checkers.Checker;
import com.cake.interpreter.utils.commmandSegregation.CommandsSegregator;
import com.cake.interpreter.utils.commmandSegregation.Segregator;
import com.cake.interpreter.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;
import com.cake.tokenization.tokens.Token;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class BlockParser extends Parser< Block > implements Checker
{

    /**
     * 
     */
    private static final Segregator COMMAND_SEGREGATOR = new CommandsSegregator();

    public static final Token OPENING_CURLY_BRACE = new Token( "{" , OPERATOR_TYPE );

    public static final Token CLOSING_CURLY_BRACE = new Token( "}" , OPERATOR_TYPE );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        if( ! checkList( sequence , 2 )) return false;
        
        return sequence.get( 0 ).equals( OPENING_CURLY_BRACE )
                && sequence.get( sequence.size() - 1 ).equals( CLOSING_CURLY_BRACE );
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , Block > parse ( Block superblock , List< Token > sequence )
    {
        sequence = sequence.subList( 1 , sequence.size()-1 );

        List< SyntaxElement > subCommands = null;
        try
        {
            subCommands = COMMAND_SEGREGATOR.segregateCodeIntoSyntaxElements( superblock , sequence );
        } catch ( MisplacedConstruct e )
        {
            System.out.println( "Cannot Segregate" );
            e.printStackTrace();
        }

        String name = Block.joinNames( superblock , null );
        Block res = new Block( name , AccessModifier.PRIVATE , superblock );
        
        res.addSubCommands( subCommands.toArray( new SyntaxElement[0] ) );
        
        return new Pair< String , Block >( name , res );
    }

}
