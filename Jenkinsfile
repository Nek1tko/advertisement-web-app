pipeline {
    agent any
    
    tools {
        nodejs 'nodejs'
        maven 'Default'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/Nek1tko/addvertisement-web-app'
            }
        }
        
        stage('Build') {
            steps {
                dir("ui") {
                    sh 'npm install'
                }
            }
        }
        
        stage('Integration Tests') {
            parallel {
                stage('Front Tests') {
                    steps {
                        dir("ui") {
                            sh 'npm run test:integration -- --runInBand --detectOpenHandles'
                        }
                    }
                }
                
                stage('Back Tests') {
                    steps {
                        dir("service") {
                            sh 'mvn -Dtest="com/spbstu/edu/advertisement/controller/**" clean test'
                        }
                    }
                }
            }
        }
    }
}