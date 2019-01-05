/*
 * 05/01/2019 11:23:14
 * Expression.java created by Tsvetelin
 */

package com.cake.syntax.expressions;


import java.util.List;

import com.cake.compilation.tokens.Token;
import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.operations.Operator;
import com.cake.syntax.variables.Variable;
import com.cake.utils.expressions.evaluation.ExpressionEvaluator;


/**
 * @author Tsvetelin
 *
 */
public class Expression extends Operator
{
    public static final String RESULT_VAR_NAME = "RESULT";
    
    private List< Token > tokenExpression;

    /**
     * @param name
     * @param accessModifier
     */
    public Expression ( List< Token > tokenExpression )
    {
        super( null );
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
     * @param tokenExpression the tokenExpression to set
     */
    public void setTokenExpression ( List< Token > tokenExpression )
    {
        this.tokenExpression = tokenExpression;
    }

    /* (non-Javadoc)
     * @see com.cake.syntax.operations.Operator#calculate(com.cake.running.runtime.CakeRuntime)
     */
    @Override
    public Variable calculate ( CakeRuntime runtime )
    {
        return new Variable( RESULT_VAR_NAME , ExpressionEvaluator.evaluate( runtime , tokenExpression ) , AccessModifier.GLOBAL );
    }

}
