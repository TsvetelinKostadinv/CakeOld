/*
 * 06/12/2018 at 16:15:21 ÷.
 * EmptyEntity.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.variables.values;


/**
 * 
 * This is the empty identity
 * 
 * @author Tsvetelin
 *
 */
public final class EmptyIdentity extends ConstantValue
{
    /**
     * 
     * The type of the empty identity
     * 
     */
    public static final String TYPE_OF_EMPTY_IDENTITY = "EMPTY_IDENTITY";
    
    /**
     * 
     * The value of the empty identity
     * 
     */
    public static final Object VALUE_OF_EMPTY_IDENTITY = null;


    /**
     * @param type
     * @param value
     */
    public EmptyIdentity ()
    {
        super( TYPE_OF_EMPTY_IDENTITY , VALUE_OF_EMPTY_IDENTITY );
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.variables.values.Value#toString()
     */
    @Override
    public String toString ()
    {
        return TYPE_OF_EMPTY_IDENTITY;
    }

}
