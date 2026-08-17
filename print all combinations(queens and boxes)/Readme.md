\# Queens and Boxes — Combination Variation



This is a variation of my previous \*\*Queens and Boxes\*\* problem.



The original problem was about finding \*\*all permutations\*\*.



This time, the requirement changes slightly:



> Find all \*\*combinations\*\*.



That small change forced me to rethink what the recursion actually needs to remember.



\---



\# Starting From What I Already Understood



For the previous problem:



```text

B = 4

Q = 2

```



I understood that the first queen has 4 choices.



After placing it, the second queen has 3 choices.



So:



```text

4 × 3 = 12

```



But those 12 are permutations.



For example:



```text

Q0 → B0

Q1 → B1

```



and:



```text

Q0 → B1

Q1 → B0

```



are considered different.



\---



\# What Changes With Combinations?



For combinations, I don't want to count those as two different answers.



The \*\*selection of boxes is what matters\*\*, not the order in which the queens selected them.



So:



```text

B0 + B1

```



should only appear once.



I needed some way to make the recursion \*\*stop going backward\*\*.



\---



\# My First Question



In the permutation version, I had:



```java

boolean\[] box

```



because I needed to know:



> Is this box already occupied?



But now I asked myself:



> \*\*What if I simply don't allow the recursion to go back to previous boxes?\*\*



For example:



```text

Q0 → B1

```



Then for the next queen, instead of starting from `B0` again, I start from:



```text

B2

```



So the recursion only moves forward.



That led me to `idx`.



\---



\# Understanding `idx`



The loop becomes:



```java

for(int i = idx; i < box.length; i++)

```



And after choosing box `i`, I pass:



```java

i + 1

```



to the next recursive call.



So if:



```text

Q0 → B1

```



then:



```text

idx = 2

```



for the next queen.



The next queen can therefore only look at:



```text

B2, B3

```



It cannot go back to:



```text

B0, B1

```



\---



\# The Important Realization



At first I thought `idx` was just another way of tracking which boxes were occupied.



But then I realized that wasn't quite right.



The `boolean\[]` and `idx` solve \*\*different problems\*\*.



\### `boolean\[]`



Tracks:



> \*\*Is this particular box already being used?\*\*



\### `idx`



Enforces:



> \*\*From which position am I allowed to start looking?\*\*



And because `idx` only moves forward, a deeper recursive call can never return to an earlier box.



\---



\# Then I Questioned the Boolean Array



This was the interesting part.



I asked:



> If `idx` already prevents me from going backward, can I still use an already-selected box?



Suppose:



```text

Q0 → B1

```



Then the next call starts with:



```text

idx = 2

```



The loop is now:



```text

B2

B3

```



It doesn't even consider `B1`.



So the answer is no.



The structure of the recursion itself has already prevented that situation.



That made me realize:



> \*\*The `boolean\[] box` is no longer necessary.\*\*



\---



\# Removing the Tracker



I tested the idea by commenting out:



```java

// if(box\[i] == false) {

//     box\[i] = true;

```



and:



```java

// box\[i] = false;

```



The important part was now simply:



```java

for(int i = idx; i < box.length; i++) {



&#x20;   print(

&#x20;       box,

&#x20;       tq,

&#x20;       qpsf + 1,

&#x20;       ans + "b" + i + "q" + qpsf,

&#x20;       i + 1

&#x20;   );

}

```



The `box` array wasn't actually being used to make a decision anymore.



So I could eventually remove it completely from the solution.



\---



\# Why No Undo Is Required Here



This was another useful realization.



In the permutation problem, I had:



```text

choose

↓

mark occupied

↓

recurse

↓

undo

↓

try another choice

```



The undo was necessary because later branches could go back and use earlier positions.



But in the combination version:



```text

choose B0

↓

only B1, B2, B3 are available to future calls



choose B1

↓

only B2, B3 are available



choose B2

↓

only B3 is available

```



The recursion is always moving forward.



So there is nothing to undo.



\---



\# The Mental Model Changed



The permutation problem felt like:



```text

"Which boxes are currently occupied?"

```



The combination problem became:



```text

"How far have I progressed through the boxes?"

```



That is a much smaller amount of state.



\---



\# My Two Mental Models



\### Permutation



```text

Try any unused box

&#x20;       ↓

Remember that it is occupied

&#x20;       ↓

Go deeper

&#x20;       ↓

Undo the choice

&#x20;       ↓

Try another box

```



\### Combination



```text

Try a box

&#x20;       ↓

Only allow future choices after it

&#x20;       ↓

Go deeper

&#x20;       ↓

No undo required

&#x20;       ↓

Continue forward

```



\---



\# The Key Insight



The biggest thing I learned from this variation wasn't just:



> "You can remove the boolean array."



It was:



> \*\*Sometimes recursion itself can enforce a constraint, so you don't need a separate data structure to track that constraint.\*\*



In the permutation problem, I needed explicit state:



```text

boolean\[]

```



In the combination problem, the increasing:



```text

idx = i + 1

```



makes the state progression itself enforce the rule.



\---



\# My Learning Milestones



\### Milestone 1



I understood that combinations don't care about the order of choosing the boxes.



\### Milestone 2



I realized that I need to prevent the recursion from going backward.



\### Milestone 3



I understood that `idx` does this by making every recursive level start after the previous choice.



\### Milestone 4



I questioned whether `boolean\[]` was still necessary.



\### Milestone 5



I realized that `idx` already prevents previously visited boxes from being selected again.



\### Milestone 6



I removed the tracker and the mark/unmark operations.



\---



\# What I Want To Remember



The useful pattern I want to carry forward is:



```text

Permutation:

"Any unused position can be chosen."

→ Need to track used positions.



Combination:

"Only positions after my current position can be chosen."

→ Position progression can enforce uniqueness.

```



So when I see a future recursion/backtracking problem, I don't want to automatically reach for a `visited\[]` or `boolean\[]`.



I want to ask:



> \*\*Can the structure of my recursion itself guarantee that I never make an invalid/repeated choice?\*\*



If yes, some of my state might be unnecessary.



\---



\# Why I'm Recording This



The interesting part of this problem wasn't finding a shorter piece of code.



It was noticing \*\*why the shorter code became possible\*\*.



I started with the previous permutation solution.



Then the requirement changed from:



```text

permutation

```



to:



```text

combination

```



That forced me to question the purpose of every piece of state I had.



And that led to:



```text

boolean\[] → unnecessary

mark/unmark → unnecessary

idx → enough to control the direction of recursion

```



That's the kind of understanding I want these GitHub notes to preserve.



Not just \*\*what the final code is\*\*, but \*\*what changed in my thinking that made the final code possible\*\*.



