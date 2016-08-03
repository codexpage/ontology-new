package file;

import java.io.*; 
import java.util.*; 

public class FileOperator { 
	
	
	//
	public FileOperator() { 
	} 

	/** 
	 * 
	 * 
	 * @param folderPath String
	 * @return void 
	 */ 
	public void createFolder(String folderPath) { 
		try { 
			String filePath = folderPath.toString(); 
			File myFilePath = new File(filePath); 
			//
			if (!myFilePath.exists()) { 
				myFilePath.mkdir(); 
			}
			else{
				System.out.println("The fold has been existed!");
			}
		} catch (Exception e) { 
			System.out.println("Error: new path :  " + folderPath); 
			e.printStackTrace(); 
		} 
	} 

	/** 
	 * 
	 * 
	 * @param filePathAndName 
	 * String 
	 * @param fileContent 
	 * String 
	 * @return void 
	 */ 
	public void cretaeFile(String filePathAndName, String fileContent) { 
		try { 
			String fName = filePathAndName.toString(); 
			File myFile = new File(fName); 
			if (!myFile.exists()) { 
				myFile.createNewFile(); 
			} 
			FileWriter fWriter = new FileWriter(myFile); 
			PrintWriter pWriter = new PrintWriter(fWriter); 
			String fContent = fileContent; 
			pWriter.println(fContent);
			fWriter.close(); 
			pWriter.close();
		} catch (Exception e) { 
			System.out.println("Error: new file: " + filePathAndName); 
			e.printStackTrace(); 

		} 
	}
	
	/**
     * 
     * @param oldPath String 
     * @param newPath String 
     * @return void
     */
	public void copyFolder(String oldPath, String newPath) {  
		try {
			//
			(new File(newPath)).mkdirs();
			
	        File file = new File(oldPath);
	        String[] files = file.list();
	        File temp = null;
	        for (int i = 0; i < files.length; i++) {
	        	//File.separator
	        	if(oldPath.endsWith(File.separator)){
	        		temp=new File(oldPath+files[i]);
	        	}
	        	else{
	        		temp=new File(oldPath+File.separator+files[i]);
	        	}
	        	//
	        	if(temp.isFile()){
	        		FileInputStream input = new FileInputStream(temp);
	        		FileOutputStream output = new FileOutputStream(newPath + File.separator + (temp.getName()).toString());
	        		byte[] b = new byte[1024 * 5];
	        		int len;
	        		while ( (len = input.read(b)) != -1) {
	        			output.write(b, 0, len);
	        		}
	        		output.flush();
	        		output.close();
	        		input.close();
	        	}
	        	if(temp.isDirectory()){//
	        		copyFolder(oldPath+File.separator+files[i], newPath+File.separator+files[i]);
	        	}
	        }
	    }
		catch (Exception e) {
	        System.out.println("Error:  copy " + oldPath + " to " + newPath);
	        e.printStackTrace();
		}
	}
	
	/**
     * 
     * @param filePathAndName String
     * @return boolean
     */
    public void delFile(String filePathAndName) {
    	try {
    		String filePath = filePathAndName.toString();
    		File myDelFile = new File(filePath);
    		myDelFile.delete();
    	}
    	catch (Exception e) { 
    		System.out.println("Error: delete file:  " + filePathAndName );
    		e.printStackTrace();
    	}
    }
	
	/**
     * 
     * @param path String 
     */
    public void delAllFile(String path) {
    	File file = new File(path);
    	if (!file.exists()) {
    		return;
    	}
    	if (!file.isDirectory()) {
    		return;
    	}
    	String[] files = file.list();
    	File temp = null;
    	for (int i = 0; i < files.length; i++) {
    		if (path.endsWith(File.separator)) {
    			temp = new File(path + files[i]);
    		}
    		else {
    			temp = new File(path + File.separator + files[i]);
    		}
    		if (temp.isFile()) {
    			temp.delete();
    		}
    		if (temp.isDirectory()) {
    			//
    			delAllFile(path+File.separator+ files[i]);
    			delFolder(path+File.separator+ files[i]);
    		}
    	}
    }


	/**
     * 
     * @param filePathAndName String 
     * @param fileContent String
     * @return boolean
     */
    public void delFolder(String folderPath) {
    	try {
    		//
    		delAllFile(folderPath); 
    		String filePath = folderPath.toString();
    		File myFilePath = new File(filePath);
    		//
    		myFilePath.delete();
      }
      catch (Exception e) {
        System.out.println("Error: delete folder:  "+ folderPath);
        e.printStackTrace();
 
      }
 
    }
    
	/**
     * 
     * @param oldPath String 
     * @param newPath String 
     */
    public void moveFolder(String oldPath, String newPath) {
      copyFolder(oldPath, newPath);
      delAllFile(oldPath); 
    }
    
  
    //
    public boolean isFileExisted(String filePathAndName){
    	boolean isExisted = false;
    	try{
    		File file = new File(filePathAndName);
    		if(file.exists()){
    			isExisted = true;
    		}   	
    	}catch(Exception e) {
            System.out.println("Error: read the file" + filePathAndName);
            e.printStackTrace();
     
        }
    	return isExisted;
    }
    
    public static void writeFile(String filePathAndName, String content) {
        try {
            //
            FileWriter writer = new FileWriter(filePathAndName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
} 


