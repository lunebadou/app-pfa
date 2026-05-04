pipeline {
    agent any
    
    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['recette', 'prod'],
            description: 'Choisir l\'environnement de déploiement'
        )
    }
    
    environment {
        DOCKER_REGISTRY = 'lunebadou'
        DOCKER_IMAGE = 'app-pfa'
        DOCKER_COMPOSE_FILE = "docker-compose.${params.ENVIRONMENT}.yml"
        APP_CONTAINER_NAME = "app-pfa-${params.ENVIRONMENT}"
        DB_CONTAINER_NAME = "postgres-finance-${params.ENVIRONMENT}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "========== CHECKOUT =========="
                echo "Environnement cible: ${params.ENVIRONMENT}"
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo "========== BUILD MAVEN =========="
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                echo "========== SONARQUBE ANALYSIS =========="
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                    sh './mvnw sonar:sonar -Dsonar.projectKey=finance-erp -Dsonar.host.url=http://localhost:9000 -Dsonar.login=$SONAR_TOKEN'
                }
            }
        }
        
        stage('Docker Build') {
            steps {
                echo "========== DOCKER BUILD =========="
                sh 'docker build -t ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest .'
                sh 'docker tag ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}'
            }
        }
        
        stage('Docker Push') {
            steps {
                echo "========== DOCKER PUSH =========="
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:latest'
                    sh 'docker push ${DOCKER_REGISTRY}/${DOCKER_IMAGE}:${BUILD_NUMBER}'
                }
            }
        }
        
        stage('Deploy') {
            steps {
                echo "========== DEPLOIEMENT SUR ${params.ENVIRONMENT.toUpperCase()} =========="
                sh 'docker compose -f ${DOCKER_COMPOSE_FILE} -p finance-${params.ENVIRONMENT} down --remove-orphans || true'
                sh 'docker rm -f ${APP_CONTAINER_NAME} ${DB_CONTAINER_NAME} || true'
                sh 'docker compose -f ${DOCKER_COMPOSE_FILE} -p finance-${params.ENVIRONMENT} up -d'
                sh 'sleep 10'
                sh 'docker compose -f ${DOCKER_COMPOSE_FILE} -p finance-${params.ENVIRONMENT} ps'
            }
}
        
        stage('Health Check') {
            steps {
                echo "========== HEALTH CHECK =========="
                script {
                    def port = params.ENVIRONMENT == 'prod' ? '9899' : '9898'
                    // On définit le statut dans une variable pour gérer l'erreur proprement
                    def healthStatus = sh(script: """
                        echo "Vérification de l'application sur http://localhost:${port}/actuator/health"
                        for i in \$(seq 1 30); do
                            if curl -s http://localhost:${port}/actuator/health | grep -q "UP"; then
                                echo "✅ Application prête après \$((i*3)) secondes!"
                                exit 0
                            fi
                            echo "Attente du démarrage... (\$i/30)"
                            sleep 3
                        done
                        exit 1
                    """, returnStatus: true)

                    if (healthStatus != 0) {
                        error "❌ L'application n'a pas démarré à temps ou est instable."
                    }
                }
            }
        }
    }
    
    post {
        success {
            echo "✅ Pipeline terminé avec succès!"
            script {
                def port = params.ENVIRONMENT == 'prod' ? '9899' : '9898'
                echo "Application accessible sur: http://localhost:${port}"
            }
        }
        failure {
            echo "❌ Pipeline échoué!"
            // Affiche les logs du conteneur d'application pour comprendre le crash
            sh 'docker compose -f ${DOCKER_COMPOSE_FILE} logs --tail=100 ${APP_CONTAINER_NAME} || true'
        }
        always {
            echo "Fin de l'exécution du pipeline"
        }
    }
}