/*
 * 09/01/2019 21:24:42
 * STDLibBlock.java created by Tsvetelin
 */

package com.cake.interpreter.STDLib.block;

import com.cake.interpreter.syntax.AccessModifier;
import com.cake.interpreter.syntax.blocks.Block;

/**
 * @author Tsvetelin
 *
 */
public class STDLibBlock
{
    public static final Block STD_LIB_BLOCK = new Block( "std" , AccessModifier.GLOBAL , null );
}
