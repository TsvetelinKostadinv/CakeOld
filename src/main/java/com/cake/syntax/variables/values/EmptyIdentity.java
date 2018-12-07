/*
 * 06/12/2018 at 16:15:21 ÷.
 * EmptyEntity.java created by Tsvetelin
 */

package com.cake.syntax.variables.values;


/**
 * @author Tsvetelin
 *
 */
public final class EmptyIdentity extends ConstantValue
{

    public static final String TYPE_OF_EMPTY_IDENTITY = "EMPTY_IDENTITY";

    public static final Object VALUE_OF_EMPTY_IDENTITY = null;


    /**
     * @param type
     * @param value
     */
    public EmptyIdentity ()
    {
        super( TYPE_OF_EMPTY_IDENTITY , VALUE_OF_EMPTY_IDENTITY );
    }

}
