pipeline {
    agent {
        docker {
            image 'adoptopenjdk/maven-openjdk11'
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
                    sh """java -jar app.jar \
                       --spring.profiles.active=$params.PROFILE \
                       --productName=$params.PRODUCT_NAME
                    """
                }
            }
        }
    }
}
