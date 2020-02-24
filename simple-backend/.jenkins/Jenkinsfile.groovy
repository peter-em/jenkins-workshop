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
    environment {
        DEV_USERNAME = credentials('sf-dev-username')
        DEV_PASSWORD = credentials('sf-dev-password')
        DEV_CLIENT_ID = credentials('sf-dev-client-id')
        DEV_CLIENT_SECRET = credentials('sf-dev-client-secret')
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
        stage('Run DEV') {
            when {
                expression {
                    params.PROFILE == 'dev'
                }
            }
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
