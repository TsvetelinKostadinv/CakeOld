/*
 * 23/10/2018 at 18:26:22
 * TokenPatternContainer.java created by Tsvetelin
 */

package com.cake.compilation.tokens.patterns.container;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.cake.compilation.tokens.patterns.TokenPattern;
import com.cake.utils.container.Container;

/**
 * 
 * Stores Token Patterns for easier access.
 * 
 * @author Tsvetelin
 *
 */
public class TokenPatternContainer extends Container< TokenPattern >
{
    /**
     * The singleton pattern ensures that there is one and only one instance of this object at any time
     */
    public static final TokenPatternContainer INSTANCE = new TokenPatternContainer();
    
    /**
     * The primitive patterns which are a constant
     */
    private final TokenPattern[] primitivePatterns = getPrimitivePattersInTokenPatterns();

    /**
     * Making it private in order to implement the singleton pattern
     */
    private TokenPatternContainer () 
    {
        for(TokenPattern p : primitivePatterns) elements.add( p );
    }
    
    /**
     * Takes a <code>TokenPattern</code> and checks if it already exists in the list. If it does it is not added. 
     * 
     * @param newPattern - the new pattern which will be added
     */
    public void addPattern ( TokenPattern newPattern)
    {
        super.addElement( newPattern );
    }
    
    /**
     * 
     * Searches the list of defined patterns for one which defines this name
     * 
     * @return the <code>TokenPattern</code> if such exists or null otherwise
     * 
     */
    public TokenPattern getTokenPatternForToken ( final String forToken )
    {
        for(TokenPattern pattern : elements)
        {
            if(pattern.forType().getIdentifier().equals( forToken ))
            {
                return pattern;
            }
        }
        return null;
    }
    
    /**
     * @return
     */
    private TokenPattern [] getPrimitivePattersInTokenPatterns ()
    {
        return Arrays.stream( TokenPattern.class.getDeclaredFields() )
                        .filter( x -> x.getType().equals( TokenPattern.class ) )
                        .filter( x -> Modifier.isStatic( x.getModifiers() ) )
                        .peek( x -> x.setAccessible( true ) )
                        .map( x -> getValue(x) )
                        .collect( Collectors.toList() )
                        .toArray( new TokenPattern[0] );
    }

    /**
     * @param x
     * @return
     */
    private TokenPattern getValue ( Field x )
    {
        try
        {
            return (TokenPattern) x.get( null );
        } catch ( IllegalArgumentException e )
        {
            // they are all static no need
            return null;
        } catch ( IllegalAccessException e )
        {
            //they are already set to visible
            return null;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "This is a list of all Token patterns." + System.lineSeparator() );
        sb.append( "It contains: " + elements.size() + " entries" + System.lineSeparator() );
        for(int i=0;i<elements.size();i++)
        {
            sb.append( i + ": " + elements.get( i ).toString() + System.lineSeparator() );
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
                && ((TokenPatternContainer) arg0).elements.equals( this.elements );
    }
    
}
