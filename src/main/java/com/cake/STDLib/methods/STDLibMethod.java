/*
 * 09/01/2019 21:22:36
 * STDLibMethod.java created by Tsvetelin
 */

package com.cake.STDLib.methods;

import com.cake.STDLib.STDLibMethodContainer;
import com.cake.STDLib.block.STDLibBlock;
import com.cake.syntax.AccessModifier;
import com.cake.syntax.blocks.Block;
import com.cake.syntax.methods.Method;
import com.cake.syntax.methods.promise.MethodPromise;


/**
 * @author Tsvetelin
 *
 */
public class STDLibMethod extends Method
{
    
    
    /**
     * @param promise
     * @param body
     * @param superBlock
     */
    public STDLibMethod ( MethodPromise promise)
    {
        super( promise, new Block( null , AccessModifier.PRIVATE ,  STDLibBlock.STD_LIB_BLOCK ) , STDLibBlock.STD_LIB_BLOCK );
        STDLibMethodContainer.INSTANCE.addMethod( this );
    }

}
