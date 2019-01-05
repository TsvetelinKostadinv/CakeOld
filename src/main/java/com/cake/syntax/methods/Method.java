/*
 * 24/12/2018 14:21:07
 * Method.java created by Tsvetelin
 */

package com.cake.syntax.methods;


import java.util.List;

import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.blocks.scopes.Scope;
import com.cake.syntax.methods.promise.MethodPromise;
import com.cake.syntax.operations.returnOp.ReturnOperator;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class Method extends Block
{

    private final MethodPromise promise;

    private final Block body;


    /**
     * @param promise
     *            - the promise for the method
     * @param body
     *            - the body of the method
     */
    public Method ( MethodPromise promise , Block body , Block superBlock )
    {
        super( promise.getName() , promise.getAccessModifier() , superBlock );
        this.promise = promise;
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
        
        if( promise.canRunWithValues( values ) )
        {
            List< Variable > input = promise.constructInputVariablesList( values );
            Scope scope = new Scope( body );
            
            List< Variable > exitVars = scope.evaluate( runtime , values , input );
            
            Variable retVar = null;

            for ( SyntaxElement syntaxElement : body.getSubcommands() )
            {
                if ( syntaxElement instanceof ReturnOperator )
                {
                    retVar = ( (ReturnOperator) syntaxElement ).calculate( runtime );
                }
            }
            if( retVar != null)
            {
                return new Result( this , retVar.getValue() , null , exitVars );
            }else {
                return new Result( this , null , null , exitVars );
            }
        }
        
        throw new IllegalArgumentException( "Method with declaration: " + promise.toString() + "cannot run with the supplied values" );
        
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
