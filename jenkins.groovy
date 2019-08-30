def server = Artifactory.server "Artifact"
def MAVEN_HOME
def buildInfo = Artifactory.newBuildInfo()

node{
stage('checkout & package'){
		checkout scm
		
		sh ("mvn clean packge")
		}
		
stage('publish Artifact'){

		def uploadSpec = """{
			"files": [
				{
				  "pattern": "jenkins-pipeline-examples/resources/.*A[A-z]tifactory.*.zip",
				  "target": "libs-snapshot-local/",
				  "regexp": "true"
				}
			]
		}"""
		server.upload spec: uploadSpec, buildInfo: buildInfo
}

}
