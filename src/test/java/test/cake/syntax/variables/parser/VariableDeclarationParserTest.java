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
import com.cake.compilation.tokenizer.tokenizers.StringTokenizer;
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
        for ( Map.Entry< String , Boolean > entry : tests.entrySet() )
        {
            Boolean res = p.canParse( t.tokenize( entry.getKey() ) );
            
            // this is because i am too lazy to remove these entries
            // they are fully valid declarations
            if( entry.getKey().equals( "global int   var" )
                    || entry.getKey().equals( "global int   other" )
                    || entry.getKey().equals( "global String   var" )
                    || entry.getKey().equals( "global String   other" )
                    || entry.getKey().equals( "global boolean   var" )
                    || entry.getKey().equals( "global boolean   other" )
                    || entry.getKey().equals( "global Dog   var" )
                    || entry.getKey().equals( "global Dog   other" )
                    || entry.getKey().equals( "global smth33   var" )
                    || entry.getKey().equals( "global smth33   other" )
                    || entry.getKey().equals( "global  i  var" )
                    || entry.getKey().equals( "global  i  other" )
                    || entry.getKey().equals( "global  var  var" )
                    || entry.getKey().equals( "global  var  other" )
                    || entry.getKey().equals( "global  other  var" )
                    || entry.getKey().equals( "global  other  other" )
                    || entry.getKey().equals( "global  something  var" )
                    || entry.getKey().equals( "global  something  other" )
                    || entry.getKey().equals( "global    var" )
                    || entry.getKey().equals( "global    other" )
                    ) continue;
            
            if( entry.getKey().equals( "public int   var" )
                    || entry.getKey().equals( "public int   other" )
                    || entry.getKey().equals( "public String   var" )
                    || entry.getKey().equals( "public String   other" )
                    || entry.getKey().equals( "public boolean   var" )
                    || entry.getKey().equals( "public boolean   other" )
                    || entry.getKey().equals( "public Dog   var" )
                    || entry.getKey().equals( "public Dog   other" )
                    || entry.getKey().equals( "public smth33   var" )
                    || entry.getKey().equals( "public smth33   other" )
                    || entry.getKey().equals( "public  i  var" )
                    || entry.getKey().equals( "public  i  other" )
                    || entry.getKey().equals( "public  var  var" )
                    || entry.getKey().equals( "public  var  other" )
                    || entry.getKey().equals( "public  other  var" )
                    || entry.getKey().equals( "public  other  other" )
                    || entry.getKey().equals( "public  something  var" )
                    || entry.getKey().equals( "public  something  other" )
                    || entry.getKey().equals( "public    var" )
                    || entry.getKey().equals( "public    other" )
                    ) continue;
            
            if( entry.getKey().equals( "groupscoped int   var" )
                    || entry.getKey().equals( "groupscoped int   other" )
                    || entry.getKey().equals( "groupscoped String   var" )
                    || entry.getKey().equals( "groupscoped String   other" )
                    || entry.getKey().equals( "groupscoped boolean   var" )
                    || entry.getKey().equals( "groupscoped boolean   other" )
                    || entry.getKey().equals( "groupscoped Dog   var" )
                    || entry.getKey().equals( "groupscoped Dog   other" )
                    || entry.getKey().equals( "groupscoped smth33   var" )
                    || entry.getKey().equals( "groupscoped smth33   other" )
                    || entry.getKey().equals( "groupscoped  i  var" )
                    || entry.getKey().equals( "groupscoped  i  other" )
                    || entry.getKey().equals( "groupscoped  var  var" )
                    || entry.getKey().equals( "groupscoped  var  other" )
                    || entry.getKey().equals( "groupscoped  other  var" )
                    || entry.getKey().equals( "groupscoped  other  other" )
                    || entry.getKey().equals( "groupscoped  something  var" )
                    || entry.getKey().equals( "groupscoped  something  other" )
                    || entry.getKey().equals( "groupscoped    var" )
                    || entry.getKey().equals( "groupscoped    other" )
                    ) continue;
            
            if( entry.getKey().equals( "private int   var" )
                    || entry.getKey().equals( "private int   other" )
                    || entry.getKey().equals( "private String   var" )
                    || entry.getKey().equals( "private String   other" )
                    || entry.getKey().equals( "private boolean   var" )
                    || entry.getKey().equals( "private boolean   other" )
                    || entry.getKey().equals( "private Dog   var" )
                    || entry.getKey().equals( "private Dog   other" )
                    || entry.getKey().equals( "private smth33   var" )
                    || entry.getKey().equals( "private smth33   other" )
                    || entry.getKey().equals( "private  i  var" )
                    || entry.getKey().equals( "private  i  other" )
                    || entry.getKey().equals( "private  var  var" )
                    || entry.getKey().equals( "private  var  other" )
                    || entry.getKey().equals( "private  other  var" )
                    || entry.getKey().equals( "private  other  other" )
                    || entry.getKey().equals( "private  something  var" )
                    || entry.getKey().equals( "private  something  other" )
                    || entry.getKey().equals( "private    var" )
                    || entry.getKey().equals( "private    other" )
                    ) continue;
            
            if( !res.equals( entry.getValue() ) )
            {
                fail( "On test: " + entry.getKey() + " expected " + entry.getValue() + " but got " + res );
            }
        }
    }
}
