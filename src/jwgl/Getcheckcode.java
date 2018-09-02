package jwgl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class Getcheckcode {
	
	
      public static void main(String[] args) throws Exception {
    	  
    		  getImage();
    	  
    	 
    	  
    	  
	}
	private static void getImage() throws MalformedURLException, IOException, Exception {
		  HttpURLConnection huc= Jwgl.getCon(Jwgl.jwurl+"/"+Jwgl.code+"/CheckCode.aspx");
  		  InputStream fis = huc.getInputStream();
    	  int x;int y;
    	  BufferedImage img = ImageIO.read(fis);
    	  
    	  int h= img.getHeight();
    	  int w =img.getWidth();
    	  for( y =0;y<h;y++){
    		  for(x=0;x<w;x++){
    			 if(isblack(img.getRGB(x, y))){
    				 img.setRGB(x, y, Color.BLACK.getRGB());
    				 System.out.print(0);
    			 }else{
    				 img.setRGB(x, y, Color.WHITE.getRGB());
    				 System.out.print(1);
    			 }
    		  }
    		  System.out.println("");
    	  }
    	  /*for(int i =0;i<4;i++){
    		  
        	  ImageIO.write(splitImage(img).get(i), "gif", new File("new_CheckCode_"+String.valueOf(i)+String.valueOf(new Random().nextInt()*10000) +".gif"));
    	  }*/
    	  BufferedImage spi=  splitImage(img).get(0);
    	  ImageIO.write(spi, "gif", new File("new_CheckCode_2.gif") );
    	 
	}
      private static boolean isblack(int colorint) {
		
    	  Color color =new Color(colorint);
    	  if(color.getBlue()==153&&color.getGreen()==0&&color.getRed()==0){
    		  return true;
    	  }else{
    		  return false;
    	  }
    	
    	  
	}
      public static List<BufferedImage> splitImage(BufferedImage img)
              throws Exception {
          List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
          
          int height = img.getHeight();
          
          subImgs.add(img.getSubimage(5, 0, 12, height));
          subImgs.add(img.getSubimage(17, 0, 12, height));
          subImgs.add(img.getSubimage(29, 0, 12, height));
          subImgs.add(img.getSubimage(41, 0, 12, height));
          return subImgs;
      }
      public static Map<BufferedImage, String> loadTrainData() throws Exception {
         
              Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();

              File dir = new File("data");
              File[] files = dir.listFiles();
              
              for (File file : files) {
                  map.put(ImageIO.read(file), file.getName().charAt(0) + "");
              }
               
          
          return map;
      }
      
}
