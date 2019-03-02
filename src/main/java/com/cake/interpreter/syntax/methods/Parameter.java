/*
 * 24/12/2018 14:49:07
 * Parameter.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.methods;


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


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        return "Parameter: " + this.getType() + " " + this.getName();
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object obj )
    {
        return obj instanceof Parameter 
                && ( (Parameter) obj ).getType().equals( this.getType() )
                && ( (Parameter) obj ).getName().equals( this.getName() );
    }
}
