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
        sh 'chmod +x gradlew && ./gradlew createDebugUnitTestCoverageReport'
        step([$class: 'JacocoPublisher'
              //execPattern: 'build/jacoco/jacoco.exec',
              //classPattern: 'build/classes/main',
              //sourcePattern: 'src/main/java',
              //exclusionPattern: 'src/test*'
        ])
      }
      post {   always {     junit '*/target/surefire-reports/.xml'   } }
    }
  }
}


