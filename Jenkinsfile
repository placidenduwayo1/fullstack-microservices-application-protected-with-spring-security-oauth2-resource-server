pipeline {
    agent any //we tell jenkins to use any available agent
    tools {
        maven 'Maven-3.9.6'
        jdk 'Java-17'
        nodejs 'Nodejs-16.14.2'
    }
    stages {
                
        stage ('Build-Backend') {
            steps {
                dir('./backend-frontend-app/utils-modules/microservices-config-service'){ //enter in folder to locate pom.xml
                    sh 'mvn -B -DskipTests clean verify'
                }
                dir('./backend-frontend-app/utils-modules/microservices-registration-service'){
                    sh 'mvn -B -DskipTests clean verify'
                }
                dir ('./backend-frontend-app/utils-modules/backend-frontend-gateway-service'){
                     sh 'mvn -B -DskipTests clean verify'
                }
                dir('./backend-frontend-app/businesse-ms-modules/clean-archi-business-service-address/'){
                      sh 'mvn -B -DskipTests clean verify'
                }
                dir('./backend-frontend-app/businesse-ms-modules/clean-archi-business-service-company/'){
                     sh 'mvn -B -DskipTests clean verify'
                }
                dir('./backend-frontend-app/businesse-ms-modules/clean-archi-business-service-employee/'){
                     sh 'mvn -B -DskipTests clean verify'
                }
                dir('./backend-frontend-app/businesse-ms-modules/clean-archi-business-service-project/'){
                    sh 'mvn -B -DskipTests clean verify'
                }
            }
        }
        stage('NPM install'){
            steps{
                dir('./backend-frontend-app/angular-project-front'){//enter in angular project to run some commands
                    sh 'npm install'
                    sh 'npm cache verify'
                }
            }
        }
        stage('Build-frontend'){
            steps{
                dir('./backend-frontend-app/angular-project-front'){//enter in angular project to run some commands
                    sh 'npm run ng build'
                }
            }
        }
    }
}
