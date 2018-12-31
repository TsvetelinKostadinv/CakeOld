/*
 * 24/12/2018 14:21:07
 * Method.java created by Tsvetelin
 */

package com.cake.syntax.methods;


import java.util.List;

import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.baseElements.RunnableSyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.methods.promise.MethodPromise;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class Method extends RunnableSyntaxElement
{

    private final MethodPromise promise;

    private final String returnVariableIdentifier;

    private final Block body;


    /**
     * @param promise
     *            - the promise for the method
     * @param body
     *            - the body of the method
     */
    public Method ( MethodPromise promise , String returnVariableIdentifier , Block body )
    {
        super( promise.getName() , promise.getAccessModifier() );
        this.promise = promise;
        this.returnVariableIdentifier = returnVariableIdentifier;
        this.body = body;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.baseElements.RunnableSyntaxElement#run(com.cake.running.
     * runtime.CakeRuntime, com.cake.syntax.variables.values.Value[])
     */
    @Override
    public Result run ( CakeRuntime runtime , Value... values )
    {
        List< Variable > exitVars = body.run( runtime , values ).getExitVariables();

        Variable returnVar = exitVars.stream().filter( x -> x.getName().equals( returnVariableIdentifier ) ).findFirst()
                .get();
        
        return new Result( this , returnVar.getValue() , null , exitVars );
        
    }


    /**
     * @return the promise
     */
    public MethodPromise getPromise ()
    {
        return promise;
    }


    /**
     * @return the body
     */
    public Block getBody ()
    {
        return body;
    }

}
