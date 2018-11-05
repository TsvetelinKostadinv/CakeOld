/*
 * 05/11/2018 at 18:15:03 ÷.
 * ParsersContainer.java created by Tsvetelin
 */

package com.cake.syntax.parsers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.cake.utils.container.Container;

/**
 * 
 * Container for all the parsers. They should be included here in order to be used.
 * 
 * @author Tsvetelin
 *
 */
public class ParsersContainer extends Container< Parser < ? > >
{
    public static ParsersContainer INSTANCE = new ParsersContainer();
    
    private List< Parser < ? > > parsers = new LinkedList<>();
    
    private ParsersContainer() {}

    /* (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator< Parser< ? > > iterator ()
    {
        return parsers.iterator();
    }
}
