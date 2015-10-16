package com.example.lambda;

import java.util.List;
import java.util.function.Function;

/**
 * @author MikeW
 */
public class NameTestOld {

  public static void main(String[] args) {
    
    System.out.println("\n==== NameTestOld ===");
    
    List<Person> list1 = Person.createShortList();
    
    // Print Names Western
    System.out.println("\n===Print Western Names===");
    for (Person person:list1){
        person.printWesternName();
    }
    
    // Print Names Eastern
    System.out.println("\n===Print Eastern Names===");
    for (Person person:list1){
        person.printEasternName();
    }    
    
  }
  
}
