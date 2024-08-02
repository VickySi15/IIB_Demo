pipeline {
   agent any

   stages {
      stage('checkout code') {
         steps {
            deleteDir()
            git 'https://github.com/VickySi15/IIB_Demo.git'
         }
      }
      stage('build bar') {
        steps {
        bat label: '', script: '''
echo off
set projectName=IIB_Demo
"C:\\Program Files\\IBM\\ACE\\12.0.12.4\\tools\\mqsicreatebar.exe" -data . -b %projectName%.bar -a %projectName% -cleanBuild
'''
}
      }
      stage('deploy bar') {
 steps {
 bat label: '', script: '''
echo off
set workspace=%cd%
cd C:\\Program Files\\IBM\\ACE\\12.0.12.4\\server\\bin\\
call .\\mqsiprofile.cmd
cd %workspace%
mqsideploy iib -e IS01 -a IIB_Demo.bar
'''

archiveArtifacts 'IIB_Demo.bar'
}
 }
   }
}