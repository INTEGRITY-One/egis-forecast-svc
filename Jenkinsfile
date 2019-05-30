pipeline {
  agent {
  	label 'maven'
  }
  stages {
    stage('Create BuildConfig') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector("bc", "forecast-svc-buildconfig").exists();
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.create(
               '{"apiVersion": "build.openshift.io/v1","kind": "BuildConfig","metadata": {"annotations": {"description": "Defines how to build the application"},"labels": {"app": "forecast-svc"},"name": "forecast-svc-buildconfig","namespace": "egis"},"spec": {"failedBuildsHistoryLimit": "5","output": {"to": {"kind": "ImageStreamTag","name": "forecast-svc:latest"}},"postCommit": {"script": ""}, "runPolicy": "Serial","source": {"git": {"uri": "https://github.com/INTEGRITY-One/egis-forecast-svc.git"},"type": "Git"},"strategy": {"type": "Source","sourceStrategy": {"from": {"kind": "ImageStream","namespace": "openshift","name": "java"}}},"successfulBuildsHistoryLimit": "5"}}'
            )
          }
        }
      }
    }
    stage('Execute Build') {
      steps {
        script {
          openshift.withCluster() {
            openshift.selector("bc", "forecast-svc-buildconfig").startBuild("--wait")
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