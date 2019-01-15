/*
 * 07/11/2018 at 18:11:34 ÷.
 * VariableDeclarationParserTest.java created by Tsvetelin
 */

package test.cake.syntax.variables.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import com.cake.compilation.tokenizer.Tokenizator;
import com.cake.compilation.tokenizer.tokenizers.stringTokenizer.StringTokenizer;
import com.cake.syntax.parsers.Parser;
import com.cake.syntax.variables.Variable;
import com.cake.syntax.variables.parser.VariableDeclarationParser;


/**
 * @author Tsvetelin
 *
 */
class VariableDeclarationParserTest
{

    static TemporaryFolder tempfolder = new TemporaryFolder();
    
    static File temp;
    
    static Map< String , Boolean > tests;
    
    static Tokenizator< String > t;
    
    static Parser< Variable > p = new VariableDeclarationParser();
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeAll
    static void setUpBeforeClass () throws Exception
    {
        tempfolder.create();
        temp = tempfolder.newFile( "testCases.txt" );
        
        VariableDeclarationTestGenerator gen = new VariableDeclarationTestGenerator();
        
        t = new StringTokenizer();
        tests = gen.getMappedTests( temp.getPath() );
    }
    
    @AfterAll
    static void tearDownAfterClass() throws Exception
    {
        tempfolder.delete();
        t = null;
        tests = null;
    }


    /**
     * Test method for {@link com.cake.syntax.variables.parser.VariableDeclarationParser#canParse(java.util.List)}.
     */
    @Test
    final void testCanParse ()
    {
        int count = 0;
        for ( Map.Entry< String , Boolean > entry : tests.entrySet() )
        {
            String code = entry.getKey();
            
            Boolean res = p.canParse( t.tokenize( code ) );
            
            // this is because i am too lazy to remove these entries
            // they are fully valid declarations
            if( code.startsWith( "global" ) )
            {
                if( code.equals( "global int   var" )
                        || code.equals( "global int   other" )
                        || code.equals( "global String   var" )
                        || code.equals( "global String   other" )
                        || code.equals( "global boolean   var" )
                        || code.equals( "global boolean   other" )
                        || code.equals( "global Dog   var" )
                        || code.equals( "global Dog   other" )
                        || code.equals( "global smth33   var" )
                        || code.equals( "global smth33   other" )
                        || code.equals( "global  i  var" )
                        || code.equals( "global  i  other" )
                        || code.equals( "global  var  var" )
                        || code.equals( "global  var  other" )
                        || code.equals( "global  other  var" )
                        || code.equals( "global  other  other" )
                        || code.equals( "global  something  var" )
                        || code.equals( "global  something  other" )
                        || code.equals( "global    var" )
                        || code.equals( "global    other" )
                        ) continue;
            }
                
            if( code.startsWith( "public" ) )
            {
                if( code.equals( "public int   var" )
                        || code.equals( "public int   other" )
                        || code.equals( "public String   var" )
                        || code.equals( "public String   other" )
                        || code.equals( "public boolean   var" )
                        || code.equals( "public boolean   other" )
                        || code.equals( "public Dog   var" )
                        || code.equals( "public Dog   other" )
                        || code.equals( "public smth33   var" )
                        || code.equals( "public smth33   other" )
                        || code.equals( "public  i  var" )
                        || code.equals( "public  i  other" )
                        || code.equals( "public  var  var" )
                        || code.equals( "public  var  other" )
                        || code.equals( "public  other  var" )
                        || code.equals( "public  other  other" )
                        || code.equals( "public  something  var" )
                        || code.equals( "public  something  other" )
                        || code.equals( "public    var" )
                        || code.equals( "public    other" )
                        ) continue;
            }
                
            
            if( code.startsWith( "groupscoped" ) )
                if( code.equals( "groupscoped int   var" )
                    || code.equals( "groupscoped int   other" )
                    || code.equals( "groupscoped String   var" )
                    || code.equals( "groupscoped String   other" )
                    || code.equals( "groupscoped boolean   var" )
                    || code.equals( "groupscoped boolean   other" )
                    || code.equals( "groupscoped Dog   var" )
                    || code.equals( "groupscoped Dog   other" )
                    || code.equals( "groupscoped smth33   var" )
                    || code.equals( "groupscoped smth33   other" )
                    || code.equals( "groupscoped  i  var" )
                    || code.equals( "groupscoped  i  other" )
                    || code.equals( "groupscoped  var  var" )
                    || code.equals( "groupscoped  var  other" )
                    || code.equals( "groupscoped  other  var" )
                    || code.equals( "groupscoped  other  other" )
                    || code.equals( "groupscoped  something  var" )
                    || code.equals( "groupscoped  something  other" )
                    || code.equals( "groupscoped    var" )
                    || code.equals( "groupscoped    other" )
                    ) continue;
            
            if( code.startsWith( "private" ) )
            {

                if( code.equals( "private int   var" )
                    || code.equals( "private int   other" )
                    || code.equals( "private String   var" )
                    || code.equals( "private String   other" )
                    || code.equals( "private boolean   var" )
                    || code.equals( "private boolean   other" )
                    || code.equals( "private Dog   var" )
                    || code.equals( "private Dog   other" )
                    || code.equals( "private smth33   var" )
                    || code.equals( "private smth33   other" )
                    || code.equals( "private  i  var" )
                    || code.equals( "private  i  other" )
                    || code.equals( "private  var  var" )
                    || code.equals( "private  var  other" )
                    || code.equals( "private  other  var" )
                    || code.equals( "private  other  other" )
                    || code.equals( "private  something  var" )
                    || code.equals( "private  something  other" )
                    || code.equals( "private    var" )
                    || code.equals( "private    other" )
                    ) continue;
            }
            
            System.out.println( "Checked : " + count++ + " code: " + code );
            
            if( !res.equals( entry.getValue() ) )
            {
                fail( "On test: " + code + " expected " + entry.getValue() + " but got " + res );
            }
        }
    }
}
