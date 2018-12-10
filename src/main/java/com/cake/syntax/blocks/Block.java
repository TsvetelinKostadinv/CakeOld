/*
 * 06/12/2018 at 15:44:39 ÷.
 * Block.java created by Tsvetelin
 */

package com.cake.syntax.blocks;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cake.syntax.AccessModifier;
import com.cake.syntax.baseElements.Result;
import com.cake.syntax.baseElements.RunnableSyntaxElement;
import com.cake.syntax.baseElements.SyntaxElement;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class Block extends RunnableSyntaxElement
{

    public static final String ROOT_NAME = "root";

    public static final String ADDRESS_SEPARATOR_BETWEEN_BLOCKS = ".";

    public static final String ADDRESS_SEPARATOR_BETWEEN_BLOCK_AND_VARIABLE = "#";

    private final Block superBlock;

    private final List< SyntaxElement > subCommands = new LinkedList<>();


    /**
     * @param name
     * @param accessModifier
     */
    public Block ( String name , AccessModifier accessModifier , Block superBlock )
    {
        super( name , accessModifier );
        this.superBlock = superBlock;
    }


    public void addSubCommand ( SyntaxElement element )
    {
        this.subCommands.add( element );
    }


    public void addSubCommands ( SyntaxElement... elements )
    {
        this.subCommands.addAll( Arrays.asList( elements ) );
    }


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


    public String getFullName ()
    {
        return ( superBlock != null ? superBlock.getFullName() : ROOT_NAME ) + "." + this.name;
    }


    /**
     * @return the subBlocks
     */
    public List< SyntaxElement > getSubBlocks ()
    {
        return subCommands;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.syntax.RunnableSyntaxElement#run(com.cake.syntax.variables.values.
     * Value[])
     */
    @Override
    public Result run ( Value... values )
    {
        throw new UnsupportedOperationException( "Runnning not implemented yet!" );
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
        if ( child instanceof Variable )
        {
            return parent.getFullName() + Block.ADDRESS_SEPARATOR_BETWEEN_BLOCK_AND_VARIABLE + child.getName();
        } else if ( child instanceof Block )
        {
            return parent.getFullName() + Block.ADDRESS_SEPARATOR_BETWEEN_BLOCKS + ( (Block) child ).getFullName();
        } else
        {
            return parent.getFullName() + Block.ADDRESS_SEPARATOR_BETWEEN_BLOCKS + child.getName();
        }

    }

}
