/*
 * 27/12/2018 14:58:30
 * Scope.java created by Tsvetelin
 */

package com.cake.syntax.blocks.scopes;


import java.util.ArrayList;
import java.util.List;

import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.operations.Operator;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class Scope
{

    private final Block block;


    public Scope ( Block block )
    {
        this.block = block;
    }


    /**
     * @param values
     * @param runtime
     * @return the exit variables
     */
    public List< Variable > evaluate ( CakeRuntime runtime , Value [] values , List< Variable > input )
    {
        List< Variable > exitVariables = new ArrayList<>();

        exitVariables.addAll( input );

        input.forEach( x -> runtime.addDecalredElement( Block.joinNames( block , x ) , x ) );

        // System.out.println( "Block sub commands: " + block.getSubcommands() );

        for ( SyntaxElement element : block.getSubcommands() )
        {
            if ( element instanceof Variable )
            {
                exitVariables.add( (Variable) element );
            } else if ( element instanceof Block )
            {
                exitVariables.addAll( ( (Block) element ).run( runtime , values ).getExitVariables() );
            } else if ( element instanceof Operator )
            {
                Operator operator = (Operator) element;
                if( operator.getOperand() != null )
                {
                    String address = operator.getOperand().getName();
                    Variable val = operator.calculate( runtime );
                    runtime.addDecalredElement( address , val );
                }else {
                    operator.calculate( runtime );
                }
                
            }
        }
        return exitVariables;
    }


    /**
     * @return the block
     */
    public Block getBlock ()
    {
        return block;
    }

}
