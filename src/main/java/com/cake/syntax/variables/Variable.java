/*
 * 05/11/2018 at 17:50:54 ÷.
 * Variable.java created by Tsvetelin
 */

package com.cake.syntax.variables;


import com.cake.syntax.variables.values.Value;


/**
 * 
 * This is a named value.
 * 
 * @author Tsvetelin
 *
 */
public class Variable
{

    private String name;


    private Value value;


    /**
     * @param name
     * @param value
     */
    public Variable ( String name , Value value )
    {

        super();
        this.name = name;
        this.value = value;
    }


    /**
     * @return the name
     */
    public String getName ()
    {

        return name;
    }


    /**
     * @param name
     *            the name to set
     */
    public void setName ( String name )
    {

        this.name = name;
    }


    /**
     * @return the value
     */
    public Value getValue ()
    {

        return value;
    }


    /**
     * @param value
     *            the value to set
     */
    public void setValue ( Value value )
    {

        this.value = value;
    }


}
