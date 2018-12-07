package com.cake.utils.fileManagement.creation;

import java.io.File;
import java.io.IOException;

public class Creator  implements IFileCreator
{

	@Override
	public File createDirectory(File dir) 
	{
		if(!dir.exists())  dir.mkdirs();
		return dir;
	}

	@Override
	public File createDirectory(String directoryPath) 
	{
		return createDirectory( new File( directoryPath ) );
	}

	@Override
	public File createFile(File file) throws IOException 
	{
	    createDirectory( file.getParent() );
	    
		if( !file.exists() ) { file.createNewFile(); }
		
		return file;
		
	}

    @Override
	public File createFile(String path) throws IOException 
	{
		return createFile( new File( path ) );
	}
    
}
