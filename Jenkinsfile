pipeline {
  agent any
  stages(){
    stage('Build & Install') {
      //Build the apk and the test apk which will run the tests on the apk
      sh 'chmod +x gradlew && ./gradlew --no-daemon --stacktrace clean :app:AndroidTest'
    }

    stage('Tests') {
      //Start all the existing tests in the test package 
      sh './gradlew --no-daemon --debug :app:AndroidTest' 
    }
  }
}

