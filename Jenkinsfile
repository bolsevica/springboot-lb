pipeline {
environment {
registry = "bolsevica/springboot"
registryCredential = 'jenkins-dockerhub'
dockerImage = ''
testPassed = 'true'
}
agent{ label 'jenkins-slave' }
stages {

stage('Build') {
steps {
  sh 'chmod +x gradlew'
sh './gradlew build -x test'
 sh 'pwd'
  sh 'ls -lah'
}
}
stage('Test') {
steps {
  script{
     try{
        sh './gradlew test'
    }catch (Exception e){
        testPassed = false
    }
  }
}
}
stage('Building Docker image') {
steps{
script {
   if(testPassed){
dockerImage = docker.build registry + ":$BUILD_NUMBER"
}
}
}
}
stage('Run image') {
steps{
script {
dockerImage.run('-p 8081:8083')
}
}
}
stage('Perform automated API tests') {
steps{
script {
build job: 'API tests'
}
}
}
stage('Deploy our image to DockerHub') {
steps{
script {
docker.withRegistry( '', registryCredential ) {
dockerImage.push()
}
}
}
}
stage('Cleaning up') {
steps{
sh "docker rmi $registry:$BUILD_NUMBER"
}
}
}
}