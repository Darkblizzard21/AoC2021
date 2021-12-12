# AoC2021

Solving AoC with Java 11. It is by far not the cleanest solution and was done just for fun.
Every day can be started from src/TestClass.java

## Structure
Starting with day 9 the implementations consist out of up to three layers:
1. *blaFoo*InputProvider
   1. provides all load functions and hides them form the main class
2. *blaFoo* extends *blaFoo*InputProvider
   1. main class implements all logic to solve the current day
3. *blaFoo*_WithDebug extends *blaFoo*
   1. contains all visualization and other debug functions
   2. is completely optional and only there if it was needed