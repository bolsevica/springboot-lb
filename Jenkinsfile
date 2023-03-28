pipeline {
environment {
registry = "bolsevica/springboot"
registryCredential = 'jenkins-dockerhub'
dockerImage = ''
}
agent{ label 'jenkins-slave' }
stages {
stage('Cloning Git') {
steps {
git branch: "${env.BRANCH_NAME}", url: "${env.REPO_URL}"
}
}
stage('Compile') {
steps {
sh './gradlew build -x test'
 sh 'pwd'
  sh 'ls -lah'
}
}
stage('Test') {
steps {
sh './gradlew build -x test'
 sh 'pwd'
  sh 'ls -lah'
}
}
stage('Building image') {
steps{
script {
dockerImage = docker.build registry + ":$BUILD_NUMBER"
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
//stage('Cleaning up') {
//steps{
//sh "docker rmi $registry:$BUILD_NUMBER"
//}
//}
}
}
