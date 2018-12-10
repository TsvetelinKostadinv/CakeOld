/*
 * 05/12/2018 at 18:48:58 ÷.
 * CakeRuntime.java created by Tsvetelin
 */

package com.cake.running.runtime;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.cake.syntax.baseElements.SyntaxElement;

/**
 * @author Tsvetelin
 *
 */
public class CakeRuntime implements Iterable< SyntaxElement >
{
    
    private final Map< String , SyntaxElement > declared = new LinkedHashMap<>();
    
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
    
    public void consumeAll ( BiConsumer< String , SyntaxElement > action )
    {
        declared.forEach( action );
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
