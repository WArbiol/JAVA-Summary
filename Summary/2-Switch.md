# Switch example method:

```java
public double calculateShipping() {
    double shippingCost;

    switch (shipping){
      case "Regular":
        shippingCost = 0;
        break;
      case "Express":
        shippingCost = 1.75;
        break;
      default:
        shippingCost = 0.5;
    }

    return shippingCost;
}
```

Without break it will execute the below case too.
