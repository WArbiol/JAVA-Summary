# ArrayList

Need to import:

```java
import java.util.ArrayList;
```

### Creating

```java
ArrayList<String> toDoList = new ArrayList<String>();
```

### Observation

```java
// This code won't compile:
ArrayList<int> ages;

// This code will compile:
ArrayList<Integer> ages;
```

We use angle brackets < and > to declare the type of the ArrayList. These symbols are used for **generics**. Generics are a Java construct that allows us to define classes and objects as parameters of an ArrayList. For this reason, we can’t use primitive types in an ArrayList.

The **<Integer>** generic has to be used in an ArrayList instead. You can also use **<Double>** and **<Character>** for types you would normally declare as doubles or chars.

### With different types

```java
ArrayList assortment = new ArrayList<>();
assortment.add("Hello"); // String
assortment.add(12); // Integer
assortment.add(ferrari); // reference to Car
// assortment holds ["Hello", 12, ferrari]
```

## Some Basics

**Get size**: Instead o `.length` of an Array it is `.size()`

**Accessing an Index**: Instead o `[1]` of an Array it is `.get(1)`

**To change a Value**: Instead o `array[0] = "bla"` of an Array it is `arrayList.set(0, "blew");`

**Removing an Item**: `arrayList.remove(3);`

**Getting an Item's Index**: `arrayList.indexOf("blew");`
