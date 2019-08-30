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
import org.jfrog.hudson.pipeline.common.types.ArtifactoryServer

echo "Build.Groovy file successfully loaded!!"

def rtMaven = Artifactory.newMavenBuild()
def server = Artifactory.server 'Artifact'
def buildInfo

node{

stage('checkout & package'){
		checkout scm
		
		sh ("mvn clean package")
		}
		
stage('publish Artifact'){

try
		{
		
		script{
		
		rtMaven.tool = 'MAVEN_HOME'
		
		rtMaven.deployer server: server, releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local'
		
		def uploadSpec = """{
			"files": [
				{
				  "pattern": "**/target/*.jar",
				  "target": "libs-snapshot-local/"
				}
			]
		}"""
		server.upload spec: uploadSpec, buildInfo: buildInfo 
		
		buildInfo.env.capture = true
		
		server.publishBuildInfo buildInfo
		
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
