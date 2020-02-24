pipeline {
    agent {
        docker {
            image 'adoptopenjdk/maven-openjdk11'
            args '-v /root/.m2:/root/.m2'
        }
    }
    parameters {
        choice(
                name: 'PROFILE',
                choices: ['local', 'dev', 'custom'],
                description: 'Choose spring profile'
        )
        string(
                name: 'PRODUCT_NAME',
                description: 'Pass the product name'
        )
    }
    stages {
        stage('Test') {
            steps {
                sh 'ls -al'
            }
        }
        stage('Build') {
            steps {
                dir('simple-backend') {
                    sh 'mvn clean install'
                }
            }
        }
        stage('Run') {
            steps {
                dir('simple-backend/target') {
                    sh """java -jar app.jar \
                       --spring.profiles.active=$params.PROFILE \
                       --productName=$params.PRODUCT_NAME
                    """
                }
            }
        }
    }
}
