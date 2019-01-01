/*
 * 27/12/2018 17:19:16
 * ReassignmentOperator.java created by Tsvetelin
 */

package com.cake.syntax.operations.reassignmentOp;

import com.cake.syntax.AccessModifier;
import com.cake.syntax.operations.Operator;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;

/**
 * @author Tsvetelin
 *
 */
public class ReassignmentOperator extends Operator
{
    private final Value newValue;
    
    /**
     * @param assignee
     * @param newValue
     */
    public ReassignmentOperator ( Variable assignee , Value newValue )
    {
        super();
        this.setOperand( assignee );
        this.newValue = newValue;
    }
    
    /**
     * @return the newValue
     */
    public Value getNewValue ()
    {
        return newValue;
    }
    
    public Variable calculateReassignment()
    {
        Variable newVar = this.getOperand();
        newVar.setValue( newValue );
        return newVar;
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.baseElements.SyntaxElement#getAccessModifier()
     */
    @Override
    public AccessModifier getAccessModifier ()
    {
        throw new UnsupportedOperationException( "Cannot get access modifier of a operator" );
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.baseElements.SyntaxElement#getName()
     */
    @Override
    public String getName ()
    {
        throw new UnsupportedOperationException( "Cannot get name of a operator" );
    }

    /* (non-Javadoc)
     * @see com.cake.syntax.operations.Operator#calculate()
     */
    @Override
    public Variable calculate ()
    {
        return this.calculateReassignment();
    }
}
