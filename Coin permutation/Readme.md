\# Coin Permutation — How I Thought About the Problem



\## The Problem



I have:



\* An array of coins

\* A target amount

\* Each coin can be picked infinitely many times

\* I need to print all the ways to reach the target amount by picking one coin at a time



For example:



```text

Coins = {1, 2, 3, 4}

Amount = 6

```



The first thing I noticed was that \*\*coins are infinite\*\*.



So if I pick `1`, I am completely allowed to pick `1` again.



That immediately gave me a different way of thinking about the problem.



\---



\# My First Thought



Suppose my target is:



```text

6

```



and I pick coin:



```text

1

```



I don't need to think about `6` anymore.



I now need:



```text

5

```



So if I pick `1` again:



```text

5 → 4

```



and again:



```text

4 → 3

```



and so on.



This made the recursive idea appear naturally in my head:



> \*\*Pick a coin, reduce the remaining amount, then solve the same problem for the remaining amount.\*\*



I wasn't thinking about recursion syntax first.



I was thinking about the \*\*smaller problem created by every choice\*\*.



\---



\# The Recursion Tree Started Making Sense



Once I thought about it this way, I could imagine a tree.



Starting from:



```text

6

```



I can choose any coin:



```text

6

├── pick 1 → 5

├── pick 2 → 4

├── pick 3 → 3

└── pick 4 → 2

```



Then each of those becomes another problem.



For example:



```text

6

└── pick 2 → 4

&#x20;   ├── pick 1 → 3

&#x20;   ├── pick 2 → 2

&#x20;   ├── pick 3 → 1

&#x20;   └── pick 4 → 0

```



And every branch keeps making another choice until the remaining amount becomes zero.



So the recursion tree wasn't something I had to memorize.



It came naturally from:



```text

make a choice

&#x20;    ↓

reduce the remaining amount

&#x20;    ↓

make another choice

&#x20;    ↓

reduce again

```



\---



\# Finding the Base Case



Once I had the idea of reducing the amount, the base case became almost obvious.



If:



```text

amount = 0

```



then I have successfully reached the target.



There is nothing left to pick.



So:



```java

if(amount == 0) {

&#x20;   System.out.println(string);

&#x20;   return;

}

```



means:



> \*\*This particular path has successfully completed a solution.\*\*



\---



\# Understanding the Loop



At every stage, I can choose any coin.



So I need to try:



```text

1

2

3

4

```



for every recursive call.



That naturally became:



```java

for(int i = 0; i < coin.length; i++)

```



And before choosing a coin, I check:



```java

if(amount >= coin\[i])

```



because I cannot choose a coin larger than the amount I still need.



\---



\# The Interesting Part — Backtracking



After getting the recursive solution working mentally, I tried thinking about it with explicit backtracking.



The idea was:



```text

choose coin

↓

reduce amount

↓

recurse

↓

restore amount

```



That gives:



```java

amount = amount - coin\[i];

print(coin, amount, string + coin\[i]);

amount += coin\[i];

```



This works.



But then I noticed something interesting.



I didn't actually need to modify the current `amount`.



I could simply pass:



```java

print(coin, amount - coin\[i], string + coin\[i]);

```



\---



\# Why Can I Remove the Backtracking?



This was one of the important things I understood from this problem.



Suppose the current call has:



```text

amount = 6

```



and I make:



```java

print(coin, 4, ...)

```



The deeper recursive call gets:



```text

amount = 4

```



But the current call still has:



```text

amount = 6

```



I didn't modify the current `amount`.



I simply passed a \*\*different value to the next recursive call\*\*.



So the call stack looks conceptually like:



```text

Current call

amount = 6

&#x20;    ↓

Next call

amount = 4

&#x20;    ↓

Next call

amount = 2

&#x20;    ↓

Next call

amount = 0

```



When the `amount = 2` call returns, it doesn't somehow turn the parent's `amount = 6` into `2`.



Each call has its own parameter value.



That was the reason I could remove:



```java

amount = amount - coin\[i];

...

amount += coin\[i];

```



and simply use:



```java

print(coin, amount - coin\[i], string + coin\[i]);

```



\---



\# Comparing This With Queens and Boxes



This helped me understand something deeper about backtracking.



In the Queens problem, I had:



```java

box\[i] = true;

```



That modified an \*\*array\*\*.



The array is shared between recursive calls.



So after returning from recursion, I needed to undo that modification:



```java

box\[i] = false;

```



The pattern was:



```text

modify shared state

&#x20;       ↓

&#x20;     recurse

&#x20;       ↓

&#x20;  restore state

```



But here:



```java

print(coin, amount - coin\[i], ...)

```



I am not modifying the parent's `amount`.



I am passing a new value to the next call.



So there is nothing to restore.



\---



\# The Important Distinction I Learned



This problem helped me understand that:



> \*\*Recursion does not automatically mean backtracking.\*\*



Backtracking becomes necessary when I make a choice that changes some state and that state needs to be restored before another branch is explored.



Here, the remaining amount is naturally passed down as a new value.



So I can solve it without explicit backtracking.



\---



\# My Two Versions



\### With explicit backtracking



```java

amount = amount - coin\[i];



print(coin, amount, string + coin\[i]);



amount += coin\[i];

```



\### Without explicit backtracking



```java

print(coin, amount - coin\[i], string + coin\[i]);

```



Both express the same recursive idea.



The second one is cleaner because I realized the `amount` of the current call never actually needed to be modified.



\---



\# My Thinking Milestones



\### Milestone 1 — Infinite coins



I realized that the same coin can be selected repeatedly.



```text

1 → 1 → 1 → 1 → ...

```



as long as the remaining amount allows it.



\---



\### Milestone 2 — Think in terms of the remaining problem



After choosing a coin:



```text

remaining amount = current amount - chosen coin

```



So every recursive call is simply solving the smaller remaining problem.



\---



\### Milestone 3 — The recursion tree



Every coin creates another branch.



```text

&#x20;            6

&#x20;       /    |    |    \\

&#x20;      5     4    3     2

&#x20;     /|\\   /|\\  /|\\   /|\\

&#x20;    ...   ...  ...   ...

```



I could see the tree before writing the recursion.



\---



\### Milestone 4 — Base case



When:



```text

amount == 0

```



the current path has successfully reached the target.



\---



\### Milestone 5 — Understanding parameters



I realized that passing:



```java

amount - coin\[i]

```



doesn't change the parent's `amount`.



The recursive call gets its own parameter value.



\---



\### Milestone 6 — Understanding why backtracking isn't required



Because I wasn't modifying shared state, there was nothing that needed to be undone.



\---



\# What I Take Away From This Problem



The biggest thing I learned here isn't just how to generate coin permutations.



It is this way of thinking:



```text

Current problem

&#x20;     ↓

Make one choice

&#x20;     ↓

What smaller problem remains?

&#x20;     ↓

Solve that smaller problem recursively

&#x20;     ↓

Stop when the remaining problem is solved

```



And when I see a recursive problem now, I want to ask:



> \*\*What changes after I make one choice, and is that change shared state that I actually need to undo?\*\*



That question helped me understand why the Queens problem needed explicit backtracking while this problem could be written without it.



\---



\# Why I'm Recording This



Again, the interesting part for me isn't the final code.



The code is relatively small:



```java

print(coin, amount - coin\[i], string + coin\[i]);

```



But that line only became obvious after I understood what the recursive call actually represents.



I first thought:



> "Pick a coin."



Then:



> "Now I only need the remaining amount."



Then:



> "Every possible coin creates another branch."



Then:



> "When the amount becomes zero, that branch is a valid answer."



And finally:



> "I don't need to undo the amount because I never modified the parent's amount in the first place."



That's the part I want this repository to preserve:



\*\*not just the solution I wrote, but the thinking that made me able to write it.\*\*



