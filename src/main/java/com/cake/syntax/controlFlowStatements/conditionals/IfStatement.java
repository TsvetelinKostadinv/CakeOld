/*
 * 06/01/2019 12:10:55
 * IfStatement.java created by Tsvetelin
 */

package com.cake.syntax.controlFlowStatements.conditionals;


import java.util.List;

import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.blocks.scopes.Scope;
import com.cake.syntax.expressions.Expression;
import com.cake.syntax.operations.returnOp.ReturnOperator;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class IfStatement extends Block
{

    public static final String NAME = "IF-STATEMENT";

    private static int counter = 0;

    private Expression condition;

    private Block body;


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
        this.body = body;
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
        if ( (double) condition.calculate( runtime ).getValue().getValue() == 1.0 )
        { 
            Scope scope = new Scope( this );
            
            List< Variable > exitVars = scope.evaluate( runtime , values , null );
            
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
        return String.format( "If statement with condition: %s and body: %n%s" , condition.toString() , body.toString() );
    }

}
