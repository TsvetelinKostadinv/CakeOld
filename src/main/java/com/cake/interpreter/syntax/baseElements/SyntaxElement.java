/*
 * 05/12/2018 at 15:22:30 �.
 * SyntaxElement.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.baseElements;

import com.cake.interpreter.syntax.AccessModifier;

/**
 * 
 * The basic building block of the language
 * 
 * @author Tsvetelin
 *
 */
public abstract class SyntaxElement
{   
    
    protected String name;
    
    /**
     * it's access modifier
     */
    protected AccessModifier accessModifier;


    /**
     * @param name
     * @param accessModifier
     */
    public SyntaxElement ( String name , AccessModifier accessModifier )
    {

        super();
        this.name = name;
        this.accessModifier = accessModifier;
    }

    public String getName()
    {
        return name;
    }
    
    /**
     * @return the accessModifier
     */
    public AccessModifier getAccessModifier ()
    {

        return accessModifier;
    }


    /**
     * @param accessModifier
     *            the accessModifier to set
     */
    public void setAccessModifier ( AccessModifier accessModifier )
    {

        this.accessModifier = accessModifier;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString ()
    {
        return this.accessModifier==null? "NO-ACCESS" :this.accessModifier.toString() + " " + this.name;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals ( Object obj )
    {

        return obj instanceof SyntaxElement
                && ( (SyntaxElement) obj ).accessModifier.equals( this.accessModifier );
    }
}