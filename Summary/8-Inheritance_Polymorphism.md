# Inheritance

## **extends** keyword

```java
class Shape {
  // Shape class members
}

class Triangle extends Shape {
  // additional Triangle class members
}
```

- A child class inherits its parent’s fields and methods.

## **super** keyword

- The super() method which acts like the parent constructor inside the child class constructor:

```java
class Triangle extends Shape {
  Triangle() {
    super(3); // (behaving as Shape(3))
  }
}
```

## **protected** keyword

### Child access

Cannot access private and fields with no modifier:
![Modifiers](./img/modifiers.png)

- **protected** keyword will be the equivalent to "private" in this context:

```java
class Shape {
  protected double perimeter;
}
```

## **final** keyword

It prevent anyone from modify this method in a child class:

```java
public class Noodle {
  public final boolean isTasty() {
    return true;
  }
}
```

# Polymorphism

We can use a child class instance where a parent class instance is required.

```java
String makeJuice(Fruit fruit) {
  return "Apple juice and " + fruit.squeeze();
}

// inside main()
Orange orange = new Orange();
System.out.println(juicer.makeJuice(orange));
    // We passed a Orange object instead a Fruit
```

## **Overriding**

If we want to override a method in a chil we can just redefing:

```java
class BankAccount {
  protected double balance;
  public BankAccount(double balanceIn){
    balance = balanceIn;
  }

  public void printBalance() {
    System.out.println("Your account balance is $" + balance);
  }
}

class CheckingAccount extends BankAccount {

  public CheckingAccount(double balance) {
    super(balance);
  }

  @Override
  public void printBalance() { //<-<-<-<-
    System.out.println("Your checking account balance is $" + balance);
  }
}
```

    @Override keyword informs the compiler that we want to override a method in the parent class. If the method doesn’t exist in the parent class, we’ll get a helpful error when we compile the program.

We may find ourselves in a unique situation where we want to use the superclass method instead of the subclass’ overridden method.
-> **super keyword again**:

```java
class CheckingAccount extends BankAccount {
  public CheckingAccount(double balance) {
    super(balance);
  }

  @Override
  public void printBalance() {
    System.out.println("Your checking account balance is $" + balance);
  }

  public void checkBalances() {
    printBalance(); // calls method from CheckingAccount
    super.printBalance(); // calls method from BankAccount
// equivalent BankAccount.printBalance();
  }
}
```

## Using a Child Class as its Parent Class

1. We can instantiate a child class object as a member of the parent class (CheckingAccount is child of BankAccount)

```java
BankAccount kaylasAccount = new CheckingAccount(600.00);
```

**BUT IT'S BAD:** What if CheckingAccount has a method transferToSavings() that BankAccount does not have? Can kaylasAccount still use that method?

Well, no. The compiler believes that kaylasAccount is just a BankAccount that doesn’t have some fancy child class transferToSavings() method, so it would throw an error. _But the overrides of the methods will be considered by the way_.

## Child Classes in Arrays and ArrayLists

```java
Monster dracula, wolfman, zombie1;

dracula = new Vampire();
wolfman = new Werewolf();
zombie1 = new Zombie();

Monster[] monsters = {dracula, wolfman, zombie1};

for (Monster monster : monsters) {
  monster.attack();
}
```

## **Review**

- A Java class can inherit fields and methods from another class.
- Each Java class requires its own file, but only one class in a Java package needs a main() method.
- Child classes inherit the parent constructor by default, but it’s possible to modify the constructor using super() or override it completely.
- You can use protected and final to control child class access to parent class members.
- Java’s OOP principle of polymorphism means you can use a child class object like a member of its parent class, but also give it its own traits.
- You can override parent class methods in the child class, ideally using the @Override keyword.
- It’s possible to use objects of different classes that share a parent class together in an array or ArrayList.

```java

```
