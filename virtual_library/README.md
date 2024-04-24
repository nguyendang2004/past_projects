# Assignment 2
## Program description
- Virtual scroll access system
- Digital storage for scrolls
- Terminal application
- Java version: Java 17 LTS
- Gradle version: Gradle 7.4
- JUnit framework: JUnit 4

## Extended program description
| **User Type**   | What They Can Do                                                                                                                                          |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| Guest           | viewScrolls, searchScroll, logout                                                                                                                         |
| Registered User | addScroll, editScroll, removeScroll, viewScrolls, downloadScroll, editDetails, searchScroll, logout                                                       |
| Admin           | addScroll, addAccount, removeAccount, viewProfiles, viewUploads, viewScrolls, removeScroll, editScroll, downloadScroll, editDetails, searchScroll, logout |
| Wizard          | Can do the same things as Admin, but can also edit any file/scroll                                                                                               |

- By default, the Admin has a username, and password of "admin", and the Wizard has a username, and password of "wizard"
- In the initial screen, users can log in as a guest, or user. Also, they may register for an account or exit the app.
- To interact with the app: 
  - the functionality, 
  - followed by the command, which is surrounded by brackets, is displayed.
  - E.g. `> Log in as Guest        (guest)`, denotes that to login as a guest, you write the `guest` command on the terminal
- Uploaded scrolls are stored in the `uploads/<custom_path>` folder, where `<custom_path>` is the path specified when adding the scroll
- Downloaded scrolls are in the `downloads/<username>` directory, where `<username>` is the user's current username
- Scrolls can only be added by Registered User, Admin, and Wizard users, and the scroll content is supplied through the terminal, and automatically uploaded at a path specified by the user.

## How to install
- To install this software, please clone this repository by doing `git clone https://github.sydney.edu.au/SOFT2412-COMP9412-2023S2/Lab13-Qifan-Group1-A2.git`
- The contents will be loaded onto your device

## How to use the program
- After cloning the repository on your device, you can run it as follows.
- To run the program, use `gradle clean run --console=plain`

## How to test the program
- To run the test cases, use `gradle clean test`
- To see the Jacoco test report, use `gradle clean build jacocoTestReport`

## How to contribute to this project
- Fork this repository, selecting an appropriate owner for it
- After creating the fork, clone it onto your local device
- Create a new branch and make your changes there
- On the GitHub page for the forked repository, press `Contribute`, and create a pull request, with an explanation of your changes
- We will review your pull request, and take appropriate measures with it
