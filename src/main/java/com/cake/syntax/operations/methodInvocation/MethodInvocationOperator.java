/*
 * 05/01/2019 12:56:34
 * MethodInvocation.java created by Tsvetelin
 */

package com.cake.syntax.operations.methodInvocation;

import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.methods.Method;
import com.cake.syntax.operations.Operator;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class MethodInvocationOperator extends Operator
{
    public static final String RETURN_VARIABLE_NAME = "ANNONIMOUS_VARIABLE_NAME";
    private Method toBeInvoked;
    private Value[] values;
    
    /**
     * @param operand
     */
    public MethodInvocationOperator ( Method toBeInvoked, Value... values )
    {
        super( null );
        this.toBeInvoked = toBeInvoked;
        this.values = values;
    }

    /* (non-Javadoc)
     * @see com.cake.syntax.operations.Operator#calculate(com.cake.running.runtime.CakeRuntime)
     */
    @Override
    public Variable calculate ( CakeRuntime runtime )
    {
        Result res = toBeInvoked.run( runtime , values );
        return new Variable( RETURN_VARIABLE_NAME , res.getReturned() , AccessModifier.PRIVATE );
    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.baseElements.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return "Invocation of method: " + toBeInvoked.getPromise().toString();
    }

}
