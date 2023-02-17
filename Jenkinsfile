pipeline {
  agent any

  environment {
    ANDROID_HOME = '/var/jenkins_home/tools/android-sdk'
  }

  stages(){
    stage('Run unit tests'){
      steps{
        // sh 'export ANDROID_HOME=/var/jenkins_home/tools/android-sdk'
        //junit "*/build/test-results/.xml"
        sh 'chmod +x gradlew && ./gradlew test'
        sh 'chmod +x gradlew && ./gradlew testDebugUnitTest'
        sh 'chmod +x gradlew && ./gradlew createDebugUnitTestCoverageReport'
        step([$class: 'JacocoPublisher'])
      }
      post {   always {    junit '*/build/reports/coverage/debug/.xml'   } }
    }
  }
}


