/*
 * 05/01/2019 11:23:14
 * Expression.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.expressions;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.operators.Operator;
import com.cake.interpreter.syntax.variables.Type;
import com.cake.interpreter.syntax.variables.Variable;
import com.cake.interpreter.syntax.variables.values.Value;
import com.cake.interpreter.utils.expressions.evaluation.ExpressionEvaluator;
import com.cake.tokenization.tokens.Token;


/**
 * @author Tsvetelin
 *
 */
public class Expression extends Operator
{

    private List< Token > tokenExpression = new CopyOnWriteArrayList<>();


    /**
     * @param operand
     */
    public Expression ( List< Token > tokenExpression )
    {
        this.setTokenExpression( tokenExpression );
    }


    /**
     * @return the tokenExpression
     */
    public List< Token > getTokenExpression ()
    {
        return tokenExpression;
    }


    /**
     * @param tokenExpression
     *            the tokenExpression to set
     */
    private void setTokenExpression ( List< Token > tokenExpression )
    {
        this.tokenExpression.addAll( tokenExpression );
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.baseElements.SyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return tokenExpression.stream().map( x -> x.getToken() ).reduce( "" , ( x , y ) -> x + y );
    }


    /* (non-Javadoc)
     * @see com.cake.syntax.operators.Operator#calculate(com.cake.running.runtime.CakeRuntime, java.util.List)
     */
    @Override
    public Result calculate ( CakeRuntime runtime , List< Variable > inputVariables )
    {
        return 
                new Result( 
                        null ,
                        new Value( 
                                Type.INTEGER.name() , 
                                ExpressionEvaluator.evaluate( runtime , tokenExpression ,inputVariables ) ) ,
                        null ,
                        null );
    }
}
