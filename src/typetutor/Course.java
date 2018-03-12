/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typetutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Course implements Serializable
{

    String text, inst;
    int serial;
    int wpm;//local wpm
    int accuracy;//local accuracy

    Course(String filename, String instruction, int serial)
    {
        StringBuffer str = new StringBuffer("");
        File file = new File(filename);

        try
        {
            //
            // Create a new Scanner object which will read the data
            // from the file passed in. To check if there are more
            // line to read from it we check by calling the
            // scanner.hasNextLine() method. We then read line one
            // by one till all line is read.
            //
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                //System.out.println(line);
                str.append(line);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        text = str.toString();
        StringBuffer str2 = new StringBuffer("");
        File file2 = new File(instruction);

        try
        {
            //
            // Create a new Scanner object which will read the data
            // from the file passed in. To check if there are more
            // line to read from it we check by calling the
            // scanner.hasNextLine() method. We then read line one
            // by one till all line is read.
            //
            Scanner scanner = new Scanner(file2);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                //System.out.println(line);
                str2.append(line);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        inst = str2.toString();
        this.serial = serial;

    }

    public String getInst()
    {
        return inst;
    }

    public String getText()
    {
        return text;
    }

    public int getSerial()
    {
        return serial;
    }

    public int getWpm()
    {
        return wpm;
    }

    public int getAccuracy()
    {
        return accuracy;
    }

    /*static String fileNameCreate(int i, boolean isText)//course panel e filename corresponding integer+true or 
     //false diye call korlei create hoye jabe..etai course er constructor e pathay dibo
     {//file er name ta path diye ekdom ready kore dite paro..edom sobar last e
     if (isText)
     {
     return "Course"+i + ".txt";
     } else
     {
     return "Course"+i + "inst" + ".txt";
     }
     }*/
    public void calculateWPMandLTS(int totalTypedChars, int correctTypedChars, long elapsedTimeInMilli)
    {//event handling e total+correct er counter tahte hobe jeta restart marle 0 kore dite hobe
        float elapsedSeconds = (float) (elapsedTimeInMilli * .001);
        System.out.println(elapsedSeconds);

        int numberOfWords = totalTypedChars / 5;
        float temp = (numberOfWords / (elapsedSeconds / 60));
        wpm=(int)temp;
        
        temp = ((float)correctTypedChars / (float)totalTypedChars) * 100;
        System.out.println("temp "+temp+"total"+totalTypedChars+"corrent"+correctTypedChars);
        accuracy=(int)temp;
        
        System.out.println("wpm " + wpm + "accuracy " + accuracy);

    }

}
