# MyHealthyLife Mobile Application (Android)

## 1.User Interface

This repository contains an Android application which allow the user to manage its **MyHealthyLife** profile.

This application has two main component:

- a *WebView* which contains the pages of the [Web Application](https://github.com/MyHealthyLife/MyHealthyLifeWeb "").
- A background service which read the amount of steps from the step-counter of the smart-phone and sends it automatically two times a day.

A compiled version of this App is available at: [https://github.com/MyHealthyLife/MyHealthyLifeMobile/releases/tag/0.101](https://github.com/MyHealthyLife/MyHealthyLifeMobile/releases/tag/0.101 "")

## 2.Application Implementation

#### 2.1 WebView

The *"Home"* of the App is a **WebView** which contains the whole web application, the web application is written using the **Angular JS** framework, which allows the programmer to easily handle asynchronous REST call ad the data manipulation. Also all the all the computation is managed in the client side; this allows us to encapsulate it in the Android application without changing the web application source code.

The advantages of having the pages of the web application stored in the smartphone memory are:

- The application loading time is reduced, in-fact the page is loaded from the phone memory and only the REST call are sent to the beck-end.
- The network usage is reduced.

When the user open the App the first time a login page is proposed to the user. The user inserts his data and the login in validate on the centric1 service. The login information are stored in the memory and loaded in the **WebView** during the application start-up, this information can be used by the webapp in order to retrieve and send information to the *MyHealthyLife* beck-end.

#### 2.2 Background service

As mentioned above, this application send automatically the amount of steps to the beck-end two times a day. In order to do that it relies on the **Android Alarm Manager**, which is an Android system service which executes tasks at precise time interval. In this particular case the App configure the *Alarm Manager* in order to execute a task, which first read the value of the step-counter of the phone and then makes a REST call, every 12 hour. 