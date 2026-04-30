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
                sh './mvnw clean package -DskipTests'
            }
        }
        
        stage('Docker Build') {
            steps {
                sh 'docker build -t lunebadou/app-pfa:latest .'
            }
        }
        
        stage('Docker Push') {
            steps {
                sh '''
                    docker login -u $DOCKER_USER -p $DOCKER_PASS
                    docker push lunebadou/app-pfa:latest
                '''
            }
        }
    }
}
