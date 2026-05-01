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
                // Ajout des droits d'exécution sur le wrapper Maven
                sh 'chmod +x mvnw'
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
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push lunebadou/app-pfa:latest'
                }
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'docker compose down || true'
                sh 'docker compose up -d'
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline terminé avec succès !'
        }
        failure {
            echo 'Pipeline échoué !'
        }
    }
}
