/*
 * 09/01/2019 21:27:59
 * STDLibMethodContainer.java created by Tsvetelin
 */

package com.cake.interpreter.STDLib;

import java.util.List;

import com.cake.interpreter.STDLib.methods.STDLibMethod;
import com.cake.interpreter.STDLib.methods.printing.PrintLNMethod;
import com.cake.interpreter.STDLib.methods.printing.PrintMethod;
import com.cake.interpreter.utils.container.Container;

/**
 * @author Tsvetelin
 *
 */
public class STDLibMethodContainer extends Container< STDLibMethod >
{
    public static final STDLibMethodContainer INSTANCE = new STDLibMethodContainer();
    
    public static final PrintLNMethod PRINT_LN = new PrintLNMethod();
    public static final PrintMethod PRINT = new PrintMethod();
    
    
    private STDLibMethodContainer() {}
    
    
    public void addMethod( STDLibMethod m )
    {
        elements.add( m );
    }
    
    public List< STDLibMethod > getAllSTDMethods()
    {
        return elements;
    }
    
}
