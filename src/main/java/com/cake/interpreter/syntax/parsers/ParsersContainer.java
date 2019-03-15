/*
 * 05/11/2018 at 18:15:03 ÷.
 * ParsersContainer.java created by Tsvetelin
 */

package com.cake.interpreter.syntax.parsers;


import java.util.ArrayList;
import java.util.List;

import com.cake.interpreter.syntax.baseElements.SyntaxElement;
import com.cake.interpreter.syntax.blocks.Block;
import com.cake.interpreter.syntax.blocks.parser.BlockParser;
import com.cake.interpreter.syntax.controlFlowStatements.conditionals.IfStatement;
import com.cake.interpreter.syntax.controlFlowStatements.conditionals.parser.IfStatementParser;
import com.cake.interpreter.syntax.controlFlowStatements.loops.whileLoop.WhileLoop;
import com.cake.interpreter.syntax.controlFlowStatements.loops.whileLoop.parser.WhileLoopParser;
import com.cake.interpreter.syntax.methods.Method;
import com.cake.interpreter.syntax.methods.parser.MethodParser;
import com.cake.interpreter.syntax.methods.promise.MethodPromise;
import com.cake.interpreter.syntax.methods.promise.parser.MethodPromiseParser;
import com.cake.interpreter.syntax.operators.declaration.VariableDeclaration;
import com.cake.interpreter.syntax.operators.declaration.parser.VariableDeclarationParser;
import com.cake.interpreter.syntax.operators.expressions.Expression;
import com.cake.interpreter.syntax.operators.expressions.parser.ExpressionParser;
import com.cake.interpreter.syntax.operators.methodInvocation.MethodInvocationOperator;
import com.cake.interpreter.syntax.operators.methodInvocation.parser.MethodInvokationOperatorParser;
import com.cake.interpreter.syntax.operators.reassignmentOp.ReassignmentOperator;
import com.cake.interpreter.syntax.operators.reassignmentOp.parser.ReassignmentOperationParser;
import com.cake.interpreter.syntax.operators.returnOp.ReturnOperator;
import com.cake.interpreter.syntax.operators.returnOp.parser.ReturnOperatorParser;
import com.cake.interpreter.utils.container.Container;
import com.cake.tokenization.tokens.Token;


/**
 * 
 * Container for all the parsers. They should be included here in order to be
 * used. <br>
 * It searches for parsers that extend
 * <code>com.cake.syntax.parsers.Parser</code> <br>
 * If they extend it they will be added here
 * 
 * @see com.cake.interpreter.syntax.parsers.Parser
 * 
 * @author Tsvetelin
 *
 */
public class ParsersContainer extends Container< Parser< ? > >
{

    public static final ParsersContainer INSTANCE = new ParsersContainer();

    public static final Parser< Block > BLOCK_PARSER = new BlockParser();
    public static final Parser< Expression > EXPRESSION_PARSER = new ExpressionParser();
    public static final Parser< IfStatement > IF_STATEMENT_PARSER = new IfStatementParser();
    public static final Parser< MethodInvocationOperator > METHOD_INVOCATION_OPERATOR_PARSER = new MethodInvokationOperatorParser();
    public static final Parser< Method > METHOD_PARSER = new MethodParser();
    public static final Parser< MethodPromise > METHOD_PROMISE_PARSER = new MethodPromiseParser();
    public static final Parser< ReassignmentOperator > REASSIGNMENT_OPERATOR_PARSER = new ReassignmentOperationParser();
    public static final Parser< ReturnOperator > RETURN_OPERATOR_PARSER = new ReturnOperatorParser();
    public static final Parser< VariableDeclaration > VARIABLE_DECLARATION_PARSER = new VariableDeclarationParser();
    public static final Parser< WhileLoop > WHILE_LOOP_PARSER = new WhileLoopParser();
    

    private ParsersContainer ()
    {
    }


    /**
     * Adds the supplied parser
     * 
     * @param parser
     */
    public void addParser ( Parser< ? > parser )
    {

        if ( !elements.contains( parser ) ) super.addElement( parser );
    }


    /**
     * Removes the supplied parser
     * 
     * @param parser
     */
    public void removeParser ( Parser< ? > parser )
    {

        super.removeElement( parser );
    }


    /**
     * 
     * Determines the parser for the supplied code
     * 
     * @param code
     * @return the parser for this sequence
     */
    public synchronized List< Parser< ? extends SyntaxElement > > getParserFor ( List< Token > code )
    {
        List< Parser< ? > > res = new ArrayList<>();
        for ( int i = 0; i< elements.size() ; i++)
        {
            if ( elements.get( i ).canParse( code ) ) res.add( elements.get( i ) );
        }
        return res;
    }

}
