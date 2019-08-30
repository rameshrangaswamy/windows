def server = Artifactory.server "Artifact"

def buildInfo

node{
stage('checkout & package'){
		checkout scm
		
		sh ("mvn clean package")
		}
		
stage('publish Artifact'){

		def uploadSpec = """{
			"files": [
				{
				  "pattern": "C:/Program Files (x86)/Jenkins/workspace/JFROG_PIPE/target/windows-1.0-SNAPSHOT.jar",
				  "target": "libs-snapshot-local/",
				  "regexp": "true"
				}
			]
		}"""
		server.upload spec: uploadSpec, buildInfo: buildInfo
}

}
