pipeline {
  agent any

  environment {
    ANDROID_HOME = '/var/jenkins_home/tools/android-sdk'
  }

  stages(){
    stage('Say hello'){
      steps{
        sh 'echo "Hello world!"'
      }
    }

    stage('Run unit tests'){
      steps{
        // sh 'export ANDROID_HOME=/var/jenkins_home/tools/android-sdk'
        sh 'chmod +x gradlew && ./gradlew test'
      }
    }
  }
}

