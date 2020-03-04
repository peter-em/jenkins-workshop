pipeline {
    agent {
        docker {
            image 'adoptopenjdk/maven-openjdk11'
            args '-v /root/.m2:/root/.m2'
        }
    }
    environment {
        PASSWORD = credentials('PASSWORD')
        USERNAME = credentials('USERNAME')
        CLIENT_SECRET = credentials('CLIENT_SECRET')
        CLIENT_ID = credentials('CLIENT_ID')
    }
    parameters {
        choice(
                name: 'PROFILE',
                choices: ['local', 'dev', 'custom'],
                description: 'Pick something pls nigga'
        )
        string(
                name: 'PRODUCT_NAME',
                defaultValue: 'Pjoter',
                description: 'WHY ARE YOU RUNNING?!'
        )
        string(
                name: 'SF_URL',
                defaultValue: '',
                description: 'Please provide valid url'
        )
    }
    stages {
        stage('Build') {
            steps {
                dir('simple-backend') {
                    sh 'mvn clean install'
                }
            }
        }
        stage('Deploy') {
            when {
                expression {
                    PROFILE == 'custom'
                }
            }
            steps {
                dir('simple-backend/target') {
                    sh """
                            java -jar app.jar --spring.profiles.active=$params.PROFILE \
                                --productName=$params.PRODUCT_NAME \
                                --salesforce.username=$USERNAME \
                                --salesforce.password=$PASSWORD \
                                --salesforce.clientId=$CLIENT_ID \
                                --salesforce.clientSecret=$CLIENT_SECRET \
                                --salesforce.url=$params.SF_URL
                    """
                }
            }
        }
        stage('Run app') {
            steps {
                dir('simple-backend/target') {
                    sh """
                        java -jar app.jar --spring.profiles.active=$params.PROFILE \
                            --productName=$params.PRODUCT_NAME
                    """
                }
            }
        }
    }
}
