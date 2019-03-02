/*
 * 01/01/2019 18:14:26
 * ReturnOperator.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.returnOp;


import java.util.List;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.Operator;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.variables.Variable;
import com.cake.interpreter.syntax.variables.values.Value;
import com.cake.interpreter.utils.dynamic.StrictEither;


/**
 * @author Tsvetelin
 *
 */
public class ReturnOperator extends Operator
{
    
    private final StrictEither< Expression , Block > assigner;
    
    /**
     * @param expr
     */
    public ReturnOperator ( Expression expr )
    {
        this.assigner = StrictEither.left( expr );
    }
    
    /**
     * @param expr
     */
    public ReturnOperator ( Block block )
    {
        this.assigner = StrictEither.right( block );
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.operations.Operator#calculate()
     */
    @Override
    public Result calculate ( CakeRuntime runtime , List< Variable > inputVariables )
    {
        Value newValue;
        
        if( assigner.containsLeft() )
        {
            newValue = assigner.getLeft().get().calculate( runtime , inputVariables ).getReturned();
        }else {
            newValue = assigner.getRight().get().run( runtime ).getReturned();
        }
        
        return new Result( null , newValue , null , null );
    }
    
    
    /**
     * @return the assigner
     */
    public StrictEither< Expression , Block > getAssigner ()
    {
        return assigner;
    }

    /* (non-Javadoc)
     * @see com.cake.syntax.baseElements.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return "Return with formula: " + assigner.toString();
    }

}
