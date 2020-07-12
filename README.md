# SMTP Tutorial

I've programmed this interactive tutorial for a presentation I had to hold in my highschool computer science course on how the SMTP protocol works.

Instead of just reading through a list of commands and explaining them, I thought it would be more interesting to test them out directly by having a basic SMTP server and a console where you can enter SMTP commands and send mails to your classmates.

It did work pretty well with the presentation as a further explanation for some commands and the web app is still online to play around with at
[smtp-tutorial.syscy.de](https://smtp-tutorial.syscy.de).
While you can always use the `HELP` command to guide you through the process of sending a mail, it may be confusing without the accompanying presentation.

The frontend is a basic React app with Material UI components which communicates via websockets/SocketIO with the Java backend.

#### [![Build Status](https://ci.syscy.de/buildStatus/icon?job=SMTP+Tutorial+Frontend)](https://ci.syscy.de/job/SMTP%20Tutorial%20Frontend/) React Frontend
#### [![Build Status](https://ci.syscy.de/buildStatus/icon?job=SMTP+Tutorial+Backend)](https://ci.syscy.de/job/SMTP%20Tutorial%20Backend/) Java SocketIO Backend
