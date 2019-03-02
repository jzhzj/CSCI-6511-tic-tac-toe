# Welcome to CSCI 6511 Team Project 2 (Spring 2019)
This project is to build an AI for tic-tac-toe.

Please kindly be reminded to follow [GW Academic Integrity Code](https://studentconduct.gwu.edu/code-academic-integrity).

## Table of Contents

- For developers
  * [Notes](#notes)
  * [Contributing](#contributing)
  * [Usage](#usage)
  * [About Maven](#about-maven)
- [Contacts](#contacts)

## Notes

1. The project is managed by [Apache Maven](https://maven.apache.org/), 
it is highly recommend to use [Intellij IDEA Ultimate](https://www.jetbrains.com/idea/download/) (with built-in Maven v2 & v3 plugin, zero configuration).

2. In this project, JDK 8+ should be used. The default compiler level has been set to 1.8 (JDK 1.8), please revise pom.xml
accordingly if the version of JDK you are using is greater than 8.

## Contributing
1. __Please do not commit anything but source code and resource files to this repository, also please make modifications based on the latest version of code to avoid conflict.__
2. __Please do include a message for every commit.__
3. __Please inform all team members before commit any change regarding to pom.xml, and .gitignore.__ 

## Usage
Revise pom.xml if you are using JDK11
```xml
<properties>
  <maven.compiler.source>11</maven.compiler.source>
  <maven.compiler.target>11</maven.compiler.target>
</properties>
```
Delete target folder
```sh
$ mvn clean
```
Build artifact
```sh
$ mvn install
```
Run / Debug
```sh
$ mvn verify
```
Run Unit Test
```sh
$ mvn test
```
## About Maven
<p align="center">
  <img src="https://cloud.githubusercontent.com/assets/300046/16313672/881e4a8e-3937-11e6-8af5-1c3b93b9caef.jpg">
</p>

## Contacts
- [Qi Jiuzhi](mailto:qijiuzhi@gwu.edu)
- [Zheng Zimu]()
- [Yang Runlai]()
- [Sun Xingwei]()
