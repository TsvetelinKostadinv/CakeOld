/*
 * 08/12/2018 14:45:05
 * BlockParser.java created by Tsvetelin
 */

package com.cake.syntax.blocks.parser;


import java.util.Arrays;
import java.util.List;
import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.parsers.Parser;
import com.cake.utils.commmandSegregation.temporary.TemporarySegregator;

import javafx.util.Pair;


/**
 * 
 * This parser parses named blocks of code
 * 
 * @author Tsvetelin
 *
 */
public class BlockParser extends Parser< Block >
{

    public static final String OPENING_BRACE = "{";

    public static final String CLOSING_BRACE = "}";

    public static final Token OPENING_BRACE_TOKEN = new Token( OPENING_BRACE ,
            TokenTypesContainer.INSTANCE.getTypeForIdentifier( BaseTokenTypesIdentificators.OPERATOR.getValue() ) );

    public static final Token CLOSING_BRACE_TOKEN = new Token( CLOSING_BRACE ,
            TokenTypesContainer.INSTANCE.getTypeForIdentifier( BaseTokenTypesIdentificators.OPERATOR.getValue() ) );

    public static final TokenTypeHolder ACCESS_MODIFIER_TYPE = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.ACCESS_MODIFIER.getValue() );

    public static final TokenTypeHolder IDENTIFIER_TYPE = TokenTypesContainer.INSTANCE
            .getTypeForIdentifier( BaseTokenTypesIdentificators.IDENTIFIER.getValue() );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#canParse(java.util.List)
     */
    @Override
    public boolean canParse ( List< Token > sequence )
    {
        // the first and the last token should be "{" and "}" respectively

        return checkList( sequence , 2 )
                && sequence.get( 0 ).equals( OPENING_BRACE_TOKEN )
                && sequence.get( sequence.size() - 1 ).equals( CLOSING_BRACE_TOKEN );
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.parsers.Parser#parse(com.cake.syntax.blocks.Block,
     * java.util.List)
     */
    @Override
    public Pair< String , Block > parse ( Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {

            TemporarySegregator segregator = new TemporarySegregator();

            Block parsedBlock = new Block( null , AccessModifier.LOCAL , superblock );

            List< SyntaxElement > subCommands = segregator.getElements( superblock , tokens );

            parsedBlock.addSubCommands( subCommands.toArray( new SyntaxElement[0] ) );

            return new Pair< String , Block >( Block.joinNames( superblock , parsedBlock ) , parsedBlock );
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.syntax.parsers.Parser#parseAndAddToRuntime(com.cake.running.runtime.
     * CakeRuntime, com.cake.syntax.blocks.Block, java.util.List)
     */
    @Override
    public Pair< String , Block > parseWithRuntime ( CakeRuntime runtime , Block superblock , List< Token > tokens )
    {
        if ( this.canParse( tokens ) )
        {

            TemporarySegregator segregator = new TemporarySegregator();

            Block parsedBlock = new Block( null , AccessModifier.LOCAL , superblock );

            List< SyntaxElement > subCommands = tokens.size() == 2 ? Arrays.asList( new SyntaxElement[0] ) :segregator.getElements( runtime , superblock , tokens );
            
            
            parsedBlock.addSubCommands( subCommands.toArray( new SyntaxElement[0] ) );
            
            
            return new Pair< String , Block >( Block.joinNames( superblock , parsedBlock ) , parsedBlock );
        }
        throw new UnsupportedOperationException( "Cannot parse the sequence" );
    }

}
