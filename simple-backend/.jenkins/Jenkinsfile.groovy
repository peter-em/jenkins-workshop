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
        string(
                name: 'URL',
                description: 'Pass the instance url - required only when profile is `custom`'
        )
        string(
                name: 'USERNAME',
                description: 'Pass the username - required only when profile is `custom`'
        )
        string(
                name: 'PASSWORD',
                description: 'Pass the password - required only when profile is `custom`'
        )
        string(
                name: 'CLIENT_ID',
                description: 'Pass the client id - required only when profile is `custom`'
        )
        string(
                name: 'CLIENT_SECRET',
                description: 'Pass the client secret - required only when profile is `custom`'
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
        stage('Run CUSTOM') {
            when {
                expression {
                    params.PROFILE == 'custom'
                }
            }
            steps {

                dir('simple-backend/target') {
                    sh """java -jar app.jar \
                       --spring.profiles.active=$params.PROFILE \
                       --productName=$params.PRODUCT_NAME \
                       --salesforce.url=$params.URL \
                       --salesforce.username=$params.USERNAME \
                       --salesforce.password=$params.PASSWORD \
                       --salesforce.clientId=$params.CLIENT_ID \
                       --salesforce.clientSecret=$params.CLIENT_SECRET \
                    """
                }
            }
        }
    }
}
