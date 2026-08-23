\# Sudoku Solver



\## Problem



Given a partially filled \*\*9 × 9 Sudoku grid\*\*, fill the empty cells such that:



\* Every row contains numbers `1–9` without repetition.

\* Every column contains numbers `1–9` without repetition.

\* Every `3 × 3` sub-grid contains numbers `1–9` without repetition.



The goal is to find a valid completed Sudoku.



\---



\## My Approach



I started by observing how I normally solve a Sudoku.



For every empty cell, I try numbers from \*\*1 to 9\*\* and check whether the number can legally be placed there.



That immediately gave me the main parameter:



```java

val

```



where `val` represents the number I am currently trying to place.



For every empty cell, there are potentially 9 choices:



```java

for(int val = 1; val <= 9; val++)

```



But before placing a number, I need to check three conditions.



\### 1. Check the Row



The same number should not already exist in the current row.



\### 2. Check the Column



The same number should not already exist in the current column.



\### 3. Check the 3 × 3 Box



The same number should not already exist inside the current `3 × 3` sub-grid.



This led to a separate method:



```java

isitsafe(grid, cr, cc, val)

```



Its responsibility is simply:



> \*\*Can I safely place this particular value at this particular cell?\*\*



\---



\## Understanding the Recursion Flow



Instead of creating separate recursion logic for rows and columns, I treated the Sudoku as a sequence of cells.



Starting from:



```text

(0,0)

```



I keep increasing the column:



```text

(0,0) → (0,1) → (0,2) → ... → (0,8)

```



When the column reaches `9`, I reset it to `0` and move to the next row.



```java

if(cc == 9) {

&#x20;   cc = 0;

&#x20;   cr++;

}

```



So the traversal becomes:



```text

Row 0: (0,0) → (0,1) → ... → (0,8)

Row 1: (1,0) → (1,1) → ... → (1,8)

...

Row 8: (8,0) → (8,1) → ... → (8,8)

```



When the row reaches `9`, it means every cell has been successfully processed.



```java

if(cr == 9) {

&#x20;   display(grid);

&#x20;   return;

}

```



Therefore:



> \*\*`cr == 9` becomes my base case because it means the entire Sudoku has been completed.\*\*



\---



\## Handling Pre-Filled Cells



If the current cell is already filled:



```java

if(grid\[cr]\[cc] != 0)

```



there is no decision to make.



That number was given by the problem, so I simply move to the next cell:



```java

Print(grid, cr, cc + 1);

```



The important distinction is:



```text

Pre-filled cell

&#x20;     ↓

Accept it

&#x20;     ↓

Move forward

```



Whereas for an empty cell:



```text

Empty cell

&#x20;   ↓

Try 1–9

&#x20;   ↓

Check whether the number is safe

&#x20;   ↓

Place it

&#x20;   ↓

Move forward recursively

```



\---



\## Where Backtracking Happens



This is the most important part of the solution:



```java

grid\[cr]\[cc] = val;



Print(grid, cr, cc + 1);



grid\[cr]\[cc] = 0;

```



The process is:



```text

Choose a number

&#x20;     ↓

Place it

&#x20;     ↓

Recursively solve the remaining Sudoku

&#x20;     ↓

If the branch eventually fails

&#x20;     ↓

Return back

&#x20;     ↓

Erase the previous choice

&#x20;     ↓

Try another number

```



The line:



```java

grid\[cr]\[cc] = 0;

```



is what restores the cell to its previous state.



This allows the algorithm to leave a wrong branch and explore another possibility.



\---



\## Important Insight About `isitsafe()`



`isitsafe()` only checks whether the current choice is \*\*valid right now\*\*.



It does not guarantee that the choice will lead to a solution.



For example:



```text

Choose 5

&#x20;  ↓

5 is valid in the current row

&#x20;  ↓

5 is valid in the current column

&#x20;  ↓

5 is valid in the current 3×3 box

&#x20;  ↓

Continue recursively

&#x20;  ↓

Several cells later...

&#x20;  ↓

No valid number exists

```



At this point, recursion returns to an earlier decision.



That earlier value is removed and another value is tried.



So there is an important distinction:



> \*\*`isitsafe()` checks local validity, while backtracking handles the consequences of that choice.\*\*



\---



\## Backtracking Pattern Identified



This problem helped me recognize a general backtracking pattern:



```text

1\. Find a position where a decision is required.

2\. Generate possible choices.

3\. Check whether a choice is valid.

4\. Make the choice.

5\. Recursively solve the remaining problem.

6\. Undo the choice.

7\. Try the next choice.

```



For Sudoku:



```text

Position  → Current empty cell

Choices   → Numbers 1–9

Constraint → Row + Column + 3×3 box

Choice     → Place a number

Recursion  → Solve remaining cells

Undo       → Set cell back to 0

```



\---



\## Code Structure



\### `Print()`



Responsible for:



\* Traversing the Sudoku grid.

\* Finding empty cells.

\* Trying possible values.

\* Making recursive calls.

\* Backtracking when necessary.



\### `isitsafe()`



Responsible for checking:



\* Current row.

\* Current column.

\* Current `3 × 3` box.



\### `display()`



Responsible for printing the completed Sudoku.



\---



\## Complexity



There can be up to \*\*9 choices for an empty cell\*\*, and there can be up to \*\*81 cells\*\*.



In the worst case, the search space is approximately:



```text

O(9^81)

```



This is a theoretical worst case. The row, column, and `3 × 3` constraints eliminate a huge number of possibilities during the actual search.



The space used by the recursion is at most proportional to the number of cells:



```text

O(81)

```



which is effectively constant for a standard 9 × 9 Sudoku.



\---



\## What I Learned



The biggest learning from this problem was not the Sudoku rules themselves.



It was understanding how a \*\*constraint-based problem can become a backtracking problem\*\*.



I recognized that:



\* Every empty cell represents a decision.

\* `1–9` represents the possible choices.

\* `isitsafe()` represents the constraints.

\* Recursion explores the consequences of a choice.

\* Resetting the cell performs the backtracking.

\* Reaching `cr == 9` means a complete valid solution has been found.



The important realization was:



> \*\*I don't need to know the correct number beforehand. I only need to generate the possible choices, reject invalid ones, explore valid choices, and undo choices when they lead to a dead end.\*\*



This is the same fundamental idea appearing in the other backtracking problems I have been solving, but Sudoku makes the \*\*choice → recursion → failure → undo → next choice\*\* cycle especially clear.



\---



\## Key Backtracking Template



```java

for(each possible choice) {



&#x20;   if(choice is safe) {



&#x20;       make the choice;



&#x20;       recursive call;



&#x20;       undo the choice;

&#x20;   }

}

```



For Sudoku, the template becomes:



```java

for(int val = 1; val <= 9; val++) {



&#x20;   if(isitsafe(grid, cr, cc, val)) {



&#x20;       grid\[cr]\[cc] = val;



&#x20;       Print(grid, cr, cc + 1);



&#x20;       grid\[cr]\[cc] = 0;

&#x20;   }

}

```



This problem reinforced that \*\*backtracking is essentially controlled trial-and-error with state restoration\*\*.



