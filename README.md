# Github Name Directory   ![alt text](https://github.com/johnjake/members-directory/blob/master/app/src/main/res/drawable/ic_github_logo.png)

members-directory app is about users directory listing features are, list of Github users, including night mode, user profile, search user or members and offline listing and search.

    IDE: Android Studio 4.1 Runtime Version: 1.8.0 VM: OpenJDK 64-Bit

    Gradle Version: 4.1.0
    Kotlin Version: 1.3.75
    Groovy Version: 2.5.11
    Gradle Type: DLS

    Language: Kotlin

    Architechture Pattern: MVVM
    
    3rd Party
    Dependency Injection: Koin-2.0.1
    Image Stream: Coil-0.13.0
    Http Client: OkHttp-4.9.0
    Http Client: Retrofit-2.9.0
    Concurrency Library: Coroutine-1.3.7
    Reactive Extensions: RxJava2 (2.2.10)
    RecyclerView Paging: Paging3 (3.0.0-alpha07)
    Navigation Component: 2.3.1

USER API: Github users list can be obtained from https://api.github.com/users?since=0  in JSON format, example [username: tawk](https://api.github.com/users?since=9743939).

PROFILE API: Profile info can be obtained from https://api.github.com/users/[username] in JSON format, example [profile: tawk](https://api.github.com/users/tawk).

   
   Offline Mode:
   
  ![alt text](https://github.com/johnjake/members-directory/blob/master/local_storage.png)



