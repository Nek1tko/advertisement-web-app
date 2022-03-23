pipeline {
    agent any
    
    tools {nodejs "nodejs"}

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
        
        stage('Unit Test') {
            steps {
                dir("ui") {
                    sh 'npm run test:unit -- --runInBand --detectOpenHandles -u'
                }
            }
        }
        
        stage('Integration Test') {
            steps {
                dir("ui") {
                    sh 'npm run test:integration -- --runInBand --detectOpenHandles'
                }
            }
        }
    }
}
