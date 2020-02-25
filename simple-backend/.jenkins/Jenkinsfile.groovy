pipeline {
  agent any
  stages {
    stage('FIRST STAGE') {
      steps {
        // we're gonna do something
        sh 'ls -l'
        sh 'pwd'
      }
    }
  }
}
