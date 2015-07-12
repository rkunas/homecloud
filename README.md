# Homecloud

Youre Homecloud written in Java. Possibilitiy for a new ecosystem of a private cloud.
The cloud with new technologies.

<img src="http://81.169.224.34:8090/download/attachments/9961474/Bildschirmfoto%202015-07-07%20um%2019.08.00.png?version=1&modificationDate=1436288899390&api=v2"/>

<img src="http://81.169.224.34:8090/download/attachments/9961474/Bildschirmfoto%202015-07-07%20um%2019.10.06.png?version=1&modificationDate=1436289018884&api=v2"/>

<img src="http://81.169.224.34:8090/download/attachments/9961474/Bildschirmfoto%202015-07-08%20um%2020.21.29.png?version=1&modificationDate=1436379725233&api=v2"/>

##1. Folder and Package Explanation:
###1.1. Folder src 
Folder of Homecloud's Main Source

####1.1.1. Packages
- backend: Source of Backend like Save Data to DB
- common: Source of Backend and Frontend like Model, Entity, Dto's
- frontend: Source of Frontend like UI Logik  

###1.2. Folder db
OrientDB we want to use NoSQL instead of PostgreSQL
##2. Features
###2.1. Current Features:
- File Browsing
- Download File
- Streaming Video with HTML5 Video Content
- Download Video Files
- Text to Speech Engine (Talking Cloud)
- User change Password
- 3D Viewer for STL Files
- File Upload Dropzone 

###2.2. Next Features to be implemented
- Delete Files and Folders
- Create Folders

###2.3. Backlog Features
- Spring Boot Integration
- Separate User Folder

###3.0 Technologies
- Apache Wicket UI Framework 6.20
- VideoJS (HTML5 Video Player extention)
- ThreeJS (3D Engine fro HTML5 Canvas)
- Bootstrap 3.3.5
- Spring Framework 4.1.6
- Hibernate 5.1
- MaryTTS Text to Speech Engine 5.2-SNAPSHOT (build by our jenkins) 
- Sphinx4 Speech to Text Engine 1.0-SNAPSHOT (build by our jenkins)

###4.0 Installation Instructions
Homcloud needs PostgreSQL 9.X Database for its Administration Data.

###4.1 Database
Follow the Installation instructions on www.postgresl.org for installing the db.
Create a User homeclowd with password homeclowd and database db_homeclowd.

Create an Admin user 
Create an Config with FOLDER_URL key for root folder of youre files.

###4.2 Tomcat 8.X
Download an Tomcat 8.X from tomcat.apache.org and copy it into a folder 

###4.3 War
Copy the homclowd.war into the webapps folder of youre Tomcat 8 installation.

###4.4 Start Tomcat
Start youre Tomcat with ./catalina.sh start from the bin folder 

####Current Version is 1.0

