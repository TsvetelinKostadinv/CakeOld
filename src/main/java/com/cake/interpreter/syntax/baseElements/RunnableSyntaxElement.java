/*
 * 05/12/2018 at 15:36:49 ÷.
 * RunnableSyntaxElement.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.baseElements;


import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.variables.values.Value;


/**
 * 
 * This is a syntax element that can be run
 * 
 * @author Tsvetelin
 *
 */
public abstract class RunnableSyntaxElement extends SyntaxElement
{

    /**
     * @param name
     * @param accessModifier
     */
    public RunnableSyntaxElement ( String name , AccessModifier accessModifier )
    {

        super( name , accessModifier );
    }

    /**
     * Runs this syntax element
     * 
     * @param values - the array of paramethers to the method
     */
    public abstract Result run ( CakeRuntime runtime, Value... values );


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {

        return "A runnable syntax element: ";
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.SyntaxElement#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object obj )
    {

        return super.equals( obj ) && obj instanceof RunnableSyntaxElement;
    }

}
