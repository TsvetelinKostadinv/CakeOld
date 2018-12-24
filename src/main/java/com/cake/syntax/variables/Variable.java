/*
 * 05/11/2018 at 17:50:54 ÷.
 * Variable.java created by Tsvetelin
 */

package com.cake.syntax.variables;


import com.cake.syntax.AccessModifier;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.variables.values.Value;


/**
 * 
 * This is a named value.
 * 
 * @author Tsvetelin
 *
 */
public class Variable extends SyntaxElement
{

    private Value value;


    /**
     * @param name
     * @param value
     */
    public Variable ( String name , Value value , AccessModifier accessModifier )
    {

        super( name , accessModifier );
        this.value = value;
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

        if ( value != this.value ) this.value = value;

    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {

        return "Variable: " + super.toString() + " => " + this.value + "(" + this.value.getType() + ")";
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.SyntaxElement#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object obj )
    {

        return super.equals( obj ) && obj instanceof Variable && ( (Variable) obj ).value.equals( this.value );
    }
}
