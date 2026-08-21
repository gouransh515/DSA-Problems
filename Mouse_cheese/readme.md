\# Rat Chases Cheese



\## The Problem



The problem is to help a rat move through a maze from the starting cell to the destination.



The maze has open cells and blocked cells (`X`), and the rat can move in four directions:



\* Left

\* Right

\* Up

\* Down



The goal is to find the path and represent it using an answer matrix.



\---



\## My First Thought



When I first read the problem, my immediate thought was:



> "The number of recursive calls will be equal to the number of ways my mouse can move."



I understood that from every position, the rat could potentially move in different directions, so recursion would be used to explore those possibilities.



But while tracing the recursion, I realized that this wasn't exactly correct.



The recursion doesn't mean every call is a valid movement.



Instead, the recursion \*\*tries all four directions\*\*, and the conditions decide whether that attempt is actually valid.



\---



\## The Conditions Became Clear



There were three important situations where the recursive call should stop.



\### 1. Destination



When the rat reaches the last cell:



```java

cr == maze.length - 1 \&\& cc == maze\[0].length - 1

```



the path has reached the destination.



I mark that cell in `ans` and display the path.



\### 2. Outside the Maze



The rat can try moving outside the boundaries.



So I need to reject positions where:



```java

cr < 0

cc < 0

cr >= maze.length

cc >= maze\[0].length

```



\### 3. Blocked Cell



If the rat reaches a cell containing `X`, that path cannot continue.



```java

maze\[cr]\[cc] == 'X'

```



So that recursive call simply returns.



This helped me understand that the recursion is basically:



> \*\*Try every direction and let the conditions reject the impossible ones.\*\*



\---



\## The Interesting Part — Why Mark the Cell `X`?



This was the part that made the problem interesting for me.



Suppose I am standing on a cell `A`.



```text

A → B

```



I move from `A` to `B`.



But from `B`, recursion can try moving left and come back to `A`.



That could create something like:



```text

A → B → A → B → A → ...

```



To prevent this, when I am standing on `A`, I temporarily mark it as:



```java

maze\[cr]\[cc] = 'X';

```



Now, while all the recursive branches coming from `A` are being explored, another branch cannot come back to `A`.



So `X` is basically saying:



> "I am already using this cell in my current path, so don't come back here."



\---



\## Why Is `X` Temporary?



This was the important backtracking realization.



Suppose from `A` there are two possibilities:



```text

&#x20;       A

&#x20;      / \\

&#x20;  wrong  right

```



I mark:



```text

A = X

```



and explore the first branch.



If that branch fails, I still need to be able to use `A` when exploring another branch.



Therefore, after \*\*all branches from A have been explored\*\*, I restore it:



```java

maze\[cr]\[cc] = 'O';

```



So the lifecycle of a cell becomes:



```text

Stand on A

&#x20;  ↓

A = X

&#x20;  ↓

Explore all possibilities from A

&#x20;  ↓

Finished with A

&#x20;  ↓

A = O

```



This made me understand that the `X` is not a permanent change.



It represents the cell being used by the \*\*current recursion path\*\*.



\---



\## The `ans` Matrix Also Needs Backtracking



I initially had:



```java

ans\[cr]\[cc] = 1;

```



both before and after exploring the recursive branches.



Then I realized that was an error.



If `ans` represents the current path, then when I backtrack from a cell, I also need to remove it from the current path.



So the correct mental model is:



```java

ans\[cr]\[cc] = 1;

```



when entering the cell, and:



```java

ans\[cr]\[cc] = 0;

```



when leaving it after the branch has been explored.



This is the same idea as:



```text

maze:

X → O



answer:

1 → 0

```



The maze state and answer state both need to be restored.



\---



\## What Actually Made This Backtracking?



This question became clear after tracing the code.



Recursion is what allows me to explore the choices.



Backtracking happens because after exploring a choice, I \*\*restore the state to what it was before\*\* so another choice can be explored.



So my mental model became:



> \*\*Recursion explores. Backtracking explores + undoes the state change.\*\*



In this problem:



```java

maze\[cr]\[cc] = 'X';



// explore directions recursively



maze\[cr]\[cc] = 'O';

```



and similarly:



```java

ans\[cr]\[cc] = 1;



// explore



ans\[cr]\[cc] = 0;

```



The recursive call returns naturally, but the state I changed before making that call has to be restored manually.



\---



\## Understanding the Four Directions



Instead of writing four separate recursive calls:



```java

rat\_maze(maze, cr, cc - 1, ans);

rat\_maze(maze, cr, cc + 1, ans);

rat\_maze(maze, cr - 1, cc, ans);

rat\_maze(maze, cr + 1, cc, ans);

```



I used arrays:



```java

int\[] r = {0, 0, -1, 1};

int\[] c = {-1, 1, 0, 0};

```



and:



```java

for (int i = 0; i < c.length; i++) {

&#x20;   rat\_maze(maze, cr + r\[i], cc + c\[i], ans);

}

```



The important thing I understood here was that the loop doesn't guarantee that every recursive call represents a valid move.



It simply tries every possible direction.



The boundary, blocked-cell, and visited-cell conditions decide whether that attempt survives.



\---



\## The `flag` Realization



The problem guarantees that a unique path exists.



When the destination is reached:



```java

flag = true;

```



I realized that after this happens, the remaining directions don't need to be explored.



So inside the loop I can check:



```java

if (flag == true) {

&#x20;   break;

}

```



before making the next recursive call.



The flow becomes:



```text

Try a direction

&#x20;    ↓

Recursive call

&#x20;    ↓

Destination found

&#x20;    ↓

flag = true

&#x20;    ↓

Return to parent loop

&#x20;    ↓

flag is checked

&#x20;    ↓

break

```



This prevents unnecessary exploration after the answer has already been found.



\---



\## My Learning Milestones



\### 1. Recursion doesn't mean every call is valid



I initially thought the number of recursive calls represented the number of ways the rat could move.



I corrected this by tracing the invalid calls.



The recursion tries possibilities, while the conditions reject invalid ones.



\### 2. A cell must be temporarily blocked



I understood why the current cell is marked `X`.



Without it, recursion could return to an already visited cell and create cycles.



\### 3. `X` must eventually become `O`



I realized that the cell cannot remain permanently blocked.



Another branch from an earlier point may need to use it.



\### 4. The answer matrix is also state



I caught my own mistake where I was setting `ans\[cr]\[cc] = 1` again after recursion.



If the branch doesn't remain part of the path, the answer matrix must also be restored:



```java

ans\[cr]\[cc] = 0;

```



\### 5. Backtracking is restoring state



This was probably my biggest conceptual takeaway.



I don't just recursively move forward.



I change the current state, explore, and then restore that state so another possibility can be tried.



\---



\## What I Want To Remember



The mental model I want to remember from this problem is:



```text

Come to a cell

&#x20;    ↓

Check whether the cell is valid

&#x20;    ↓

Mark it as part of the current path

&#x20;    ↓

Temporarily block it from being revisited

&#x20;    ↓

Try all possible directions

&#x20;    ↓

If a branch fails, recursion returns

&#x20;    ↓

Restore the state

&#x20;    ↓

Allow another branch to use the cell

```



The most important line of thinking for me is:



> \*\*"What state am I changing before recursion, and what state do I need to restore after recursion?"\*\*



That question helps me recognize where backtracking is actually happening.



\---



\## Why I'm Recording This



This problem was interesting because the Java syntax itself was also a big part of the learning.



I had to understand:



\* 2D arrays

\* character arrays

\* recursive function calls

\* passing arrays into recursive methods

\* modifying shared arrays

\* loops inside recursion

\* direction arrays

\* boolean state using `flag`

\* `break`

\* base cases

\* boundary conditions

\* temporary state changes



But more importantly, I didn't want to just remember the code.



I want to remember \*\*why the code needs to change state, why that state has to be restored, and how recursion and backtracking work together.\*\*



That's the part I want to carry into the next problem.



