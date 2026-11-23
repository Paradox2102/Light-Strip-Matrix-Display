// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.mechanisms;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.wpilib.command3.Mechanism;
import org.wpilib.hardware.led.AddressableLED;
import org.wpilib.hardware.led.AddressableLEDBuffer;
import org.wpilib.hardware.led.LEDPattern;
import org.wpilib.system.Timer;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class LightSubsystem implements Mechanism{
  /** Creates a new LightSubsystem. */
  private AddressableLED m_led = new AddressableLED(0);
  public AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(880);
  private static final File k_imagePath = new File("src\\main\\java\\first\\robot\\constants\\images");

  private int[][][][] imageArray;

  int imageIndex = 0;

  private final Timer m_frameSwitchTimer = new Timer();
  private final double frameTime = 0.25;

  public LightSubsystem() {
    m_frameSwitchTimer.start();
    m_led.setLength(880);
    // m_led.start();

    imageArray = getAllImages(k_imagePath);
    System.out.println("Done");

    ArrayList<Color> totalArray = new ArrayList<Color>();
    for (int i = 0; i < imageArray[0].length; i++) {
        for (int j = 0; j < imageArray[0][i].length; j++) {
            totalArray.add(new Color(imageArray[0][i][j][0], imageArray[0][i][j][1], imageArray[0][i][j][2]));
        }
    }
    for (int i = 0; i < totalArray.size(); i++) {
        m_ledBuffer.setRGB(i, totalArray.get(i).getRed(), totalArray.get(i).getGreen(), totalArray.get(i).getBlue());
    }
    m_led.setData(m_ledBuffer);

    m_led.setStart(0);


  }

  public void periodic() {

    if (m_frameSwitchTimer.get() > frameTime){
        m_frameSwitchTimer.reset();
    

    ArrayList<Color> totalArray = new ArrayList<Color>();
    for (int i = 0; i < imageArray[imageIndex].length; i++) {
        for (int j = 0; j < imageArray[imageIndex][i].length; j++) {
            totalArray.add(new Color(imageArray[imageIndex][i][j][0], imageArray[imageIndex][i][j][1], imageArray[imageIndex][i][j][2]));
        }
    }
    for (int i = 0; i < totalArray.size(); i++) {
        m_ledBuffer.setRGB(i, totalArray.get(i).getRed(), totalArray.get(i).getGreen(), totalArray.get(i).getBlue());
    }
    
    m_led.setData(m_ledBuffer);

    m_led.setStart(0);
    
    imageIndex ++;
    // if (imageIndex >= k_imagePath.listFiles().length){
    //     imageIndex = 0;
    // }
}
  }

  public static int[][][][] getAllImages(File path){
        
        var images = getImages(path);

        int[][][][] imageArray = new int[images.length][images[0].getHeight()][images[0].getWidth()][3];
        
        for (int i = 0; i < images.length; i++) {
            imageArray[i] = getImageData(images[i]);
        }

        return imageArray;
    }

  public static BufferedImage[] getImages(File path){
        BufferedImage[] array = new BufferedImage[k_imagePath.listFiles().length];

        for (int i = 0; i < array.length; i++) {
            try {
                array[i] = ImageIO.read(path.listFiles()[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return array;
    }

    public static int[][][] getImageData(BufferedImage image){
        int[][][] imageColorData = new int[image.getHeight()][image.getWidth()][3];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                int color = image.getRGB(x, y);

                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = (color & 0x000000ff);
          
                imageColorData[y][x][0] = red;
                imageColorData[y][x][1] = green;
                imageColorData[y][x][2] = blue;
            }
        }

        return imageColorData;
    }
 
}
