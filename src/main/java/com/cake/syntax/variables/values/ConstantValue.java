/*
 * 05/11/2018 at 17:53:28 ÷.
 * ConstantValue.java created by Tsvetelin
 */

package com.cake.syntax.variables.values;


/**
 * @author Tsvetelin
 *
 */
public class ConstantValue extends Value
{
    
    /**
     * @param type
     * @param value
     */
    public ConstantValue ( String type , Object value )
    {

        super( type , value );
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.variables.values.Value#setType(java.lang.String)
     */
    @Override
    public void setType ( String type )
    {
        throw new IllegalArgumentException( "Cannot change the type of a constant. " );
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.variables.values.Value#setType(java.lang.String)
     */
    @Override
    public void setValue ( Object value )
    {
        throw new IllegalArgumentException( "Cannot change the value of a constant. " );
    }

}
