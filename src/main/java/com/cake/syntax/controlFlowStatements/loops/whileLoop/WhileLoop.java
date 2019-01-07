/*
 * 06/01/2019 17:44:53
 * WhileLoop.java created by Tsvetelin
 */

package com.cake.syntax.controlFlowStatements.loops.whileLoop;


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
public class WhileLoop extends Block
{

    public static final String NAME = "WHILE-LOOP";

    private static int counter = 0;

    private Expression condition;


    /**
     * @param name
     * @param accessModifier
     * @param superBlock
     */
    public WhileLoop ( Expression condition , Block body , Block superBlock )
    {
        super( NAME + counter , AccessModifier.LOCAL , superBlock );
        counter++;
        this.condition = condition;
        this.addSubCommands( body.getSubcommands().toArray( new SyntaxElement[0] ) );
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.blocks.Block#run(com.cake.running.runtime.CakeRuntime,
     * com.cake.syntax.variables.values.Value[])
     */
    @Override
    public Result run ( CakeRuntime runtime , Value... values )
    {
        while ( (double) condition.calculate( runtime ).getValue().getValue() == 1.0 )
        { 
            Scope scope = new Scope( this );
            
            List< Variable > exitVars = scope.evaluate( runtime , values , null );
            
            Variable retVar = null;

            for ( SyntaxElement syntaxElement : this.getSubcommands() )
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
}
