\# Prime Sieve — Sieve of Eratosthenes



\## 🧩 Problem



Find all prime numbers up to a given number `n`.



For example, for `n = 100`, print all prime numbers from `2` to `100`.



\---



\## 💡 The Main Idea



The \*\*Sieve of Eratosthenes\*\* is based on a mathematical observation:



> If a number is composite, then it must have at least one factor less than or equal to its square root.



Therefore, while finding primes up to `n`, we only need to process numbers up to:



```text

√n

```



For every number that is still marked as prime, we eliminate all of its multiples because they cannot be prime.



So the process is:



```text

Assume every number is prime

&#x20;       ↓

Pick the next unmarked number

&#x20;       ↓

It is prime

&#x20;       ↓

Eliminate its multiples

&#x20;       ↓

Continue until √n

&#x20;       ↓

Everything still marked is prime

```



\---



\## 🔎 Why Does √n Work?



Suppose `x` is a composite number.



Then:



```text

x = a × b

```



If both `a` and `b` were greater than `√x`, then:



```text

a × b > √x × √x

&#x20;     > x

```



which is impossible.



Therefore, at least one factor of every composite number must be:



```text

≤ √x

```



This means that when we process all possible factors up to `√n`, every composite number up to `n` will have been eliminated.



This is the mathematical justification for stopping the outer loop at:



```java

i \* i <= n

```



\---



\## 🧠 Translating the Mathematics into Code



First, create a boolean array.



```java

boolean\[] a = new boolean\[n + 1];

```



Initially, mark every number from `2` onwards as potentially prime:



```java

for(int i = 2; i < a.length; i++) {

&#x20;   a\[i] = true;

}

```



Then start checking numbers from `2`.



If `a\[i]` is still `true`, then `i` has not been eliminated by any smaller prime, so `i` is prime.



```java

if(a\[i] == true)

```



Now eliminate its multiples.



A simple version is:



```java

for(int j = 2; j \* i < a.length; j++) {

&#x20;   a\[j \* i] = false;

}

```



After processing up to `√n`, every number that remains `true` is prime.



\---



\## ⚙️ Important Optimization



The multiples of `i` don't actually need to start from `2 × i`.



For example, when `i = 5`:



```text

10 → already eliminated by 2

15 → already eliminated by 3

20 → already eliminated by 2

```



The first new multiple that needs to be eliminated is:



```text

5 × 5 = 25

```



So we can write:



```java

for(int j = i \* i; j <= n; j += i) {

&#x20;   a\[j] = false;

}

```



This avoids repeatedly checking numbers that have already been eliminated.



\---



\## 🧪 Example



For:



```text

n = 30

```



Start with:



```text

2  3  4  5  6  7  8  9  10 ... 30

✓  ✓  ✓  ✓  ✓  ✓  ✓  ✓  ✓  ... ✓

```



\### Process 2



Eliminate:



```text

4, 6, 8, 10, 12, ...

```



\### Process 3



Eliminate:



```text

9, 12, 15, 18, 21, ...

```



\### Process 5



Since:



```text

5 × 5 > 30

```



there is no need to continue processing primes beyond this point.



The numbers still marked are:



```text

2, 3, 5, 7, 11, 13, 17, 19, 23, 29

```



These are the primes up to `30`.



\---



\## ⏱️ Complexity



The Sieve of Eratosthenes runs in approximately:



```text

Time:  O(n log log n)

Space: O(n)

```



The space comes from the boolean array used to keep track of which numbers are still considered prime.



\---



\## 🧠 What I Learned



This problem was less about discovering a complicated algorithm and more about \*\*recognizing and implementing a mathematical property\*\*.



The important realization was:



```text

Every composite number has a factor ≤ √n

```



Therefore:



```text

We only need to eliminate multiples of primes up to √n.

```



The code is basically a direct translation of that mathematical reasoning.



This is a good example of how understanding \*\*why an algorithm works\*\* can make the implementation feel almost obvious.



\---



\## 📝 Final Implementation



```java

package imp;



public class prime\_seive {



&#x20;   public static void main(String\[] args) {



&#x20;       int n = 100;



&#x20;       boolean\[] a = new boolean\[n + 1];



&#x20;       // Assume every number from 2 onwards is prime

&#x20;       for(int i = 2; i < a.length; i++) {

&#x20;           a\[i] = true;

&#x20;       }



&#x20;       // We only need to process up to √n

&#x20;       for(int i = 2; i \* i < a.length; i++) {



&#x20;           if(a\[i] == true) {



&#x20;               // Eliminate multiples of i

&#x20;               for(int j = i \* i; j < a.length; j += i) {

&#x20;                   a\[j] = false;

&#x20;               }

&#x20;           }

&#x20;       }



&#x20;       // Print remaining prime numbers

&#x20;       for(int i = 2; i < a.length; i++) {



&#x20;           if(a\[i] == true) {

&#x20;               System.out.println(i);

&#x20;           }

&#x20;       }

&#x20;   }

}

```



\---



\## 🚀 Takeaway



\*\*The Sieve of Eratosthenes isn't difficult because of its implementation.\*\*



Its power comes from the mathematical observation that allows us to avoid checking every number individually.



```text

Mathematical property

&#x20;       ↓

Only process up to √n

&#x20;       ↓

Eliminate multiples

&#x20;       ↓

Remaining numbers = primes

```



