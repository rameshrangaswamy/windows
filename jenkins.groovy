node{
stage('checkout & package'){
		checkout scm		
		sh ("mvn clean package")
		}		
stage('publish Artifact'){
	try
	{	    def buildInfo = Artifactory.newBuildInfo()
	            def serverId
		    rtUpload (
			serverId: "Artifact",
			spec:
			    """{
			      "files": [
				{
				  "pattern": "C:/Jenkins/workspace/ArtifactDownload/libs-snapshot-local/demo-b490.tar",
				  "target": "libs-snapshot-local/"
				}
			     ]
			    }"""
		    )
		   buildInfo.env.capture = true
		   serverId.publishBuildInfo(buildInfo)
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
