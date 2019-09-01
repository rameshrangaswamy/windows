node{
stage('checkout & package'){
		checkout scm		
		sh ("mvn clean package")
		}		
stage('publish Artifact'){
	try
	{	   
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
		   rtPublishBuildInfo (
		        serverId: "Artifact"
		    )
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
