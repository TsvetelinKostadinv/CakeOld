/*
 * 23/10/2018 at 18:26:22
 * TokenPatternContainer.java created by Tsvetelin
 */

package com.cake.tokens.patterns.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import com.cake.tokens.patterns.TokenPattern;

/**
 * 
 * Stores Token Patterns for easier access.
 * 
 * @author Tsvetelin
 *
 */
public class TokenPatternContainer implements Iterable< TokenPattern >
{
    /**
     * The singleton pattern ensures that there is one and only one instance of this object at any time
     */
    public static final TokenPatternContainer INSTANCE = new TokenPatternContainer();
    
    /**
     * All the patterns
     */
    private List< TokenPattern > patterns = new ArrayList< TokenPattern >();
    
    /**
     * The primitive patterns which are a constant
     */
    private final TokenPattern[] primitivePatterns = {
            TokenPattern.EMPTY_PATTERN,
            TokenPattern.OPERATOR_PATTERN,
            TokenPattern.IDENTIFIER_PATTERN,
            TokenPattern.STRING_LITERAL_PATTERN,
            TokenPattern.REAL_NUMBER_LITERAL_PATTERN,
            TokenPattern.INTEGER_NUMBER_LITERAL_PATTERN,
            TokenPattern.BOOLEAN_LITERAL
    };
    
    /**
     * Making it private in order to implement the singleton pattern
     */
    private TokenPatternContainer () 
    {
        for(TokenPattern p : primitivePatterns) patterns.add( p );
    }
    
    /**
     * Takes a <code>TokenPattern</code> and checks if it already exists in the list. If it does it is not added. 
     * 
     * @param newPattern - the new pattern which will be added
     */
    public void addPattern ( TokenPattern newPattern)
    {
        if ( !patterns.contains( newPattern ) )
        {
            patterns.add( newPattern );
        }
    }
    
    /**
     * 
     * Searches the list of defined patterns for one which defines this name
     * 
     * @return the <code>TokenPattern</code> if such exists
     * 
     */
    public TokenPattern getTokenPatternForToken ( final String forToken )
    {
        TokenPattern foundPattern;
        try {
            foundPattern = patterns.stream()
                    .filter( pattern -> pattern.forType().getIdentifier().equals( forToken ) )
                    .distinct()
                    .reduce( ( x, y ) -> x )
                    .get();
        }catch (NoSuchElementException e) {
            foundPattern = null;
        }
        return foundPattern;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator< TokenPattern > iterator ()
    {
        return patterns.iterator();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "This is a list of all Token patterns." + System.lineSeparator() );
        sb.append( "It contains: " + patterns.size() + " entries" + System.lineSeparator() );
        for(int i=0;i<patterns.size();i++)
        {
            sb.append( i + ": " + patterns.get( i ).toString() + System.lineSeparator() );
        }
        return sb.toString();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object arg0 )
    {
        return arg0 instanceof TokenPatternContainer
                && ((TokenPatternContainer) arg0).patterns.equals( this.patterns );
    }
    
}
