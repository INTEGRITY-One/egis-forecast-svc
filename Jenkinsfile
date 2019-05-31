pipeline {
  agent {
  	label 'maven'
  }
  stages {
    stage('Create Artifact BuildConfig') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector("bc", "forecast-svc-artifact-build").exists();
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.create(
               '{"apiVersion": "build.openshift.io/v1","kind": "BuildConfig","metadata":{"name": "forecast-svc-artifact-build","namespace": "egis","annotations":{"description": "Defines how to build the application artifact"},"labels":{"app": "forecast-svc"}},"spec":{"runPolicy": "Serial","source":{"git":{"uri": "https://github.com/INTEGRITY-One/egis-forecast-svc.git"},"type": "Git"},"strategy":{"type": "Source","sourceStrategy":{"from":{"kind": "ImageStream","name": "openjdk-11-rhel8","namespace": "openshift"}}},"output":{"to":{"kind": "ImageStreamTag","name": "forecast-svc-artifact:latest"}},"postCommit":{"script": ""},"successfulBuildsHistoryLimit": 3,"failedBuildsHistoryLimit": 3}}'
            )
          }
        }
      }
    }
    stage('Create Image BuildConfig') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector("bc", "forecast-svc-image-build").exists();
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.create(
               '{"apiVersion": "build.openshift.io/v1","kind": "BuildConfig","metadata":{"name": "forecast-svc-image-build","namespace": "egis","annotations":{"description": "Defines how to build the application image"},"labels":{"app": "forecast-svc"}},"spec":{"runPolicy": "Serial","source":{"dockerfile": "FROM graalvm:latest\\nVOLUME /tmp\\nARG JAR_FILE\\nWORKDIR /usr/local/\\nCOPY forecast-svc-1.0-SNAPSHOT.jar ./forecast-svc.jar\\nCOPY forecast-svc-1.0-SNAPSHOT-runner.jar ./forecast-svc-runner.jar\\nCOPY lib ./lib\\nENTRYPOINT [\\"java\\",\\"-Djava.security.egd=file:/dev/./urandom\\",\\"-jar\\",\\"/usr/local/forecast-svc-runner.jar\\"]\\nEXPOSE 8080","images":[{"from":{"kind": "ImageStreamTag","namespace": "egis","name": "forecast-svc-artifact:latest"},"paths":[{"sourcePath": "/tmp/src/target/forecast-svc-1.0-SNAPSHOT.jar","destinationDir": "."},{"sourcePath": "/tmp/src/target/forecast-svc-1.0-SNAPSHOT-runner.jar","destinationDir": "."},{"sourcePath": "/tmp/src/target/lib","destinationDir": "."}]}]},"strategy":{"dockerStrategy":{"from":{"kind": "ImageStreamTag","name": "graalvm:latest","namespace": "openshift"}}},"output":{"to":{"kind": "ImageStreamTag","name": "forecast-svc:latest"}},"postCommit":{"script": ""},"successfulBuildsHistoryLimit": 3,"failedBuildsHistoryLimit": 3}}'
            )
          }
        }
      }
    }
    stage('Execute Artifact Build') {
      steps {
        script {
          openshift.withCluster() {
            openshift.selector("bc", "forecast-svc-artifact-build").startBuild("--wait")
          }
        }
      }
    }
    stage('Execute Image Build') {
      steps {
        script {
          openshift.withCluster() {
            openshift.selector("bc", "forecast-svc-image-build").startBuild("--wait")
          }
        }
      }
    }
    stage('Promote to DEV') {
      steps {
        script {
          openshift.withCluster() {
            openshift.tag("forecast-svc:latest", "forecast-svc:dev")
          }
        }
      }
    }
	stage('Create DEV') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector('dc', 'forecast-svc-dev').exists()
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newApp("forecast-svc:latest", "--name=forecast-svc-dev").narrow('svc').expose()
          }
        }
      }
    }
    stage('Promote to STAGE') {
      steps {
        script {
          openshift.withCluster() {
            openshift.tag("forecast-svc:dev", "forecast-svc:stage")
          }
        }
      }
    }
    stage('Create STAGE') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector('dc', 'forecast-svc-stage').exists()
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newApp("forecast-svc:stage", "--name=forecast-svc-stage").narrow('svc').expose()
          }
        }
      }
    }
  }
}