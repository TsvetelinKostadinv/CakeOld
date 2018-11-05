/*
 * 05/11/2018 at 17:32:49 ÷.
 * Value.java created by Tsvetelin
 */

package com.cake.syntax.variables.values;


/**
 * 
 * A simple holder class for a value.
 * 
 * @author Tsvetelin
 *
 */
public class Value
{

    private String type;


    private Object value;


    /**
     * @param value
     *            - the value to be stored
     */
    public Value ( String type , Object value )
    {

        super();
        this.value = value;
        this.setType( type );
    }


    /**
     * @return the type
     */
    public String getType ()
    {

        return type;
    }


    /**
     * @param type
     *            the type to set
     */
    public void setType ( String type )
    {

        this.type = type;
    }


    /**
     * @return the value
     */
    public Object getValue ()
    {

        return value;
    }


    /**
     * @param value
     *            the value to set
     */
    public void setValue ( Object value )
    {

        this.value = value;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {

        return "A value with type: " + this.getType() + " and value: " + this.value.toString();
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object obj )
    {

        return obj instanceof Value && ( (Value) obj ).value.equals( this.value );
    }

}
