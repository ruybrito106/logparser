# Solution
> Author: Ruy Brito Barbosa  
> Date: 07-11-17  
> Essay link: https://gist.github.com/guiocavalcanti/ea42e57ce0a0491e17ab164973d9679c  
> HandsOn link: https://gist.github.com/guiocavalcanti/ab70f53f9850c9ed7ffc5421360e1340  
> Essay solution link: https://gist.github.com/ruybrito106/dde8c5be5e24e0f7ba5e27f49b85b8bd  

## Project description

This project is a component step of a recruitment process. The initial guidelines were provided in the first link above
and the initial system design is available at the third link above.

Basically, the system is designed to cover three user cases in which we want to provide some information about a Quake III
game log. The cases are the following:

* The first user case is a simple game status with information about players in a current match, their kill amount and
the total kill amount from a specific match.
* The second user case is the ranking (based on the kill amount) of the players.
* The third user case is a web solution to the previous user case, i.e., a web disposition of the same data previously gathered.

## What's changed from Essay version

The modifications listed below are either due to misunderstandings on the previous version or to efficiency-based decisions.
They are the following:

* Regarding the structural organization of the project, there were some updates. Folders for storing test status, assets, fonts and styles (for web template) and dependencies (lib) were added to the structure.
* From the essay description, I've considered previously that each log file would contain information about a single match,
what has proved to be wrong (based on the sample provided). The system now cover multiple matches.
* The initial idea to cover the third user case was by overwritting a template file. However, since we are now dealing with
multiple matches, I found reasonable to use another language besides *Java* to deal with the *HTML* file. The choice was for
*Javascript* for the language is expressive, powerful, supports asynchronous programming and has also a great community.
The use of *Javascript* is basically to parse the *ranking.txt* file into the webpage file.
* The multithreading **LogHandler** module was suggested previously and implemented now. For its common concurrency problems,
I had to take some decisions in order to make the whole module thread-safe. These decisions were for using concurrency
supportive data structures for shared data (by threads), as *Java* ConcurrentHashMap, and making shared methods *synchronized*.
As the module was getting a little bit overcomplicated, I decided to split in two modules (**LogHandler** and **Data**),
which communicate together with the higher-level modules.
* Some other storage strategies were adopted to simplify the parsing process. One of them is the idea of grouping the match
data in a class *MatchStatus*, whose objects are stored by the **Data** module and used by high-level modules.
* The bash files' complexity has been improved in order to simplify the (Unix) user experience. Now the user can optionally
choose the input file, the number of threads used in the processing and the output file.
* Since the sorting processing of the **Ranking** module was based on a custom class (*Pair*), it was necessary to override the hashcode method of the class and a polynomial hash algorithm has been implemented for that.
* The third user case implementation changed a bit. Although mantaining the same tools suggested previously, I've changed a lot the suggested web template in order to make it responsive and user-friendly. Also the parsing process, as mentioned before, has been modified. Some Javascript and jQuery code has been necessary for this task.
* The use of **Redis** database has been suggested if the application were larger and needed a faster processing, but I
considered it would be a overkill and it remained as a suggestion.
* Since the application grew a little bit more complex, it became necessary to increase the number of unity tests, for that
*JUnit* has been chosen. The choice will be defended below at the *Tests* section.

The above modifications result in the following system scheme:

![alt text](http://res.cloudinary.com/ufpe/image/upload/v1499753670/Scheme2.png "System Scheme")

## Getting started

This instructions aim to provide you a local copy of the repository for testing and contributing purposes

### Prerequisites

For using the system, the following prerequisites are needed:

* User needs to have *Python 2.7.6* or later installed because the webpage generated is hosted in a local python server.
* User needs to have *Java 7* or later installed, since great part of the code is written in *Java*.

### Installation

#### On Unix-based systems

Run the project in quick steps:

```bash
git clone "https://ruybrito106@bitbucket.org/ruybrito106/logparser.git"
cd /logparser/Source/Scripts
bash GameStatus.sh -N -inPath -outPath #1 User Case
bash Ranking.sh -N -inPath -outPath #2 User Case
bash WebRanking.sh -N -inPath #3 User Case
```

```text
N: number of threads used in the processing. Default value: 5
inPath: input path. Default value: ~/logparser/Logs/input.txt
outPath: output path. Deafult value: ~/logparser/Outputs/(depends_on_the_user_case).txt

[1] The parameters above are optional, but if passed, they should be in the same sequence
[2] The WebRanking script doesn't require output path, since the result is outputted in a webpage
    automatically opened at http://localhost:8080/Outputs/Web/rankingWeb.html
[3] The output path is printed on the console
```

#### On Windows

Many of the implemented command-line scripts are written in *Bash*, which is native on Unix-based systems. So, if the user wants to run the project on Windows, it is necessary to change directory from the project main folder to */Source/Scripts*, and command-line at cmd the equivalent on Windows.

### Usage sample

```bash
cd /logparser/Source/Scripts
bash GameStatus.sh -16    #Input/Output is default
bash GameStatus.sh -1 -../../Logs/sample.txt    #Input is sample.txt, Output default
bash Ranking.sh -10 -../../Logs/sample.txt -../../../   #Output in project's parent folder
bash Ranking.sh -22   #Input/Output is default
bash WebRanking.sh -13
```

## Tests

Several tests were implemented in order to guarantee the consistency of the written methods. The user can also run the tests
to check the results. The tests were implemented with JUnit 4.12 for the following reasons:

* JUnit is a framework designed specifically to deal with unit tests, which is basically my goal
* JUnit provides easy-to-use methods, clear documentation, active community and fancy output formatting

Unit tests have been written for all Java classes but the Pair one, since it is a simple container and has no complex method to be tested.

### Dependencies

The following dependencies are required for testing:

* JUnit 4.12 (jar file)
* Hamcrest core 1.3 (jar file)

Since the project is simple, I found reasonable not to use a dependency manager as *Maven* for its unecessary additional
complexity. But, in order to simplify the dependency instalation, the user can simply run the following commands:

```bash
cd /logparser/Source/Scripts
bash DependencyManager.sh
# This will install the jar files if are not already installed
```

### Running tests

For running all the tests just run the following commands:

```bash
cd /logparser/Source/Scripts
bash RunTests.sh
# This will run all the unit tests and output them in separate files
```

All the test status are outputted at */Outputs/TestStatus*.

## References

The following references have been used:

* http://www.javaworld.com/article/2076265/testing-debugging/junit-best-practices.html [JUnit best practice]
* https://tiswww.case.edu/php/chet/bash/bashref.html [Bash documentation]
* https://codepen.io/ [Responsive developing]
