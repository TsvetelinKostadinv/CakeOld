/*
 * 27/12/2018 17:20:18
 * Operator.java created by Tsvetelin
 */

package com.cake.syntax.operations;

import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.variables.Variable;


/**
 * @author Tsvetelin
 *
 */
public abstract class Operator extends SyntaxElement
{

    private static int opCounter = 0;
    
    private Variable operand;
    

    /**
     * @param name
     * @param accessModifier
     */
    public Operator ( Variable operand )
    {
        super( "operator" + opCounter , null );
        
        opCounter++;
        this.operand = operand;
    }
    

    public abstract Variable calculate ();
    
    /**
     * @return the operand
     */
    public Variable getOperand ()
    {
        return operand;
    }

    /**
     * @param operand the operand to set
     */
    public void setOperand ( Variable operand )
    {
        this.operand = operand;
    }

}
