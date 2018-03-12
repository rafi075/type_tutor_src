package typetutor;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
   
/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can use the static variable SoundEffect.volume to mute the sound.
 */
public enum Sound {
   CLAP("clap.wav"),
   Wall("wall_broken.wav"),
   crash("crash.wav"),
   BOXINTRO("box intro.wav"),
   MAININTRO("main intro.wav"),
   FIRE("fire.wav"),
   THEEND("theend.wav"),
   ERROR("error.wav"),
   DRAWINTRO("draw intro.wav"),
   T1("1.wav"),
   T2("2.wav"),
   T3("3.wav"),
   T4("4.wav"),
   T5("5.wav"),
   T6("6.wav"),
   T7("7.wav"),
   T8("8.wav"),
   T9("9.wav"),// bullet
   CLICK("Click.wav"),
   ABOUT("about.wav");
   
   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static Volume volume = Volume.LOW;
   
   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;
   
   // Constructor to construct each element of the enum with its own sound file.
   Sound(String soundFileName) {
      try {
         // Use URL (instead of File) to read from disk and JAR.
         File soundFile = new File(soundFileName);
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start();     // Start playing
      }
   }
   
   // Optional static method to pre-load all the sound files.
   static void init() {
      values(); // calls the constructor for all the elements
   }
}/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */