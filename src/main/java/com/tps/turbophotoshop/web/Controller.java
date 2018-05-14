package com.tps.turbophotoshop.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;


@RestController
public class Controller {

    @RequestMapping(value = "/image/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addImage(HttpServletRequest requestEntity) throws Exception {
        String imageId = ImageProcessorController.setImage(requestEntity.getInputStream());
        return new ResponseEntity<Object>( Collections.singletonMap("Id",imageId),HttpStatus.OK);
    }

    @RequestMapping(value = "/image/{id}/size", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDimension(@PathVariable("id")String id) throws Exception {
        if(ImageProcessorController.imageExist(id)){
            ImageDimensions imageDimensions = ImageProcessorController.getImageDimensions(id);
            return new ResponseEntity<Object>(imageDimensions,HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Image not found",HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeImage(@PathVariable("id")String id) throws Exception {
        if(ImageProcessorController.imageExist(id)){
            ImageProcessorController.removeImage(id);
            return new ResponseEntity<Object>("Image with id:"+id+" deleted",HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Image not found",HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/image/{id}/histogram", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> makeHistogram(@PathVariable("id")String id) throws Exception {
        if(ImageProcessorController.imageExist(id)){
            return new ResponseEntity<Object>(ImageProcessorController.getHistogram(id),HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Image not found",HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/image/{id}/crop", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Object> getCroppedImage(@PathVariable("id")String id,@RequestParam Integer start,@RequestParam Integer stop, @RequestParam Integer width, @RequestParam Integer height) throws Exception {
    if(ImageProcessorController.imageExist(id)){
            try {

                return new ResponseEntity<Object>(ImageProcessorController.cropImage(id, start, stop, width, height), HttpStatus.OK);
            }catch(IOException e){
                return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Wrong params");
            }
        }
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Image not exist");
    }



}