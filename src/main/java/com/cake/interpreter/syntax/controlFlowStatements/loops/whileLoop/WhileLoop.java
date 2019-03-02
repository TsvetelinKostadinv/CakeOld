/*
 * 06/01/2019 17:44:53
 * WhileLoop.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.controlFlowStatements.loops.whileLoop;


import java.util.List;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.blocks.scopes.Scope;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.operators.returnOp.ReturnOperator;
import com.cake.interpreter.syntax.variables.Variable;
import com.cake.interpreter.syntax.variables.values.Value;


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
        
        System.out.println( "WHILE || Body: " + body );
        
        this.inheritVariables( superBlock );
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
        
        this.inheritVariables( this.getSuperBlock() );
        
        Value retVal = null;
        Scope scope = new Scope();
        List< Variable > exitVars = null;
//        System.out.println( "In while || parent : " + this.getSuperBlock().getFullName() );
//        System.out.println( "In while || parent vars : " + getSuperBlock().getVariables() );
        
        Double conditionResult = (Double) ((Value) condition
                .calculate( runtime , getSuperBlock().getVariables() )
                .getReturned()
                .getValue()).getValue();
        
        while ( conditionResult == 1.0 )
        {
//            System.out.println( "While || Running.." );
            exitVars = scope.evaluate( runtime , this , this.getVariables() ).getExitVariables();
            
//            System.out.println( "While || Variables: " + this.getVariables() );
//            System.out.println( "While || Scope of while exited with vars: " + exitVars );
            
//            try
//            {
//                Thread.sleep( 1000 );
//            } catch ( InterruptedException e )
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            
            this.setVariables( exitVars );
            
            for ( SyntaxElement syntaxElement : this.getSubcommands() )
            {
                if ( syntaxElement instanceof ReturnOperator )
                {
                    retVal = ( (ReturnOperator) syntaxElement ).calculate( runtime , getSuperBlock().getVariables() )
                            .getReturned();
                }
            }
            
            conditionResult = (Double) ( (Value) condition
                    .calculate( runtime , this.getVariables() )
                    .getReturned()
                    .getValue()).getValue();
        }
        
//        System.out.println( "While || FINAL Exiting with variables: " + exitVars );
            
        if ( retVal != null )
        {
            return new Result( this , retVal , null , exitVars );
        } else
        {
            return new Result( this , null , null , exitVars );
        }
    }
}
