# Advent of Code 2020 - matthewjhands attempt
 
This is my attempt at the [Advent of Code 2020 Challenge](https://adventofcode.com/2020/). This year I'm attempting each day's challenge using Java 11, to reinforce some recent training. I'm also using the opportunity to try out VSCode's support of Java development on Windows using Microsofts' [recommended Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

## Measuring solution performance using Powershell

You can use `Measure-Command` as a tool similar tool to Unix's `time` to measure how long a solution takes to execute.

``` powershell
Measure-Command { java path\to\solution.java | Out-Default }
```