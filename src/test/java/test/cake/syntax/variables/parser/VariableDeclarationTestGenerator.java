/*
 * 07/11/2018 at 18:30:22 ÷.
 * TestGenerator.java created by Tsvetelin
 */

package test.cake.syntax.variables.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Tsvetelin
 *
 */
public class VariableDeclarationTestGenerator
{

    /**
     * @param args
     * @throws IOException 
     */
    private void generateTestFile ( String path ) throws IOException
    {
        File test = new File( path );
        BufferedWriter writer = new BufferedWriter( new FileWriter( test ) );
        
        String[] accessOptions = new String[] { "global", "public", "groupscoped", "private", "" };
        String[] typeOptions = new String[] { "int", "String", "boolean", "Dog", "931", "smth33" , "" };
        String[] nameOptions = new String[] { "i", "var", "something", "" };
        String[] operatorOptions = new String[] { "=", ">", "+", "" };
        String[] assigningOptions = new String[] { "5", "5.0", "var", "other", "+", "\"testString\"", "" };
        
        for(String access : accessOptions)
        {
            for(String type : typeOptions)
            {
                for(String name : nameOptions)
                {
                    for(String operator : operatorOptions)
                    {
                        for(String assigning : assigningOptions)
                        {
                            Boolean isCorrect = false;
                            if(!access.equals( "" )
                                    &&
                                    !type.equals( "931" )
                                    &&
                                    (    
                                            !name.equals( "" )
                                            ||
                                            !type.equals( "" )
                                    )
                                    &&
                                    (
                                            ( 
                                                    operator.equals( "" ) 
                                                    && 
                                                    assigning.equals( "" ) 
                                            ) 
                                            ||
                                            ( 
                                                    operator.equals( "=" )
                                                    && 
                                                    (
                                                            !assigning.equals( "+" )
                                                            && 
                                                            !assigning.equals( "" ) 
                                                    )
                                            )
                                    )
                              )
                            {
                                isCorrect = true;
                            }
                            writer.write( String.format( "%s %s %s %s %s" , access, type, name, operator, assigning ) );
                            writer.write( String.format( ":%b" , isCorrect ) );
                            writer.write( System.lineSeparator() );
                        }
                    }
                }
            }
        }
        writer.close();
    }
    
    public Map< String , Boolean > getMappedTests ( String path ) throws IOException
    {
        Map< String , Boolean > res = new LinkedHashMap<>();
        
        generateTestFile( path );
        
        BufferedReader reader = new BufferedReader( new FileReader( new File( path ) ) );
        
        reader.lines().forEach( x -> 
        {
            String[] segments = x.split( ":" );
            String testCase = segments[0];
            Boolean result = Boolean.parseBoolean( segments[1] );
            res.put( testCase , result );
        });
        reader.close();
        
        return res;
        
    }
}
