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
				  "pattern": "C:/Jenkins/workspace/JFROG_PIPE/target/windows-1.0-SNAPSHOT.jar",
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
