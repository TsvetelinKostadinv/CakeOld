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
 * 
 * This is the default Runtime of the Cake language
 * 
 * @author Tsvetelin
 *
 */
public class CakeRuntime implements Iterable< SyntaxElement >
{

    /**
     * A map of all declared elements and the paths to them
     */
    private final Map< String , SyntaxElement > declared = new LinkedHashMap<>();

    
    /**
     *
     * 
     * @param name
     *            - the full name to the variable
     * @param element
     *            - the element to be added
     */
    public void addDecalredElement ( String name , SyntaxElement element )
    {
        declared.put( name , element );
    }


    /**
     * 
     * 
     * @param name
     *            - the full name of the element to be removed
     */
    public void removeElement ( String name )
    {
        declared.remove( name );
    }


    /**
     * 
     * 
     * 
     * @param name
     *            - the full name of the element wanted
     * @return the element from that address or null if it does not exist
     */
    public SyntaxElement getElement ( String name )
    {
        return declared.containsKey( name ) ? declared.get( name ) : null;
    }

    /**
     * 
     * A normal for each function taking a biconsumer. The first is the address and the second is the element
     * 
     * @param action
     */
    public void forEach ( BiConsumer< String , SyntaxElement > action )
    {
        declared.forEach( action );
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.utils.container.Container#iterator()
     */
    @Override
    public Iterator< SyntaxElement > iterator ()
    {
        return declared.entrySet().stream().map( x -> x.getValue() ).iterator();
    }
}
