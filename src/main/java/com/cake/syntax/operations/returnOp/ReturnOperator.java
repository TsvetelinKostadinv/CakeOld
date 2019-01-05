/*
 * 01/01/2019 18:14:26
 * ReturnOperator.java created by Tsvetelin
 */

package com.cake.syntax.operations.returnOp;


import com.cake.syntax.expressions.Expression;

import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.operations.Operator;
import com.cake.syntax.variables.Variable;


/**
 * @author Tsvetelin
 *
 */
public class ReturnOperator extends Operator
{
    
    private Expression formula;
    
    /**
     * @param expr
     */
    public ReturnOperator ( Expression expr )
    {
        super( null );
        this.formula = expr;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.operations.Operator#calculate()
     */
    @Override
    public Variable calculate ( CakeRuntime runtime )
    {
        return formula.calculate( runtime );
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.baseElements.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return "Return with formula: " + formula.toString();
    }

}
