def server = Artifactory.server "Artifact"

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
				  "pattern": "/target/*.jar",
				  "target": "libs-snapshot-local/",
				  "regexp": "true"
				}
			]
		}"""
		server.upload spec: uploadSpec, buildInfo: buildInfo
}

}
