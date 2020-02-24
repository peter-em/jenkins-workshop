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
        stage('Run') {
            steps {
                dir("simple-backend/target") {
                    sh "java -jar app.jar"
                }
            }
        }
    }
}
