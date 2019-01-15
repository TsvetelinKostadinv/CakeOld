/*
 * 11/01/2019 17:25:41
 * TempTokenizer.java created by Tsvetelin
 */

package com.cake.compilation.tokenizer.tokenizers.temporary;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import com.cake.compilation.tokenizer.Tokenizator;
import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.patterns.TokenPattern;
import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;


/**
 * @author Tsvetelin
 *
 */
public class TempTokenizer implements Tokenizator< String >
{

    /* (non-Javadoc)
     * @see com.cake.compilation.tokenizer.Tokenizator#tokenize(java.lang.Object)
     */
    @Override
    public List< Token > tokenize ( String source )
    {
        source = source.replace( "\r\n" , "" );
        String[] tokens = source.split( "\\s+" );
        List< Token > sequence = new LinkedList<>();
        for( String s : tokens )
        {
            for( TokenPattern p : patterns )
            {
                Matcher matcher = p.getPattern().matcher( s );
                
                if ( matcher.find() )
                {
                    String token = matcher.group().trim();
                    s = matcher.replaceFirst( "" );
//                    System.out.println( "Source: " + source );
//                    System.out.println( "The token is:" + token );
                    if ( p.forType() == types
                            .getTypeForIdentifier( BaseTokenTypesIdentificators.STRING_LITERAL.getValue() ) )
                    {
                        sequence.add( processedStringToken( token ) ) ;
                    } else 
//                        if ( pattern.forType() == types
//                            .getTypeForIdentifier( BaseTokenTypesIdentificators.NUMBER_LITERAL.getValue() ) )
//                    {
//                        return exactTypeOfNumberToken( token );
//                    }
                    {
                        sequence.add( new Token( token , p.forType() ) );
                    }
                }
            }
        }
        
        sequence = sequence.stream().filter( x -> !x.getToken().isEmpty() ).collect( Collectors.toList() );
        
        return sequence;
    }
    

    /**
     * @param token - the token to be processed
     * @return a new token of the type String literal
     */
    private Token processedStringToken ( String token )
    {
        return new Token( token.substring( 1 , token.length() - 1 ) ,
                types.getTypeForIdentifier( BaseTokenTypesIdentificators.STRING_LITERAL.getValue() ) );
    }

}
