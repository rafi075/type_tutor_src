/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typetutor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author User
 */
public class User implements Serializable
{

    int id = 0;
    String name;
    Course lastrunning;
    long totalWPM = 0, totalACCURACY = 0;
    int WPM = 0, ACCURACY = 0; //average of an user
    int lessonCompleted;
    int UserboxScore = 0, drawScore = 0;

    public int getUserboxScore()
    {
        return UserboxScore;
    }

    public void setUserboxScore(int UserboxScore)
    {
        this.UserboxScore = UserboxScore;
    }

    public int getDrawScore()
    {
        return drawScore;
    }

    public void setDrawScore(int drawScore)
    {
        this.drawScore = drawScore;
    }

    public User(String name, Course lastrunning, int WPM, int ACCURACY, int id)
    {
        this.name = name;
        this.lastrunning = lastrunning;//last running course na thakle null dilei hobe
        this.WPM = WPM;//last running course null kina check koro..null hole course 1 chalao
        //nahole lastrunning niye oitar getserial diye oi file ta read kore or
        lessonCompleted = 0;//oitar string ta direct poro
        this.ACCURACY = ACCURACY;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    static void createNewFileForUserOrUpdate(User a)
    {

        try
        {
            String b=""+a.getId();
            FileOutputStream fos = new FileOutputStream(b);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(a);
            
            oos.flush();
            oos.close();
        } catch (IOException e)
        {
            System.out.println("Exception during serialization: " + e);
            System.exit(0);
        }
    }

    static void createNewFileForUserOrUpdate(String a)
    {
        Course test = new Course("1.txt", "1inst.txt", 1);
        User temp = new User(a, test, 1, 2,1);
        try
        {
            FileOutputStream fos = new FileOutputStream(temp.getName());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(temp);
            oos.flush();
            oos.close();
        } catch (IOException e)
        {
            System.out.println("Exception during serialization: " + e);
            System.exit(0);
        }
    }
    
    static void createNewFileForUserOrUpdate(int a)//integer niye oita name hisebe dei+oitar name ei file banay
    {
        Course test = new Course("1.txt", "1inst.txt", 1);
        User temp = new User(""+a, test, 0, 0,a);
        try
        {
            FileOutputStream fos = new FileOutputStream(""+a);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(temp);
            oos.flush();
            oos.close();
        } catch (IOException e)
        {
            System.out.println("Exception during serialization: " + e);
            System.exit(0);
        }
    }

    static User readUserFile(String a)//combo box theke int return korbo so eita lagbe na
    {
        User userObject;
        try
        {
            FileInputStream fis = new FileInputStream(a);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("got");
            userObject = (User) ois.readObject();
            System.out.println("got2");
            ois.close();
            return userObject;

        } catch (Exception e)
        {
            System.out.println("Exception during deserialization: " + e);
            System.exit(0);
        }
        return null;//file na paile null return nahole userobject e return krbe

    }
    
    static User readUserFile(int a)//combo box theke int return korbo..int theke file er name bujha jabe
    {
        User userObject;
        try
        {
            FileInputStream fis = new FileInputStream(""+a);
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("got");
            userObject = (User) ois.readObject();
            System.out.println("got2");
            ois.close();
            return userObject;

        } catch (Exception e)
        {
            System.out.println("Exception during deserialization: " + e);
            System.exit(0);
        }
        return null;//file na paile null return nahole userobject e return krbe

    }

    @Override
    public String toString()
    {
        return "User{" + "name=" + name + ", lastrunning=" + lastrunning + ", WPM=" + WPM + ", LPS=" + ", lessonCompleted=" + lessonCompleted + '}';
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLastrunning(Course lastrunning)
    {
        this.lastrunning = lastrunning;
    }

    public void setWPM(int WPM)
    {
        this.WPM = WPM;
    }

    public void setACCURACY(int ACCURACY)
    {
        this.ACCURACY = ACCURACY;
    }

    public void setLessonCompleted(int lessonCompleted)
    {
        this.lessonCompleted = lessonCompleted;
    }

    public void avg()
    {
        WPM = (int) (totalWPM / lessonCompleted);
        ACCURACY = (int) (totalACCURACY / lessonCompleted);
    }

}
