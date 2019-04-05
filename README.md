# NYT
[![Build Status](https://travis-ci.org/amsiddh/NYT.svg?branch=master)](https://travis-ci.org/amsiddh/NYT) [![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=NYT&metric=alert_status)](https://sonarcloud.io/dashboard?id=NYT)

The app displays a list the NY Times Most Popular Articles in list and on tap of any article its details infomation is shown. 

### Libraries used 
* AppCompat & RecyclerView
* Data Binding & LiveData
* Dagger 2
* Retrofit 2
* Glide
* Circle ImageView
* Sonarqube
* Junit
* Espresso

### MVVM - Model View ViewModel
This app uses MVVM architechture, this pattern has recently started to gain popularity due to the release of the [data binding library](https://developer.android.com/tools/data-binding/guide.html). In this case, ViewModels retrieve data from the model when requested from the view via data binding. With this pattern, Activities and Fragments become very lightweight. Moreover, writting unit tests becomes easier because the ViewModels are decoupled from the view. 
Additionally LiveData is also used, as it's lifecycle-aware and is an observable data holder class. It's used for communication between ViewModels and UI views (activities and fragments). There is no reference to the View from a ViewModel so the communication between them happen via a subscriptions and it handles the configurations changes by itself.

### Travis for Continious Integration
This app also uses [Travis](https://travis-ci.org/) for a continuous integration to build the app as code is pushed to repository. It's configuration details are present in [.travis.yml](https://github.com/amsiddh/NYT/blob/master/.travis.yml)

### Sonarqube
This app uses the Sonarqube to monitor and analyze code quality and technical debts. The complete analysed report is shown in there dashboard with it's details to undarstand it. The process is automated as code pushed to repository, Travis CI will build the applicaiton on successful build Sonar scanner analysis is being initiated. Its configuration details are present in [sonar-project.properties](https://github.com/amsiddh/NYT/blob/master/sonar-project.properties) and [.travis.yml](https://github.com/amsiddh/NYT/blob/master/.travis.yml)

### Command line scripts
From command line move to project root and use below commands
* gradlew lint  - For Lint check, the detail report will be generated in /app/build/outputs/lint/lint.html
* gradlew build - For building the app

