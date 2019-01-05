/*
 * 06/12/2018 at 15:44:39 ÷.
 * Block.java created by Tsvetelin
 */

package com.cake.syntax.blocks;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cake.running.runtime.CakeRuntime;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.baseElements.RunnableSyntaxElement;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;


/**
 * 
 * This is a named piece of code. Not necessarily a method, but can be run
 * 
 * @author Tsvetelin
 *
 */
public class Block extends RunnableSyntaxElement
{

    /**
     * the root name for all the blocks
     */
    public static final String ROOT_NAME = "root";

    /**
     * This is the separator for addresses between blocks of code
     */
    public static final String ADDRESS_SEPARATOR_BETWEEN_BLOCKS = ".";

    /**
     * This is the separator for addresses between a block of code and a variable
     */
    public static final String ADDRESS_SEPARATOR_BETWEEN_BLOCK_AND_VARIABLE = "#";

    /**
     * the parent of the current block
     */
    private final Block superBlock;

    /**
     * all the sub commands
     */
    private final List< SyntaxElement > subCommands = new LinkedList<>();

    private List< Variable > variables = new ArrayList<>();


    /**
     * @param name
     * @param accessModifier
     */
    public Block ( String name , AccessModifier accessModifier , Block superBlock )
    {
        super( name , accessModifier );
        this.superBlock = superBlock;
        this.assignVariables();
    }


    /**
     * 
     * @param element
     *            - the element to be added
     */
    public void addSubCommand ( SyntaxElement element )
    {
        this.subCommands.add( element );
    }


    /**
     * 
     * Adds all the sub commands
     * 
     * @param elements
     *            - the elements to be added
     */
    public void addSubCommands ( SyntaxElement... elements )
    {
        this.subCommands.addAll( Arrays.asList( elements ) );
    }


    /**
     * 
     * @return a map of all the sub commands
     */
    public Map< String , SyntaxElement > getSubCommandsWithPaths ()
    {
        Map< String , SyntaxElement > res = new LinkedHashMap<>();

        subCommands.forEach( x -> res.put( Block.joinNames( this , x ) , x ) );

        return res;
    }


    /**
     * @return the superBlock
     */
    public Block getSuperBlock ()
    {
        return superBlock;
    }


    /**
     * 
     * Gets the full name of the current block
     * 
     * @return
     */
    public String getFullName ()
    {
        return ( superBlock != null ? superBlock.getFullName() : ROOT_NAME ) + "." + this.name;
    }


    /**
     * @return the subBlocks
     */
    public List< SyntaxElement > getSubcommands ()
    {
        return subCommands;
    }


    /**
     * @return the variables
     */
    public List< Variable > getVariables ()
    {
        return variables;
    }


    /**
     * @param variables
     *            the variables to set
     */
    public void setVariables ( List< Variable > variables )
    {
        this.variables = variables;
    }


    /**
     * 
     */
    private void assignVariables ()
    {
        for ( SyntaxElement syntaxElement : subCommands )
        {
            if ( syntaxElement instanceof Variable )
            {
                this.variables.add( (Variable) syntaxElement );
            }
        }

        if ( superBlock != null ) this.variables.addAll( superBlock.variables );

    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.syntax.RunnableSyntaxElement#run(com.cake.syntax.variables.values.
     * Value[])
     */
    @Override
    public Result run ( CakeRuntime runtime , Value... values )
    {
        throw new UnsupportedOperationException( "Cannot run a standalone block" );
    }


    /**
     * 
     * Joins the names of the block and the child element
     * 
     * @param parent
     *            - the parent
     * @param child
     *            - the nested element
     * @return a string representing the full name of the <b>child</b>
     */
    public static String joinNames ( Block parent , SyntaxElement child )
    {
        String parentFullName;

        if ( parent != null ) parentFullName = parent.getFullName();
        else parentFullName = "root";

        if ( child instanceof Variable )
        {
            return parentFullName + Block.ADDRESS_SEPARATOR_BETWEEN_BLOCK_AND_VARIABLE + child.getName();
        } else if ( child instanceof Block )
        {
            return parentFullName + Block.ADDRESS_SEPARATOR_BETWEEN_BLOCKS + ( (Block) child ).getFullName();
        } else
        {
            return parentFullName + Block.ADDRESS_SEPARATOR_BETWEEN_BLOCKS + child.getName();
        }

    }
    
    /* (non-Javadoc)
     * @see com.cake.syntax.baseElements.RunnableSyntaxElement#toString()
     */
    @Override
    public String toString ()
    {
        StringBuilder sb = new StringBuilder();
        
        for ( SyntaxElement syntaxElement : subCommands )
        {
            sb.append( syntaxElement.toString() );
            sb.append( System.lineSeparator() );
        }
        return sb.toString();
    }

}
