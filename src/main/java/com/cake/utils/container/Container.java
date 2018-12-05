/*
 * 05/11/2018 at 18:20:04 ÷.
 * Container.java created by Tsvetelin
 */

package com.cake.utils.container;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tsvetelin
 *
 */
public abstract class Container< ContainedElement > implements Iterable< ContainedElement >
{
    protected List< ContainedElement > elements = new LinkedList<>();
    
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
    
}
