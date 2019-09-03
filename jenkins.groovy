node{
stage('checkout & package'){
		checkout scm	
	
		sh ("mvn clean package -DskipTests")

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
				  "pattern": "C:/Jenkins/workspace/JFROG_PIPE/target/windows-*-SNAPSHOT.jar",
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
	stage('pom read'){
		//def readMavenPom 
		def pom = readMavenPom file: 'pom.xml'
		sh '''#!/bin/bash +x
		echo "artifact id :: $pom.version"
		'''
	}
	
}
