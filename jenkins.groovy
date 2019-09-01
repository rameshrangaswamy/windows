node{
stage('checkout & package'){
		checkout scm		
		sh ("mvn clean package")
		sh ("tar -cvf "$env.BUILD_NUMBER.tar" C:/Jenkins/workspace/JFROG_PIPE/target/*-SNAPSHOT.jar")
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
				  "pattern": "C:/Jenkins/workspace/JFROG_PIPE/target/*.tar",
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
