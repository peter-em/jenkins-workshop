pipeline {
    agent {
        docker {
            image 'adoptopenjdk/maven-openjdk11'
        }
    }
    stages {
        stage('Build') {
            steps {
                dir("simple-backend") {
                    sh "mvn clean install"
                }
            }
        }
    }
}