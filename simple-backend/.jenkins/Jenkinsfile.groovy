pipeline {
  agent {
    docker {
      image 'DOCKER_IMAE_NAME'
    }
  }
  stages {
    stage('FIRST STAGE') {
      steps {
        // we're gonna do something
        sh 'ls -l'
        sh 'pwd'
      }
    }
    stage('Build') {
      steps {
        dir('simple-backend') {
          sh 'mvn clean install'
        }
      }
    }
  }
}
