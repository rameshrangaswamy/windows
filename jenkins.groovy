//def server = Artifactory.server "Artifact"

//def buildInfo = Artifactory.newBuildInfo()

node{
stage('checkout & package'){
		checkout scm
		
		sh ("mvn clean package")
		}
		
stage('publish Artifact'){

		def server = Artifactory.server 'Artifact'
		def uploadSpec = """{
			"files": [
				{
				  "pattern": "**/*.jar",
				  "target": "libs-snapshot-local"
				}
			]
		}"""

		def buildInfo = server.upload(uploadSpec)

		// Publish build information.
		buildInfo.env.capture = true
		server.publishBuildInfo(buildInfo)
}

}
