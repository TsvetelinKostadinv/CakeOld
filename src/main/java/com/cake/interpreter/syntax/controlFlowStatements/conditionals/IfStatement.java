/*
 * 06/01/2019 12:10:55
 * IfStatement.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.controlFlowStatements.conditionals;


import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.blocks.scopes.Scope;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class IfStatement extends Block
{

    public static final String NAME = "IF-STATEMENT";

    private static int counter = 0;

    private Expression condition;

    /**
     * @param name
     * @param accessModifier
     * @param condition
     * @param body
     */
    public IfStatement ( Expression condition , Block body , Block superblock )
    {
        super( NAME + counter , AccessModifier.LOCAL, superblock );
        counter++;
        this.condition = condition;
        this.addSubCommands( body.getSubcommands().toArray( new SyntaxElement[0] ) );
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
        this.inheritVariables( this.getSuperBlock() );
        
        Double conditionResult = (Double) ((Value) condition
                .calculate( runtime , 
                        getSuperBlock().getVariables() )
                .getReturned()
                .getValue()).getValue();
        
        if ( conditionResult == 1.0 )
        { 
            Scope scope = new Scope();
            
            Result res = scope.evaluate( runtime , this , this.getVariables() );
            
            return res;
        }
        return null;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.baseElements.RunnableSyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        return String.format( "If statement with condition: %s and body: %n%s" , condition.toString() , getSubcommands().toString() );
    }

}
