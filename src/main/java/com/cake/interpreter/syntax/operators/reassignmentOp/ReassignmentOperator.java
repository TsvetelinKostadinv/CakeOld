/*
 * 27/12/2018 17:19:16
 * ReassignmentOperator.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.reassignmentOp;


import java.util.ArrayList;
import java.util.List;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.AccessModifier;
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
public class ReassignmentOperator extends Operator
{
    private final StrictEither< Expression , Block > assigner;
    
    private final String assignee;
    
    /**
     * @param assignee
     * @param newValue
     */
    public ReassignmentOperator ( String assignee , Expression formula )
    {
        this.assigner = StrictEither.left( formula );
        this.assignee = assignee;
    }
    
    /**
     * @param assignee
     * @param newValue
     */
    public ReassignmentOperator ( String assignee , Block assigningBlock )
    {
        assigner = StrictEither.right( assigningBlock );
        this.assignee = assignee;
    }
   

    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.baseElements.SyntaxElement#getName()
     */
    @Override
    public String getName ()
    {
        return super.getName();
    }


    
    /**
     * @return the assignee
     */
    public String getAssignee ()
    {
        return assignee;
    }

    
    /**
     * @return the assigner
     */
    public StrictEither< Expression , Block > getAssigner ()
    {
        return assigner;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.operations.Operator#calculate()
     */
    @Override
    public Result calculate ( CakeRuntime runtime , List< Variable > inputVariables )
    {
        List< Variable > variable = new ArrayList<>();
        
        Value newValue;
        
        if( assigner.containsLeft() )
        {
            newValue = assigner.getLeft().get().calculate( runtime , inputVariables ).getReturned();
        }else {
            newValue = assigner.getRight().get().run( runtime ).getReturned();
        }
        
        variable.add( new Variable( assignee , newValue , AccessModifier.GLOBAL ) );
        
        return new Result( null , newValue , null , variable );
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.baseElements.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return "Reasssignment of: " + assignee + " with " + assigner.toString();
    }
}
