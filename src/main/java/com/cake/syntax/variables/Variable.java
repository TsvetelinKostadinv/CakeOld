/*
 * 05/11/2018 at 17:50:54 ÷.
 * Variable.java created by Tsvetelin
 */

package com.cake.syntax.variables;


import com.cake.syntax.AccessModifier;
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
    
    private AccessModifier accessModifier;

    private Value value;


    /**
     * @param name
     * @param value
     */
    public Variable ( String name , Value value, AccessModifier accessModifier )
    {

        super();
        this.name = name;
        this.value = value;
        this.accessModifier = accessModifier;
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


    /**
     * @return the accessModifier
     */
    public AccessModifier getAccessModifier ()
    {

        return accessModifier;
    }


    /**
     * @param accessModifier the accessModifier to set
     */
    public void setAccessModifier ( AccessModifier accessModifier )
    {

        this.accessModifier = accessModifier;
    }


}
