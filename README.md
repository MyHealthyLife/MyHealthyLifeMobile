# MyHealthyLife Mobile Application (Android)

## 1.User Interface

This repository contains an Android application which allow the user to manage its **MyHealthyLife** profile.

This application has two main component:

- a *WebView* which contains the pages of the [Web Application](https://github.com/MyHealthyLife/MyHealthyLifeWeb "").
- A background service which read the amount of steps from the step-counter of the smart-phone and sends it automatically two times a day.

## 2.Application Implementation

#### 2.1 WebView

The *"Home"* of the App is a **WebView** which contains the whole web application, the web application is written using the **Angular JS** framework, which allows the programmer to easily handle asynchronous REST call ad the data manipulation. Also all the all the computation is managed in the client side; this allows us to encapsulate it in the Android application without changing the web application source code.

The advantages of having the pages of the web application stored in the Smart Phone memory are:

- The application loading time is reduced, in-fact the page is loaded from the phone memory and only the REST call are sent to the beck-end.
- The network usage is reduced.

When the user open the App the first time a login page is proposed to the user. The user inserts his data and the login in validate on the centric1 service. The login information are stored in the memory and loaded in the **WebView** during the application start-up, this information can be used by the webapp in order to retrieve and send information to the *MyHealthyLife* beck-end.