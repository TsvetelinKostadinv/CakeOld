/*
 * 08/12/2018 16:29:26
 * CommandsSegregator.java created by Tsvetelin
 */

package com.cake.utils.commmandSegregation;


import java.util.ArrayList;
import java.util.List;

import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;
import com.cake.syntax.parsers.Parser;
import com.cake.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;

import javafx.util.Pair;


/**
 * @author Tsvetelin
 *
 */
public class CommandsSegregator implements Segregator
{

    private final TokenTypesContainer typesCont = TokenTypesContainer.INSTANCE;

    private final TokenTypeHolder accessType = typesCont
            .getTypeForIdentifier( BaseTokenTypesIdentificators.ACCESS_MODIFIER.getValue() );

    private final TokenTypeHolder keywordType = typesCont
            .getTypeForIdentifier( BaseTokenTypesIdentificators.KEYWORD.getValue() );

    private final String useKeyword = "use";

    private final TokenTypeHolder identifierType = typesCont
            .getTypeForIdentifier( BaseTokenTypesIdentificators.IDENTIFIER.getValue() );


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.utils.commmandSegregation.Segregator#segregateCode(java.util.List)
     */
    @Override
    public List< Pair< Parser< ? > , List< Token > > > segregateCode ( List< Token > sequence )
            throws MisplacedConstruct
    {

        if ( sequence == null || sequence.size() == 0 ) return null;

        List< Pair< Parser< ? > , List< Token > > > result = new ArrayList<>();

        while ( sequence.size() > 0 )
        {
            List< Token > currentCommand = new ArrayList<>();
            
            Token current = sequence.get( 0 );
            
            currentCommand.add( current );
            
            if ( current.getTokenType().equals( keywordType ) )
            {
                if ( current.getToken().equals( useKeyword ) )
                {
                    // TODO implement external usage
                    throw new UnsupportedOperationException( "External usage is not yet supported" );
                } else
                {
                    throw new MisplacedConstruct( current.getToken() + " cannot be used in this context" );
                }
            } else if ( current.getTokenType().equals( accessType ) )
            {
                Token second = sequence.get( 1 );
                if ( second.getTokenType().equals( identifierType ) )
                {
                    // the second is an identifier
                    //TODO implement command segregation
                    
                }
            }
        }

        throw new UnsupportedOperationException( "Not implemented yet" );
    }

}
