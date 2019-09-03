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
		sh '''#!/bin/bash +x
		pom=`cat pom.xml | grep "artifactId>win" | cut -d '<' -f2 | cut -d '>' -f2`
		echo "artifact id :: $pom.version"
		echo "${pom}"
		'''
	}
	
}
