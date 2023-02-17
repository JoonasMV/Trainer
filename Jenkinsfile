pipeline {
  agent any
  stages(){
    stage('Say hello'){
      steps{
        sh 'echo "Hello world!"'
      }
    }

    stage('Run unit tests'){
      steps{
        sh './gradlew test'
      }
    }
  }
}

