# jRules Framework

## Introduction
JRules is a framework to execute rules or a group of rules before specific call method. The main developments objetive is validate signature method’s parameters in an easy and simple way, using annotations.

In this first version, jRules is based on spring, for this reason all the annotation's interception happen when the method's calls are through the context. I'm working in other version without spring dependency, using just aspectj.

## Current features
* Create a rule, and execute this rule before method's call.
* Create a few rules, and execute individually before method's call.
* Create a rule and assign one or more groups.
* Associate a list of rules to the same group, and execute this group before method's call.
* Execute a group or more groups of rules before  method's call..
* Execute individually rules and a list of groups of rules before method's call.

## How use it?
Use jRules is very easy!  You just need to follow these four steps.

1. Obtain jRules jar for your project, import the project from maven repository or clone the src and compile the project using maven
2. Configure project's web.xml file
3. Create a jRules
4. Annotate with jRules annotation the method to test

You can find the complete manual to configure and use jRules at [wiki page](https://github.com/joeldelvalle/jRules/wiki). Also, you can clone a simple web aplication to watch how jRules works at [jRule-example](https://github.com/joeldelvalle/jRules-example.git).

## Quick start

### 1. Obtain jRules jar for your project

#### Clone the src and compile
1. Before, you need git and maven in your system (if you don't have it pls google how to install git and maven).  
2. After install, in a console type:  ``` git clone https://github.com/joeldelvalle/jRules.git ```
3. Then go to downloaded src path and write:  ```mvn clean install ```
4. When the compilation is finished, go to target and use the jrules-1.0.0-SNAPSHOT.jar

### 2. Configure web.xml file
