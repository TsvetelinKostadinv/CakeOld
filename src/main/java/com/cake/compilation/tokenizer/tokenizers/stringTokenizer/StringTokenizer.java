/*
 * 27/10/2018 at 18:11:37
 * Tokenizer.java created by Tsvetelin
 */

package com.cake.compilation.tokenizer.tokenizers.stringTokenizer;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import com.cake.compilation.tokenizer.Tokenizator;
import com.cake.compilation.tokens.Token;
import com.cake.compilation.tokens.patterns.TokenPattern;
import com.cake.compilation.tokens.patterns.container.TokenPatternContainer;
import com.cake.compilation.tokens.types.BaseTokenTypesIdentificators;
import com.cake.compilation.tokens.types.TokenTypesContainer;


/**
 * 
 * A tokenizer for Strings
 * 
 * @author Tsvetelin
 *
 */
public class StringTokenizer implements Iterator< Token > , Tokenizator< String >
{



    /**
     * The remaining source to be tokenized
     */
    private String source = "";


    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext ()
    {

        return !source.isEmpty();
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#next()
     */
    @Override
    public Token next ()
    {
        source = source.trim();

        if ( source.isEmpty() ) new Token( "" , types.getTypeForIdentifier( BaseTokenTypesIdentificators.EMPTY.getValue() ) );
        
//        System.out.println( "Searching for:" + source );
        
        for ( TokenPattern pattern : patterns )
        {
            Matcher matcher = pattern.getPattern().matcher( source );
            
//            System.out.println( "Trying with pattern: " + pattern.forType() );
            
            if ( pattern.equals( TokenPattern.EMPTY_PATTERN ) ) continue;

            if ( matcher.find() )
            {
                String token = matcher.group().trim();
                source = matcher.replaceFirst( "" );
//                System.out.println( "Source: " + source );
//                System.out.println( "The token is:" + token );
                if ( pattern.forType() == types
                        .getTypeForIdentifier( BaseTokenTypesIdentificators.STRING_LITERAL.getValue() ) )
                {
                    return processedStringToken( token );
                } else 
//                    if ( pattern.forType() == types
//                        .getTypeForIdentifier( BaseTokenTypesIdentificators.NUMBER_LITERAL.getValue() ) )
//                {
//                    return exactTypeOfNumberToken( token );
//                }
                {
                    return new Token( token , pattern.forType() );
                }
            }
        }
        throw new IllegalArgumentException( "Illegal token: " + source );
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


//    /**
//     * 
//     * Determines the exact type of number that this token represents()integer or real number
//     * 
//     * @param token - the token to be processed
//     * @return a token with the exact representation of the number or null if something went wrong
//     */
//    private Token exactTypeOfNumberToken ( String token )
//    {
//        if ( token.matches( patterns
//                .getTokenPatternForToken( BaseTokenTypesIdentificators.INTEGER_NUMBER_LITERAL.getValue() )
//                .getPattern().pattern() ) )
//        {
//            return new Token( token , types
//                    .getTypeForIdentifier( BaseTokenTypesIdentificators.INTEGER_NUMBER_LITERAL.getValue() ) );
//        } else if ( token.matches( patterns
//                .getTokenPatternForToken( BaseTokenTypesIdentificators.REAL_NUMBER_LITERAL.getValue() )
//                .getPattern().pattern() ) )
//        {
//            return new Token( token , types
//                    .getTypeForIdentifier( BaseTokenTypesIdentificators.REAL_NUMBER_LITERAL.getValue() ) );
//        }
//        
//        return null;
//    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.tokenizer.Tokenizator#tokenize(java.lang.String)
     */
    @Override
    public List< Token > tokenize ( String source )
    {

        List< Token > tokensOfSource = new LinkedList< Token >();

        this.source = source;

        while ( this.hasNext() )
        {
//            try
//            {
//                Thread.sleep( 1000 );
//            } catch ( InterruptedException e )
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            System.out.println( "After interation: " + tokensOfSource );
            tokensOfSource.add( this.next() );
        }
        //System.out.println( "Tokens of source: " + tokensOfSource );
        return tokensOfSource;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object arg0 )
    {

        return arg0 instanceof StringTokenizer && ( (StringTokenizer) arg0 ).source.equals( this.source );
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {

        return "This is a Tokenizer with remaining source: " + source;
    }
}
