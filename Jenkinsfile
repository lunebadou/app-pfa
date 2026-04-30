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
        
        stage('Docker Build') {
            steps {
                bat 'docker build -t lunebadou/app-pfa:latest .'
            }
        }
        
        stage('Docker Push') {
            steps {
                bat '''
                    docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                    docker push lunebadou/app-pfa:latest
                '''
            }
        }
    }
}
pipeline {
    agent any
    
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
        
        stage('Docker Build') {
            steps {
                bat 'docker build -t lunebadou/app-pfa:latest .'
            }
        }
        
        stage('Docker Push') {
            steps {
                bat '''
                    docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                    docker push lunebadou/app-pfa:latest
                '''
            }
        }
    }
}
