pipeline {
  agent { 
    node { label 'android' }                     (*)
  }

  stages {                                       (**)
    stage('Test') {
      parallel {                                 (***)

        stage('Unit Test') {
          steps {
            // Execute your Unit Test
            sh './gradlew testStagingDebug'
          }
        }
      }
    }

    stage('UI Testing') {
      steps {
        script {                                  (****)                          
          if (currentBuild.result == null         
              || currentBuild.result == 'SUCCESS') {  
          // Start your emulator, testing tools
          sh 'emulator @jenkins-emu 
          sh 'appium &'  
     
          // You're set to go, now execute your UI test
          sh 'rspec spec -fd'
          }
        }
      }
    }

  }

  post {                                           (*****)
    always {

      // And kill the emulator?
      sh 'adb emu kill'
    }
  }

}