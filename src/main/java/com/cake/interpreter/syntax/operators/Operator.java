/*
 * 27/12/2018 17:20:18
 * Operator.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators;

import java.util.List;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.variables.Variable;


/**
 * @author Tsvetelin
 *
 */
public abstract class Operator extends SyntaxElement
{

    /**
     * 
     */
    public static final String NAME = "operator";
    
    private static int opCounter = 0;
    
    /**
     * @param name
     * @param accessModifier
     */
    public Operator ()
    {
        super( NAME + opCounter , null );
        
        opCounter++;
    }
    

    public abstract Result calculate ( CakeRuntime runtime , List< Variable > inputVariables );

}
