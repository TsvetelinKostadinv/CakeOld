/*
 * 01/01/2019 18:14:26
 * ReturnOperator.java created by Tsvetelin
 */

package com.cake.syntax.operations.returnOp;


import com.cake.syntax.operations.Operator;
import com.cake.syntax.variables.Variable;


/**
 * @author Tsvetelin
 *
 */
public class ReturnOperator extends Operator
{

    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.operations.Operator#calculate()
     */
    @Override
    public Variable calculate ()
    {
        return this.getOperand();
    }

}
