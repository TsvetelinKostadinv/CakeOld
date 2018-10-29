/*
 * 27/10/2018 at 18:11:37
 * Tokenizer.java created by Tsvetelin
 */

package com.cake.tokenizer;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import com.cake.tokens.Token;
import com.cake.tokens.patterns.TokenPattern;
import com.cake.tokens.patterns.container.TokenPatternContainer;
import com.cake.tokens.types.BaseTypesIdentificators;
import com.cake.tokens.types.TokenTypesContainer;


/**
 * 
 * A tokenizer for Strings 
 * 
 * @author Tsvetelin
 *
 */
public class StringTokenizer implements Iterator< Token >, Tokenizator< String >
{
    /**
     * Holder of the patterns
     */
    private final TokenPatternContainer patterns = TokenPatternContainer.INSTANCE;
    /**
     * Holder of the token types
     */
    private final TokenTypesContainer types = TokenTypesContainer.INSTANCE;
    
    /**
     * The remaining source to be tokenized
     */
    private String source = "";
    
    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext ()
    {
        return !source.isEmpty();
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    @Override
    public Token next ()
    {
        source = source.trim();
        
        if (source.isEmpty()) {
            new Token("", types.getTypeForIdentifier( BaseTypesIdentificators.EMPTY.getValue() ));
        }
        
        for (TokenPattern pattern : patterns) {
            Matcher matcher = pattern.getPattern().matcher(source);
            
            if(pattern.equals( TokenPattern.EMPTY_PATTERN )) continue;
            
            if (matcher.find()) {
                String token = matcher.group().trim();
                source = matcher.replaceFirst("");
                
                if (pattern.forType() == types.getTypeForIdentifier( BaseTypesIdentificators.STRING_LITERAL.getValue() )) {
                    return new Token(token.substring(1, token.length() - 1), types.getTypeForIdentifier( BaseTypesIdentificators.STRING_LITERAL.getValue() ));
                }
                
                else {
                    return new Token(token, pattern.forType() );
                }
            }
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.cake.tokenizer.Tokenizator#tokenize(java.lang.String)
     */
    @Override
    public List< Token > tokenize ( String source )
    {
        List<Token> tokensOfSource = new ArrayList< Token >();
        
        this.source = source;
        
        while(this.hasNext())
        {
            tokensOfSource.add( this.next() );
        }
        return tokensOfSource;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object arg0 )
    {
        return arg0 instanceof StringTokenizer
                && ((StringTokenizer) arg0 ).source.equals( this.source );
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        return "This is a Tokenizer with remaining source: " + source;
    }
}
