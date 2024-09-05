pipeline {
    agent any

    environment {
        // Definir variables de entorno necesarias
        DEPENDENCY_TRACK_URL = 'http://localhost:8081' // URL donde está corriendo Dependency-Track
        API_KEY = 'odt_B5Mg3ngUw9MoYI9bxsQR9AcqL5b7PeQG' // Reemplaza con tu clave API de Dependency-Track
        PROJECT_NAME = 'JavaLoginApp'
        PROJECT_VERSION = '1.0'
    }

    stages {
        stage('Checkout') {
            steps {
                // Clonar el repositorio
                git branch: 'main',
                     'https://github.com/agueaguilar/LoginVulnerabilidad.git',
                     credentialsId: 'github-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'
            }
        }

        stage('Build and Package') {
            steps {
                // Usar Maven para compilar y empaquetar el código
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Deploy') {
            steps {
                // Desplegar usando Docker Compose
                script {
                    sh 'docker-compose up --build -d'
                }
            }
        }

        stage('Upload to Dependency-Track') {
            steps {
                // Crear un proyecto en Dependency-Track (si no existe)
                script {
                    sh """
                    curl -X PUT "$DEPENDENCY_TRACK_URL/api/v1/project" \\
                    -H "Content-Type: application/json" \\
                    -H "X-Api-Key: $API_KEY" \\
                    -d '{
                          "name": "$PROJECT_NAME",
                          "version": "$PROJECT_VERSION"
                        }'
                    """
                }

                // Subir el archivo JAR a Dependency-Track para análisis
                script {
                    sh """
                    curl -X POST "$DEPENDENCY_TRACK_URL/api/v1/scan" \\
                    -H "X-Api-Key: $API_KEY" \\
                    -F "projectName=$PROJECT_NAME" \\
                    -F "projectVersion=$PROJECT_VERSION" \\
                    -F "file=@target/LoginApp.jar"
                    """
                }
            }
        }

        stage('Check Scan Results') {
            steps {
                // Obtener el resultado del escaneo desde Dependency-Track
                script {
                    sh """
                    curl -X GET "$DEPENDENCY_TRACK_URL/api/v1/project/lookup?name=$PROJECT_NAME&version=$PROJECT_VERSION" \\
                    -H "X-Api-Key: $API_KEY"
                    """
                }
            }
        }

        stage('Generate Report') {
            steps {
                // Generar el reporte PDF desde JSON (si es necesario)
                script {
                    sh 'pandoc -s report.json -o report.pdf'
                }
            }
        }

        stage('Send Report') {
            steps {
                // Enviar el reporte por correo electronico
                script {
                    mail bcc: '', body: 'Find attached the vulnerability report.', subject: 'Vulnerability Report', to: 'aguedaaguilar14@gmai.com', attachmentsPattern: 'report.pdf'
                }
            }
        }
    }
}
