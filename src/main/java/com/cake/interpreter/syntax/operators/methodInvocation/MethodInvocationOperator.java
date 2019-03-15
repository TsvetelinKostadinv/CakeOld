/*
 * 05/01/2019 12:56:34
 * MethodInvocation.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.methodInvocation;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.methods.Method;
import com.cake.interpreter.syntax.operators.Operator;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.variables.Variable;
import com.cake.interpreter.syntax.variables.values.EmptyIdentity;
import com.cake.interpreter.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class MethodInvocationOperator extends Operator
{

    public static final String RETURN_VARIABLE_NAME = "ANNONIMOUS_VARIABLE_NAME";

    private String toBeInvokedAddress;

    private Expression [] expressions;


    /**
     * @param operand
     */
    public MethodInvocationOperator ( String toBeInvoked , Expression... expressions )
    {
        // System.out.println( "A new invocation!" );
        this.toBeInvokedAddress = toBeInvoked;
        this.expressions = expressions;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.operations.Operator#calculate(com.cake.running.runtime.
     * CakeRuntime)
     */
    @Override
    public Result calculate ( CakeRuntime runtime , List< Variable > inputVariables )
    {
//        System.out.println( this.getClass() + " || Expressions: " + Arrays.toString( expressions ) );
        Value [] values = Arrays.stream( expressions )
                .map( x -> x.calculate( runtime , inputVariables ) )
                .map( x -> x.getReturned() )
                .collect( Collectors.toList() )
                .toArray( new Value[0] );
        Result res = ( (Method) runtime.getElement( toBeInvokedAddress ) ).run( runtime , values );
        return res != null ? res : new Result( null , new EmptyIdentity() , null , null );
    }


    /**
     * @return the values
     */
    public Expression [] getExpressions ()
    {
        return expressions;
    }


    /**
     * @return the toBeInvokedAddress
     */
    public String getToBeInvokedAddress ()
    {
        return toBeInvokedAddress;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.baseElements.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return "Invocation of method: " + toBeInvokedAddress;
    }

}
