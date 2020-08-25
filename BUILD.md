# Building Cell Line Sleuth
Only the java parts of the program require any compilation, and since java is a bytecode based language you can use the distributed binaries on all platforms.

If you do want to build from scratch then you can do this using the ant build file provided

## Requirements
To build from source you will need:

* A java development kit installation which provides a working copy of ```javac```

* An installation of the [Apache Ant](https://ant.apache.org/) build system

## Building
Once you have a copy of the source tree on your machine, simply cd to the root directory of the project and run

```ant```

You should see something like this:

```
Buildfile: /bi/home/andrewss/Sleuth/celllinesleuth/build.xml

build-subprojects:

init:
    [mkdir] Created dir: /bi/home/andrewss/Sleuth/celllinesleuth/bin
     [copy] Copying 15 files to /bi/home/andrewss/Sleuth/celllinesleuth/bin

build-project:
     [echo] CellLineSleuth: /bi/home/andrewss/Sleuth/celllinesleuth/build.xml
    [javac] Compiling 6 source files to /bi/home/andrewss/Sleuth/celllinesleuth/bin

build:

BUILD SUCCESSFUL
Total time: 0 seconds
```
The compiled code should be in a newly created ```bin``` folder and you can either use it from there, or copy it elsewhere.

