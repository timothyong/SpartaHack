
import java.io.File;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;




public class Picture {
	
	public String UserTag;  
	
	
	String Base64String =" ";
	
	private int FileLength = 0;
	
	
	public Picture(User u, String p){
		
		UserTag = u.getUserName();
		
		File file = new File(p);
		FileLength = (int) file.length();
		byte [] ImageByteData = new byte[FileLength];
		try{
		FileInputStream imageInFile = new FileInputStream(file);
        imageInFile.read(ImageByteData);
        imageInFile.close();
		}
		catch(IOException ioe){
			
		}
		
		Base64String = getBase64String(ImageByteData);
		
		
			
		
		
	}
	
	public Map<String,String> getmap(){
		Map<String,String> ret = new HashMap<String,String>();
		ret.put("Username", "kkkk");
		ret.put("Base64String", Base64String);
		ret.put("Filelength",Integer.toString(FileLength));
		return ret;
	}

	public String getBase64String(byte[] imageByteArray){
		return Base64.encodeBase64URLSafeString(imageByteArray);

	}
	
	public byte[] getByteArray(String imageDataString) {
		
        return Base64.decodeBase64(imageDataString);
    }
	
	public void DisplayImage(){
		
		byte [] ImageByteData = new byte[FileLength];
		
		ImageByteData = getByteArray(Base64String);
		
		
		
		BufferedImage image;
		image = byteArrayToImage(ImageByteData);
		JFrame f = new JFrame("Server");
	      ImageIcon icon = new ImageIcon(image);
	      JLabel l = new JLabel();
	      
	      l.setIcon(icon);
	      f.add(l);
	      f.pack();
	      f.setVisible(true);
	      
	      
		
	}
	 public  BufferedImage  byteArrayToImage(byte[] bytes){  
	        BufferedImage bufferedImage=null;
	        try {
	            InputStream inputStream = new ByteArrayInputStream(bytes);
	            bufferedImage = ImageIO.read(inputStream);
	        } catch (IOException ex) {
	            System.out.println(ex.getMessage());
	        }
	        return bufferedImage;
	}
	
}
