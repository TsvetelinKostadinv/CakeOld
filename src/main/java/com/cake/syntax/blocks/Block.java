/*
 * 06/12/2018 at 15:44:39 ÷.
 * Block.java created by Tsvetelin
 */

package com.cake.syntax.blocks;


import java.util.LinkedList;
import java.util.List;

import com.cake.syntax.AccessModifier;
import com.cake.syntax.RunnableSyntaxElement;
import com.cake.syntax.variables.values.Value;


/**
 * @author Tsvetelin
 *
 */
public class Block extends RunnableSyntaxElement
{

    public static final String ROOT_NAME = "root";

    private final Block superBlock;

    private final List< Block > subBlocks = new LinkedList<>();


    /**
     * @param name
     * @param accessModifier
     */
    public Block ( String name , AccessModifier accessModifier , Block superBlock )
    {
        super( name , accessModifier );
        this.superBlock = superBlock;
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
        return (superBlock != null ? superBlock.getFullName() : ROOT_NAME) + "." + this.name;
    }


    /**
     * @return the subBlocks
     */
    public List< Block > getSubBlocks ()
    {
        return subBlocks;
    }


    public void addSubBlock ( Block block )
    {
        this.subBlocks.add( block );
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.cake.syntax.RunnableSyntaxElement#run(com.cake.syntax.variables.values.
     * Value[])
     */
    @Override
    public void run ( Value... values )
    {

    }

}
