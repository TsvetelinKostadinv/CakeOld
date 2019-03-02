/*
 * 24/12/2018 14:21:07
 * Method.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.methods;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.blocks.scopes.Scope;
import com.cake.interpreter.syntax.methods.promise.MethodPromise;
import com.cake.interpreter.syntax.variables.Variable;
import com.cake.interpreter.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class Method extends Block
{

    private final MethodPromise promise;


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
        if ( body != null )
        {
            this.addSubCommands( body.getSubcommands().toArray( new SyntaxElement[0] ) );
        }
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
        if ( promise.canRunWithValues( values ) )
        {
            List< Variable > inputVariables = promise.constructInputVariablesList( values );

            Scope scope = new Scope();

            Result res = scope.evaluate( runtime , this , inputVariables );
            
//            System.out.println( "Method || Scope exited with: " + res.getExitVariables() );
            
            for ( Variable exitVar : res.getExitVariables() )
            {
                boolean isLocal = this.getVariables().stream().map( x -> x.getName() )
                        .filter( x -> x.equals( exitVar.getName() ) ).findFirst().isPresent();
                if ( isLocal )
                {
//                    System.out.println( "Method || Found a local variable with name: " + exitVar.getName() );
                    int index = this.getVariables().stream().map( x -> x.getName() ).collect( Collectors.toList() )
                            .indexOf( exitVar.getName() );
                    
                    List< Variable > newVars = this.getVariables();
                    newVars.remove( index );
                    newVars.add( exitVar );
                    
                    this.setVariables( newVars );
                    
                }
            }

            return res;
        } else
        {
            throw new IllegalArgumentException( "Method with promise: " + promise.toString()
                    + " cannot run with values: " + Arrays.toString( values ) );
        }

    }


    /**
     * @return the promise
     */
    public MethodPromise getPromise ()
    {
        return promise;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.blocks.Block#toString()
     */
    @Override
    public String toString ()
    {
        return promise.toString() + " with body " + this.getSubcommands();
    }
}
