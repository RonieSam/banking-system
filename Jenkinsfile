pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/RonieSam/banking-system.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t banking-system .'
            }
        }

        stage('Run Docker Compose') {
            steps {
                // Stop & remove old containers (ignoring errors if not present)
                sh '''
                    docker stop banking-system || true
                    docker rm banking-system || true
                    docker stop graphite || true
                    docker rm graphite || true
                    docker-compose down || true
                    docker-compose up -d
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
