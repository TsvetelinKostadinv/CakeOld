/*
 * 27/12/2018 17:19:16
 * ReassignmentOperator.java created by Tsvetelin
 */

package com.cake.syntax.operations.reassignmentOp;


import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.expressions.Expression;
import com.cake.syntax.operations.Operator;
import com.cake.syntax.variables.Variable;


/**
 * @author Tsvetelin
 *
 */
public class ReassignmentOperator extends Operator
{
    
    private Expression formula;
    
    private Block assigningBlock;
    
    /**
     * @param assignee
     * @param newValue
     */
    public ReassignmentOperator ( Variable assignee , Expression formula )
    {
        super( assignee );
        this.formula = formula;
        this.assigningBlock = null;
    }
    
    /**
     * @param assignee
     * @param newValue
     */
    public ReassignmentOperator ( Variable assignee , Block assigningBlock )
    {
        super( assignee );
        this.formula = null;
        this.assigningBlock = assigningBlock;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.baseElements.SyntaxElement#getAccessModifier()
     */
    @Override
    public AccessModifier getAccessModifier ()
    {
        throw new UnsupportedOperationException( "Cannot get access modifier of a operator" );
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


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.operations.Operator#calculate()
     */
    @Override
    public Variable calculate ( CakeRuntime runtime )
    {
        Variable newVar = this.getOperand();
        if( formula != null )
        {
            newVar.setValue( formula.calculate( runtime ).getValue() );
        }else {
            newVar.setValue( assigningBlock.run( runtime ).getReturned() );
        }
        return newVar;
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.baseElements.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return "Reasssignment of: " + getOperand().getName() + " with " + formula.toString();
    }
}
