pipeline {
  agent any

  environment {
    ANDROID_HOME = '/var/jenkins_home/tools/android-sdk'
  }

  stages(){
    stage('Run unit tests'){

      steps{
        // sh 'export ANDROID_HOME=/var/jenkins_home/tools/android-sdk'
        sh 'chmod +x gradlew && ./gradlew test'
        sh 'chmod +x gradlew && ./gradlew testDebugUnitTest'
        // Cannot run Instrumented tests yet sh "chmod +x gradlew && ./gradlew connectedAndroidTest"
        sh 'chmod +x gradlew && ./gradlew createDebugUnitTestCoverageReport'
        step([$class: 'JacocoPublisher'])
      }
    }
  }
}


