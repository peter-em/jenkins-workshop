pipeline {
    agent {
        docker {
            image 'adoptopenjdk/maven-openjdk11'
            args '-v /root/.m2/root/.m2'
        }
    }
    parameters {
        choice(name: 'PROFILE', choices: ['local', 'dev', 'others'], description: 'Pick something pls nigga')
        string(name: 'PRODUCT_NAME', defaultValue: 'Pjoter', description: 'WHY ARE YOU RUNNING?!')
    }
    stages {
        stage('Build') {
            steps {
                dir('simple-backend') {
                    sh 'mvn clean install'
                }
            }
        }
        stage('Run app') {
            steps {
                dir('simple-backend/target') {
                    sh 'java -jar app.jar'
                }
            }
        }
    }
}
