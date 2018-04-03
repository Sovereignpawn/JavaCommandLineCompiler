/*
* File: CycleDetectedException.java
* Author: Claire Stovall
* Date: February 5, 2018
* Purpose: This class is a user defined checked exception for when a cycle
* is detected.
*/

// Exception class for when an invalid class name is detected
public class CycleDetectedException extends Exception {
  public CycleDetectedException() {
    System.out.println("Cycle detected!");
  }

  // toString method
  public String toString(){
   return ("Cycle detected!");
 }
}
