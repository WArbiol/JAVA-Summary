# Static methods

Static methods are methods that **belong to an entire class**, n**ot a specific object** of the class. Static methods are called using the class name and the . operator. We’ve seen a couple of static methods already!

```java
double randomNumber = Math.random();
// Stores a random decimal between 0.0 and 1.0 in randomNumber

double number = Double.valueOf("2.5");
// Transforms the String "2.5" into a double
```

- _But it can be called from a object too!_

## Static Variables

```java
public class ATM{
  // Static variables
  public static int totalMoney = 0;
  public static int numATMs = 0;

  // Instance variables
  public int money;

  public ATM(int inputMoney){
    this.money = inputMoney;

    // Steps 1 and 2: Edit numATMs and total money here
    ATM.totalMoney+=inputMoney;
    ATM.numATMs+=1;
  }
```

## Review

- Static methods and variables are associated with the class as a whole, not objects of the class.
- Static methods and variables are declared as static by using the static keyword upon declaration.
- Static methods cannot interact with non-static instance variables. This is due to static methods not having a this reference.
- Both static methods and non-static methods can interact with static variables.
