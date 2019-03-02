/*
 * 27/02/2019 13:28:37
 * VariableDeclaration.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.operators.declaration;


import java.util.ArrayList;
import java.util.List;

import com.cake.interpreter.running.runtime.CakeRuntime;
import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.baseElements.Result;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.operators.Operator;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.variables.Variable;
import com.cake.interpreter.syntax.variables.values.EmptyIdentity;
import com.cake.interpreter.syntax.variables.values.Value;
import com.cake.interpreter.utils.dynamic.StrictEither;


/**
 * @author Tsvetelin
 *
 */
public class VariableDeclaration extends Operator
{

    private String nameOfVariable;

    private StrictEither< Expression , Block > assigner;

    private AccessModifier accessModifier;


    /**
     * @param operand
     */
    public VariableDeclaration ( String nameOfVariable , Expression expression , AccessModifier accessModifier )
    {
        this.nameOfVariable = nameOfVariable;
        this.assigner = StrictEither.left( expression );
        this.accessModifier = accessModifier;
    }


    /**
     * @param operand
     */
    public VariableDeclaration ( String nameOfVariable , Block block , AccessModifier accessModifier )
    {
        this.nameOfVariable = nameOfVariable;
        this.assigner = StrictEither.right( block );
        this.accessModifier = accessModifier;
    }


    /**
     * @param operand
     */
    public VariableDeclaration ( String nameOfVariable , AccessModifier accessModifier )
    {
        this.nameOfVariable = nameOfVariable;
        this.assigner = null;
        this.accessModifier = accessModifier;
    }


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.syntax.operators.Operator#calculate(com.cake.running.runtime.
     * CakeRuntime)
     */
    @Override
    public Result calculate ( CakeRuntime runtime , List< Variable > inputVariables )
    {
        List< Variable > variables = new ArrayList<>();
        Value newValue = null;

        if ( assigner == null )
            variables.add( new Variable( nameOfVariable , new EmptyIdentity() , accessModifier ) );
        else if ( assigner.containsLeft() )
        {
            newValue = assigner.getLeft().get().calculate( runtime , inputVariables ).getReturned();
        } else
        {
            newValue = assigner.getRight().get().run( runtime ).getReturned();
        }

        variables.add( new Variable( nameOfVariable , newValue , accessModifier ) );

        return new Result( null , null , null , variables );
    }
}
