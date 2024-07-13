pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/rainbow0x/Prueba.git'
        IMAGE_NAME = 'flaskapp'
        ARTIFACTORY_URL = 'https://pruebas123.jfrog.io/ui/packages/docker:%2F%2Fjenkins%2Fjenkins'
        ARTIFACTORY_CREDENTIALS = 'jfrog-credentials'
    }

    stages {
        stage('Clone repository') {
            steps {
                git branch: 'main', url: "${REPO_URL}"
            }
        }
        stage('Build Docker image') {
            steps {
                script {
                    docker.build("${IMAGE_NAME}")
                }
            }
        }
        stage('Push to Artifactory') {
            steps {
                script {
                    docker.withRegistry("${ARTIFACTORY_URL}", "${ARTIFACTORY_CREDENTIALS}") {
                        docker.image("${IMAGE_NAME}").push()
                    }
                }
            }
        }
        stage('Deploy container') {
            steps {
                script {
                    def ansibleInventory = """
                    [127.0.0.1]
                    192.168.1.100
                    192.168.1.101
                    """

                    writeFile file: 'inventory.ini', text: ansibleInventory

                    def deployPlaybook = """
                    - hosts: 127.0.0.1
                      become: yes
                      tasks:
                        - name: Pull Docker image
                          docker_image:
                            name: ${IMAGE_NAME}
                            source: pull
                            force_source: yes

                        - name: Run Docker container
                          docker_container:
                            name: ${IMAGE_NAME}
                            image: ${IMAGE_NAME}
                            state: started
                            restart_policy: always
                            ports:
                              - "5000:5000"
                    """

                    writeFile file: 'deploy.yaml', text: deployPlaybook

                    ansiblePlaybook playbook: 'deploy.yaml', inventory: 'inventory.ini'
                }
            }
        }
    }
}