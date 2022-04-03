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

        stage('System Tests') {
            steps {
                sh "docker compose up -d"
                dir("service") {
                    sh 'mvn -Dtest="com/spbstu/edu/advertisement/systemtest/**" clean test'
                }
            }
        }
    }
}
