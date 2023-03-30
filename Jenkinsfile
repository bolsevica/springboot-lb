pipeline {
environment {
registry = "bolsevica/springboot"
registryCredential = 'jenkins-dockerhub'
dockerImage = ''
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
boolean testPassed = true
stage('Test') {
steps {
  try{
        sh './gradlew test'
    }catch (Exception e){
        testPassed = false
    }
   junit 'build/reports/**/*.xml'
}
}
stage('Building image') {
  if(testPassed){
steps{
script {
dockerImage = docker.build registry + ":$BUILD_NUMBER"
}
}
}
}
stage('Deploy our image') {
steps{
script {
docker.withRegistry( '', registryCredential ) {
dockerImage.push()
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
stage('Cleaning up') {
steps{
sh "docker rmi $registry:$BUILD_NUMBER"
}
}
}
}
