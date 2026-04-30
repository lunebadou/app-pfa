pipeline {
    agent any
    
    environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21.0.10'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvnw clean package -DskipTests'
            }
        }
    }
    
    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
