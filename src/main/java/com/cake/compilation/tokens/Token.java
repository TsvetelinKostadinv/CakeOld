/*
 * 24/10/2018 at 17:58:59
 * Token.java created by Tsvetelin
 */

package com.cake.tokens;

import com.cake.tokens.types.TokenTypesContainer.TokenTypeHolder;

/**
 * 
 * A simple data structure to hold a single token of the language
 * 
 * @author Tsvetelin
 *
 */
public class Token
{
    /**
     * This is the String representation from the source
     */
    private String token;
    
    /**
     * This is the token type of the object
     */
    private TokenTypeHolder tokenType;
    
    
    /**
     * @param token
     * @param tokenType
     */
    public Token ( String token , TokenTypeHolder tokenType )
    {

        super();
        this.token = token;
        this.tokenType = tokenType;
    }

    /**
     * @return the token
     */
    public String getToken ()
    {

        return token;
    }

    /**
     * @return the tokenType
     */
    public TokenTypeHolder getTokenType ()
    {

        return tokenType;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object arg0 )
    {
        return arg0 instanceof Token
                && ( (Token) arg0 ).token.equals( this.token )
                && ( (Token) arg0 ).tokenType.equals( this.tokenType );
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        return "Token with type: " + this.tokenType.getIdentifier() + " and token value: " + this.token;
    }
}
