/*
 * 05/12/2018 at 18:48:58 ÷.
 * CakeRuntime.java created by Tsvetelin
 */

package com.cake.running.runtime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cake.syntax.SyntaxElement;
import com.cake.utils.container.Container;

/**
 * @author Tsvetelin
 *
 */
public class CakeRuntime extends Container< SyntaxElement >
{
    
    private final Map< String , SyntaxElement > declared = new HashMap<>();
    
    /**
     * 
     */
    public CakeRuntime ()
    {
        
    }
    
    public void addDecalredElement ( String name, SyntaxElement element )
    {
        declared.put( name , element );
    }
    
    public void removeElement ( String name )
    {
        declared.remove( name );
    }
    
    public SyntaxElement getElement( String name )
    {
        return declared.get( name );
    }
    
    /* (non-Javadoc)
     * @see com.cake.utils.container.Container#iterator()
     */
    @Override
    public Iterator< SyntaxElement > iterator ()
    {
        return declared.entrySet().stream().map( x -> x.getValue() ).iterator();
    }
}
