/*
 * 09/01/2019 21:27:59
 * STDLibMethodContainer.java created by Tsvetelin
 */

package com.cake.STDLib;

import java.util.List;

import com.cake.STDLib.methods.STDLibMethod;
import com.cake.STDLib.methods.printing.PrintLNMethod;
import com.cake.STDLib.methods.printing.PrintMethod;
import com.cake.utils.container.Container;

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
