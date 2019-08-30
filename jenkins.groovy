#!groovy
import groovy.sql.Sql
import groovy.json.JsonParserType
import groovy.json.JsonSlurperClassic
import groovy.json.JsonOutput
import java.io.*
import java.net.*
import org.apache.commons.io.FileUtils
import java.util.*;
import groovy.json.JsonSlurper

echo "Build.Groovy file successfully loaded!!"

@NonCPS
def buildInfo = Artifactory.newBuildInfo()
def server = Artifactory.server 'Artifact'

node{

stage('checkout & package'){
		checkout scm
		
		sh ("mvn clean package")
			pwd
		}
		
stage('publish Artifact'){

try
{
	dir(target/)
		{
		
		script{
		
		def uploadSpec = """{
			"files": [
				{
				  "pattern": "**/target/*.jar",
				  "target": "libs-snapshot-local/"
				}
			]
		}"""
		server.upload(uploadSpec)

		// Publish build information.
		buildInfo.env.capture = true
		
		server.publishBuildInfo(buildInfo)
		}
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
