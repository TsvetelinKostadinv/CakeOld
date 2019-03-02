/*
 * 09/01/2019 09:58:23
 * FileTokenizer.java created by Tsvetelin
 */

package com.cake.tokenization.tokenizer.tokenizers.fileTokenizer;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.cake.interpreter.utils.fileManagement.reading.IFileReader;
import com.cake.interpreter.utils.fileManagement.reading.Reader;
import com.cake.tokenization.tokenizer.Tokenizator;
import com.cake.tokenization.tokenizer.tokenizers.stringTokenizer.StringTokenizer;
import com.cake.tokenization.tokens.Token;


/**
 * @author Tsvetelin
 *
 */
public class FileTokenizer implements Tokenizator< File >
{

    private IFileReader reader = new Reader();

    private Tokenizator< String > tokenizer = /*new TempTokenizer()*/ new StringTokenizer();


    /*
     * (non-Javadoc)
     * 
     * @see com.cake.compilation.tokenizer.Tokenizator#tokenize(java.lang.Object)
     */
    @Override
    public List< Token > tokenize ( File source )
    {
        source = Objects.requireNonNull( source );
        try
        {
            return tokenizer.tokenize( reader.readFile( source ).replaceAll( "\r\n" , "" ) );
        } catch ( IOException e )
        {
            System.out.println( "Cannot access file!" );
        }
        return null;
    }
//    
//    public static void main ( String [] args ) throws IOException
//    {
//        CakeRuntime runtime = Runner.getNewProjectRuntime();
//        String path = "C:\\Users\\Tsvetelin\\Desktop\\variables_declaration.cake";
//        
//        File source = new File( path );
//        
//        IFileReader reader = new Reader();
//        
//        System.out.println( "Program: " + reader.readFile( path ) );
//        
//        List< Token > tokens = new FileTokenizer().tokenize( source );
//            
//        System.out.println( "Tokens after parsing:" + tokens );
//        Pair< String , ? extends SyntaxElement > pair = ParsersContainer.INSTANCE
//                .getParserFor( tokens ).get( 0 )
//                .parseWithRuntime( runtime , null , tokens );
//
//        runtime.addDecalredElement( pair.getKey() , pair.getValue() );
//
//        Result res = ( (RunnableSyntaxElement) pair.getValue() ).run( runtime );
//
//        System.out.println( "Result:" + res );
//        
//    }

}
