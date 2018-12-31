/*
 * 24/12/2018 14:49:07
 * Parameter.java created by Tsvetelin
 */

package com.cake.syntax.methods;


/**
 * @author Tsvetelin
 *
 */
public class Parameter
{
    private final String name;
    private final String type;
    
    /**
     * @param name
     * @param type
     */
    public Parameter ( String name , String type )
    {
        super();
        this.name = name;
        this.type = type;
    }

    /**
     * @return the name
     */
    public String getName ()
    {
        return name;
    }

    /**
     * @return the type
     */
    public String getType ()
    {
        return type;
    }
}
