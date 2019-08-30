def server = Artifactory.server "ArtifactDemo"

def buildInfo = Artifactory.newBuildInfo()

node{
stage('checkout & package'){
		checkout scm
		
		sh ("${MAVEN_HOME}//bin//mvn clean packge")
		}
		
stage('publish Artifact'){

		def uploadSpec = """{
			"files": [
				{
				  "pattern": "/target/*.jar",
				  "target": "libs-snapshot-local/",
				  "regexp": "true"
				}
			]
		}"""
		server.upload spec: uploadSpec, buildInfo: buildInfo
}

}
