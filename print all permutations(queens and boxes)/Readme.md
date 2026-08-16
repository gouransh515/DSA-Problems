\# Queens and Boxes



\## The Problem



I had `N` queens and `B` boxes, and I had to print all the possible ways of placing the queens in different boxes.



For this problem, I took:



```text

B = 4

N = 2

```



Instead of immediately trying to understand recursion or backtracking, I first asked myself:



> \*\*What does "all permutations" actually mean here?\*\*



\---



\## My First Thought



If I put the first queen in one of the 4 boxes, I have:



```text

4 choices

```



Once that queen is placed, that box can't be used again.



So for the second queen, I have:



```text

3 choices

```



That immediately gave me:



```text

4 × 3 = 12

```



So I knew there had to be \*\*12 different possibilities\*\*.



This was my first milestone.



I didn't know how to code it yet, but I understood what the program had to explore:



> \*\*Every possible choice for the first queen, followed by every possible remaining choice for the second queen.\*\*



\---



\## Then I Asked: How Do I Remember Which Boxes Are Taken?



If I choose:



```text

Q0 → B0

```



then when I try to place the next queen, I need to remember that `B0` is no longer available.



So I created:



```java

boolean\[] box = new boolean\[b];

```



My interpretation became:



```text

false → box is empty

true  → box is occupied

```



Now I had a way to represent the current state of my choices.



\---



\## Next Question: How Do I Keep Placing Queens?



At this point I knew:



1\. Try a box.

2\. If it is available, occupy it.

3\. Place the next queen.

4\. Continue until all queens are placed.



That naturally made me think:



> \*\*After placing one queen, I need to do the exact same thing for the next queen.\*\*



That's where recursion started making sense to me.



I didn't think of recursion as:



> "Call the same function because recursion."



I thought of it as:



> \*\*"I have placed one queen. Now solve the same problem for the remaining queens."\*\*



\---



\## What Is `qpsf`?



I needed some way to know how many queens I had already placed.



So I used:



```text

qpsf = queens placed so far

```



Initially:



```text

qpsf = 0

```



After placing the first queen:



```text

qpsf = 1

```



After placing the second:



```text

qpsf = 2

```



Now the stopping condition became obvious.



If:



```text

qpsf == total queens

```



then I have successfully placed every queen.



That means I have reached \*\*one complete permutation\*\*.



So I print it.



\---



\# The Part Where Backtracking Made Sense



This was the important part for me.



Suppose I make this choice:



```text

Q0 → B0

```



Then I explore everything that can happen after it:



```text

Q1 → B1

Q1 → B2

Q1 → B3

```



But after finishing those possibilities, I still need to try:



```text

Q0 → B1

Q0 → B2

Q0 → B3

```



So I realized:



> \*\*I cannot leave B0 marked as occupied forever.\*\*



I have to undo my previous decision.



That's why I do:



```text

mark box as occupied

&#x20;       ↓

explore all possibilities

&#x20;       ↓

mark box as empty again

&#x20;       ↓

try the next box

```



This was the point where I understood what \*\*backtracking\*\* actually meant.



It wasn't some special complicated technique.



It was simply:



> \*\*Make a choice → explore it completely → undo the choice → try another choice.\*\*



\---



\# The Actual Mental Picture



Eventually I stopped seeing the problem as code.



I started seeing it as a tree.



```text

&#x20;                   Q0

&#x20;            /       |       |       \\

&#x20;          B0        B1      B2       B3

&#x20;         / | \\     / | \\   / | \\    / | \\

&#x20;       B1 B2 B3  B0 B2 B3 B0 B1 B3 B0 B1 B2

```



Every level represents another queen being placed.



At every level:



\* I look for available boxes.

\* I choose one.

\* I move deeper.

\* When I finish that branch, I come back.

\* I undo the previous choice.

\* I try the next available box.



\---



\# The Code Came After the Thinking



Once I had this mental model, the important part of the code was almost a translation of my thought process:



```java

if(box\[i] == false)

```



means:



> Is this box available?



```java

box\[i] = true;

```



means:



> I am choosing this box.



```java

print(...);

```



means:



> Now solve the same problem for the next queen.



```java

box\[i] = false;

```



means:



> I am done exploring this choice. Undo it so I can try another one.



And:



```java

if(tq == qpsf)

```



means:



> I have placed all the queens, so one complete possibility is ready.



\---



\# My Milestones



\### Milestone 1 — Understand the permutations



```text

4 boxes

2 queens



4 choices × 3 choices = 12

```



I first understood \*\*what\*\* had to be generated.



\### Milestone 2 — Track the state



I needed to remember which boxes were occupied.



```text

boolean\[] box

```



\### Milestone 3 — Track progress



I needed to know how many queens had been placed.



```text

qpsf

```



\### Milestone 4 — Recursion



After placing one queen, I need to place the next queen using the same process.



\### Milestone 5 — Backtracking



After finishing one choice, undo it so the previous level can try another choice.



\---



\# What I Take Away From This Problem



The important thing I want to remember isn't the syntax of the solution.



It's this pattern:



```text

&#x20;                    CHOOSE

&#x20;                      ↓

&#x20;                   EXPLORE

&#x20;                      ↓

&#x20;                   FINISH

&#x20;                      ↓

&#x20;                    UNDO

&#x20;                      ↓

&#x20;                 NEXT CHOICE

```



For me, this problem was less about learning a "backtracking question" and more about learning how to \*\*translate a thought process into recursion\*\*.



I started with:



> "There are 4 choices, then 3 choices."



Then I had to figure out:



> "How do I remember which choices are already used?"



Then:



> "How do I move from one queen to the next?"



Then:



> "How do I know when I'm done?"



And finally:



> "How do I undo my choice so I can explore another possibility?"



That sequence of questions is what led me to the solution.



\---



\## Why I'm Recording This



There is almost always a clean solution available somewhere.



But when I look at a finished solution, I don't get to see the \*\*thinking that produced it\*\*.



So I want these notes to capture that missing part.



Not:



> \*\*"Here is the correct solution."\*\*



But:



> \*\*"Here is how I went from not knowing how to think about the problem to being able to write the solution myself."\*\*



That's the part I want to get better at.



