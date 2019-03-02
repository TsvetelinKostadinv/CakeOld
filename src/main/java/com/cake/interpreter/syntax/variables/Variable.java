/*
 * 05/11/2018 at 17:50:54 ÷.
 * Variable.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.variables;


import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.variables.values.Value;


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
     * @param expr
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

        return this.value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        String superString = super.toString();
        String type = this.value == null ? "NOTHING" : this.value.toString();
        return "Variable: " + superString + " => " + this.value + "(" + type + ")";
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.SyntaxElement#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object obj )
    {

        return super.equals( obj ) && obj instanceof Variable ;
    }
}
