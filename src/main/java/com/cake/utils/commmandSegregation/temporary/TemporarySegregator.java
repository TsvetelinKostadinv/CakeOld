/*
 * 12/12/2018 16:38:24
 * TemporarySegregator.java created by Tsvetelin
 */

package com.cake.utils.commmandSegregation.temporary;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;
import com.cake.compilation.tokens.types.TokenTypesContainer.TokenTypeHolder;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.parsers.ParsersContainer;
import com.cake.utils.commmandSegregation.Segregator;
import com.cake.utils.commmandSegregation.segregatorExceptions.MisplacedConstruct;


/**
 * @author Tsvetelin
 * @deprecated this is just temporary
 */
@Deprecated
public class TemporarySegregator implements Segregator
{

    private final TokenTypesContainer typesCont = TokenTypesContainer.INSTANCE;

    private final TokenTypeHolder accessType = typesCont
            .getTypeForIdentifier( BaseTokenTypesIdentificators.ACCESS_MODIFIER.getValue() );
    
    private final ParsersContainer parsCont = ParsersContainer.INSTANCE;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.utils.commmandSegregation.Segregator#segregateCodeWithParsers(java.
     * util.List)
     */
    @Override
    public Map< Parser< ? > , List< Token > > segregateCodeWithParsers ( List< Token > sequence )
            throws MisplacedConstruct
    {
        Map< Parser< ? > , List< Token > > result = new LinkedHashMap<>();

        List< Token > temp = new ArrayList<>();

        for ( int i = 0 ; i < sequence.size() ; i++ )
        {
            if ( sequence.get( i ).getTokenType().equals( accessType ) )
            {
                result.put( parsCont.getParserFor( temp ).get( 0 ) , temp );
            }
        }

        return result;
    }

}
