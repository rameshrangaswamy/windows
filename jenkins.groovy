//def server = Artifactory.server "Artifact"

//def buildInfo

node{
stage('checkout & package'){
		checkout scm
		
		sh ("mvn clean package")
		}
		
stage('publish Artifact'){

		rtUpload (
		    serverId: "Artifact",
		    spec:
			"""{
			  "files": [
			    {
			      "pattern": "C:/Program Files (x86)/Jenkins/workspace/JFROG_PIPE/target/windows-1.0-SNAPSHOT.jar",
			      "target": "bazinga-repo/froggy-files/"
			    }
			 ]
			}"""
		)
}

}
