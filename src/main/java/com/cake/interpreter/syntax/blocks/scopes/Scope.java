/*
 * 27/02/2019 12:30:00
 * Scope.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.blocks.scopes;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.Operator;
import com.cake.interpreter.syntax.operators.declaration.VariableDeclaration;
import com.cake.interpreter.syntax.operators.methodInvocation.MethodInvocationOperator;
import com.cake.interpreter.syntax.operators.reassignmentOp.ReassignmentOperator;
import com.cake.interpreter.syntax.operators.returnOp.ReturnOperator;
import com.cake.interpreter.syntax.variables.Variable;
import com.cake.interpreter.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class Scope
{

    public Result evaluate ( CakeRuntime runtime , Block caller , List< Variable > inputVars )
    {
        List< Variable > internalVariables = new ArrayList<>( inputVars );

        Value retValue = null;

        for ( SyntaxElement el : caller.getSubcommands() )
        {
            if ( el instanceof Block )
            {
                Result res = ( (Block) el ).run( runtime );
                for ( Variable exitVar : res.getExitVariables() )
                {
                    boolean isLocal = internalVariables.stream().map( x -> x.getName() )
                            .filter( x -> x.equals( exitVar.getName() ) ).findFirst().isPresent();
                    if ( isLocal )
                    {
                        // System.out.println( "Scope || Found a local variable with name: " +
                        // exitVar.getName() );
                        int index = internalVariables.stream().map( x -> x.getName() ).collect( Collectors.toList() )
                                .indexOf( exitVar.getName() );

                        internalVariables.remove( index );
                        internalVariables.add( exitVar );

                    }
                }
            } else if ( el instanceof Operator )
            {
                if ( el instanceof VariableDeclaration )
                {
                    Variable variable = ( (VariableDeclaration) el ).calculate( runtime , internalVariables )
                            .getExitVariables().get( 0 );
                    
                    System.out.println( this.getClass().getName() + " || Declared variable: " + variable.getName() );
                    
                    internalVariables.add( variable );
                    caller.addVariable( variable );
                } else if ( el instanceof MethodInvocationOperator )
                {
                    
                    System.out.println( this.getClass().getName() + " || CAlled method: "
                            + ( (MethodInvocationOperator) el ).getToBeInvokedAddress() );
                    
                    ( (Operator) el ).calculate( runtime , internalVariables );
                } else if ( el instanceof ReassignmentOperator )
                {
                    
                    System.out.println( this.getClass().getName() + " || Reassigned var: " + ( (ReassignmentOperator) el ).getAssignee() );
                    
                    Variable newVar = ( (Operator) el ).calculate( runtime , internalVariables ).getExitVariables()
                            .get( 0 );

                    boolean internal = internalVariables.stream().map( x -> x.getName() )
                            .filter( x -> x.equals( ( (ReassignmentOperator) el ).getAssignee() ) ).findFirst()
                            .isPresent();

                    if ( internal )
                    {
                        int internalIndex = internalVariables.stream().map( x -> x.getName() )
                                .collect( Collectors.toList() ).indexOf( ( (ReassignmentOperator) el ).getAssignee() );

                        internalVariables.set( internalIndex , newVar );
                    } else
                    {
                        runtime.addDecalredElement( ( (ReassignmentOperator) el ).getAssignee() , newVar );
                    }

                } else if ( el instanceof ReturnOperator )
                {
                    System.out.println( this.getClass().getName() + " || Returning: " + ( (ReassignmentOperator) el ).getAssigner() );
                    retValue = ( (Operator) el ).calculate( runtime , internalVariables ).getReturned();
                }
            }
        }
        return new Result( caller , retValue , null , internalVariables );
    }

}
