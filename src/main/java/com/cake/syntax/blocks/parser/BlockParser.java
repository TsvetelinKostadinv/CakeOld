/*
 * 08/12/2018 14:45:05
 * BlockParser.java created by Tsvetelin
 */

package com.cake.syntax.blocks.parser;


import java.util.List;
import java.util.stream.Collectors;

import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.parsers.Parser;
import com.cake.utils.commmandSegregation.CommandsSegregator;
import com.cake.utils.commmandSegregation.Segregator;
import com.cake.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;

import javafx.util.Pair;


/**
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
        // the first should be the access modifier
        // the second token should be the name
        // the third and the last token should be "{" and "}" respectively

        return sequence.get( 0 ).getTokenType().equals( ACCESS_MODIFIER_TYPE )
                && sequence.get( 0 ).getTokenType().equals( IDENTIFIER_TYPE )
                && sequence.get( 1 ).equals( OPENING_BRACE_TOKEN )
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
            String name = tokens.get( 0 ).getToken();

            AccessModifier accessModifier = AccessModifier.valueOf( tokens.get( 0 ).getToken().toUpperCase() );

            Segregator segregator = new CommandsSegregator();

            Block parsedBlock = new Block( name , accessModifier , superblock );

            try
            {
                List< SyntaxElement > subCommands = segregator.segregateCode( tokens ).stream()
                        .map( x -> x.getKey().parse( parsedBlock , x.getValue() ).getValue() )
                        .collect( Collectors.toList() );

                parsedBlock.addSubCommands( subCommands.toArray( new SyntaxElement[0] ) );

            } catch ( MisplacedConstruct e )
            {
                throw new UnsupportedOperationException( "Cannot parse the sequence" );
            }

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
    public Pair< String , Block > parseAndAddToRuntime ( CakeRuntime runtime , Block superblock , List< Token > tokens )
    {
        Pair< String , Block > parsed = parse( superblock , tokens );

        runtime.addDecalredElement( parsed.getKey() , parsed.getValue() );
        parsed.getValue().getSubCommandsWithPaths().forEach( ( x , y ) -> runtime.addDecalredElement( x , y ) );

        return null;
    }

}
