def server = Artifactory.server "Artifact"

def buildInfo = Artifactory.newBuildInfo()

node{
stage('checkout & package'){
		checkout scm
		
		sh ("mvn clean package")
		}
		
stage('publish Artifact'){

try
		{
		
		script{
		//def server = Artifactory.server 'Artifact'
		def uploadSpec = """{
			"files": [
				{
				  "pattern": "C:/Program Files (x86)/Jenkins/workspace/JFROG_PIPE/target/windows-1.0-SNAPSHOT.jar",
				  "target": "libs-snapshot-local/",
				  "recursive": "false"
				}
			]
		}"""

		buildInfo = server.upload(uploadSpec)

		// Publish build information.
		buildInfo.env.capture = true
		server.publishBuildInfo(buildInfo)
		}
}
			catch(Exception exception) 
			{
				currentBuild.result = "FAILURE"
				throw exception
			}
			
			finally
			{
				println("Exiting Publish to Artifactory stage")
			}

}
}
