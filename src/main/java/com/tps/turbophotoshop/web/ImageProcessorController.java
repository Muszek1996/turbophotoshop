package com.tps.turbophotoshop.web;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageProcessorController {
    public static Map<String,BufferedImage> image = new HashMap<>();



    static public String setImage(InputStream inputStream) {
        String id;
        try {
            BufferedImage binaryImage = ImageIO.read(inputStream);
            if(isImage(binaryImage)){
                id = IdFactory.getId();
                image.put(id,binaryImage);
            }else{
                return "Image file corrupted";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Image processing error";
        }
        return id;
    }

    static public byte[] cropImage(String id,int start,int stop,int width,int height) throws IOException{
        byte[] imageBytes = new byte[]{};
        BufferedImage bufferedImage = image.get(id);
            if(start>bufferedImage.getWidth()||stop>bufferedImage.getHeight()||width>bufferedImage.getWidth()||height>bufferedImage.getHeight()){
                throw new IOException("Podany punkt znajduje sie poza obrazem");
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage croppedImage = bufferedImage.getSubimage(start,stop,width,height);
            ImageIO.write(croppedImage,"png",byteArrayOutputStream);
            imageBytes = byteArrayOutputStream.toByteArray();
        return imageBytes;
    }

    static public byte[] getImage(String id){
        byte[] imageBytes = new byte[]{};
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image.get(id),"png",byteArrayOutputStream);
            imageBytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

    static private boolean isImage(BufferedImage bufferedImage){
        Image image = bufferedImage;
         return (image != null);
    }

    static public boolean imageExist(String id){
        if(image.containsKey(id))return true;
        return false;
    }

    static public void removeImage(String id){
         image.remove(id);
    }

    static public int[] getHistogram(String id){
        int[] histogram = new int[256];
        for(int i =0;i<256;i++){
            histogram[i]=0;
        }
        BufferedImage bufferedImage = image.get(id);
        for(int i=0;i<bufferedImage.getHeight();i++){
            for(int j=0;j<bufferedImage.getWidth();j++){
                int val = bufferedImage.getRGB(j,i);
                Color color = new Color(val);
                ++histogram[convertToGrayScale(color)];
            }
        }

        return histogram;
    }

    static private int convertToGrayScale(Color color){
        double val = 0.21*color.getRed() + 0.72*color.getGreen() + 0.07*color.getBlue();
        return (int)val;
    }


    static public ImageDimensions getImageDimensions(String id){
            BufferedImage binaryImage = image.get(id);
            int width,height;
            width = binaryImage.getWidth();
            height = binaryImage.getHeight();
            ImageDimensions imageDimensions = new ImageDimensions(width,height);
            return imageDimensions;
        }
}
