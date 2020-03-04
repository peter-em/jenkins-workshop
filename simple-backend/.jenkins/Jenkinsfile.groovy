pipeline {
    agent {
        docker {
            image 'adoptopenjdk/maven-openjdk11'
            args '-v /root/.m2/root/.m2'
        }
    }
    stages {
        stage('FIRST STAGE') {
            steps {
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
