package com.cake.utils.fileManagement.deletion;

import java.io.File;

public class Deletor implements IFileDeletor
{
    @Override
    public void delete(File file) 
    {
        if( file.exists() )  file.delete();
        
    }

    @Override
    public void delete(String path) 
    {
        delete( new File(path) );
    }
}
