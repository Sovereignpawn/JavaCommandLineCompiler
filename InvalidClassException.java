/*
* File: InvalidClassException.java
* Author: Claire Stovall
* Date: February 5, 2018
* Purpose: This class is a user defined checked exception for invalid
* class names.
*/

// Exception class for when an invalid class name is detected
public class InvalidClassException extends Exception {
  public InvalidClassException() {
    System.out.println("Invalid class!");
  }

  // toString method
  public String toString(){
   return ("Invalid class name!");
 }
}
