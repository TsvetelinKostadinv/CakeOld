/*
 * 05/11/2018 at 18:20:04 ÷.
 * Container.java created by Tsvetelin
 */

package com.cake.utils.container;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * A container for a certain type of elements
 * 
 * @author Tsvetelin
 *
 */
public abstract class Container< ContainedElement > implements Iterable< ContainedElement >
{
    protected final List< ContainedElement > elements = new LinkedList<>();
    
    /**
     * 
     * If the element is not already in the list it is added
     * 
     * @param element - the element to be added. Cannot be <code>null</code>
     */
    protected void addElement ( ContainedElement element )
    {
        if ( element != null && !elements.contains( element ) )
        {
            elements.add( element );
        }
    }
    
    /**
     * 
     * Removes the element
     * 
     * @param element - the element to be removed
     */
    protected void removeElement ( ContainedElement element )
    {
        elements.remove( element );
    }
    
    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator< ContainedElement > iterator ()
    {
        return elements.iterator();
    }
    
}
