# Open access publishing data: File conversion and retrieval software

The project is a small, conceptual Java SE software application to create and persist POJOs mapped from JSON (via open access web services) and XML data (local business information). The software replicates data from adjacent business systems (i.e. [CRIS](https://en.wikipedia.org/wiki/Current_research_information_system)), external RESTful web services (e.g. [OaDoi](https://oadoi.org/api)) and persists data in a MySQL database.

The primary objective is to capture data on ['open access scholarly publishing'](https://en.wikipedia.org/wiki/Open_access) for Universities or research institutions. Typically these organisations hold publication metadata about the research outputs they produce. This information is  often lacking insights into open access publishing from collaborative partnerships, but this data is available from new web services like OaDoi.

The software provides a model for capturing and enhancing this data using available tools. A full report describing the context, development, use cases and software model is published on ['Figshare'](https://dx.doi.org/10.6084/m9.figshare.4887011).

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. The software is tested to run within the Netbeans IDE environment. We recommend you install the Netbeans IDE. You require a relational database instance running on a local/remote server (Hibernate supports these [databases](https://netbeans.org/kb/docs/ide/git.html))).

* A report describing the context, development, use cases and software model is published on ['Figshare'](https://dx.doi.org/10.6084/m9.figshare.4887011).
* The database schema is publishing as a .sql dump on ['Figshare'](https://dx.doi.org/10.6084/m9.figshare.5765499).

### Prerequisites

The software is tested to run within the Netbeans IDE environment. A database should be created per the following ['schema'](https://dx.doi.org/10.6084/m9.figshare.5765499).

### Installing

A step by step series of examples that tell you have to get a development environment running

#### 1) Set up
* The repository may be cloned in the Netbeans environment through the following ['tutorial'](https://netbeans.org/kb/docs/ide/git.html)
* Add the Hibernate ORM 4.3x libraries to the class path (via wizard)
* Add the json-simple-1.1.1 [library](https://code.google.com/archive/p/json-simple/downloads) to the class path
* Hibernate.cfg.xml configuration file; configure JCDB properties for a relational database instance, e.g. driver, url, username, password, Hibernate dialect
https://netbeans.org/kb/docs/web/hibernate-webapp.html#05

#### 2) Running the software
Supply a array argument containing any of the following values to the program at runtime; 1, 4, 5, 6, 7. In netbeans this can be done by via the project properties menu and selecting "run".

These values correspond to use cases identified during the design. The OAFS_Main package contains the main class that processes these arguments. At runtime a session object is created per the Hibernate framework. The run class prepares parameters for the OAFS package model, based on the use case running. All use cases corresponding to the values supplied will be run.

In this way, the software allows all required use cases to be run sequentially, or for utility tools to schedule cases to run according to an appropriate schedule.

The software runs until completion and then closes.

#### 2a) Use case "1" - Input XML data
* Supply a array argument "1" to the program at runtime. XML files in the "Input" directory are validating according against xsd files in the "Schema" directory, mapped to Java objects and persisted in the database via Hibernate

#### 2b) Use case "4" - Output XML data
* Supply a array argument "4" to the program at runtime. An example case of the program being provided a HQL query to retreive data from the database, and then export the data as XML for availablilty in the "Output" directory.

#### 2c) Use case "5" - Retreive [OaDoi](https://oadoi.org/api) Web service data
* Supply a array argument "5" to the program at runtime. Retrieves data, maps to Java objects, then perists data in DB through Hibernate.

#### 2d) Use case "6" - Retreive [Core](https://core.ac.uk/services#api) Web service data
* Supply a array argument "6" to the program at runtime. Retrieves data, maps to Java objects, then perists data in DB through Hibernate.

#### 2e) Use case "7" - Retreive [Sherpa REF](https://ref.sherpa.ac.uk/api) Web service data
* Supply a array argument "7" to the program at runtime. Retrieves data, maps to Java objects, then perists data in DB through Hibernate.

## Running the tests

There are no automated tests for this system. There is a test file in the "Input" directory that can be used to test use case 1. All other use cases can be tested once test data is loaded. This is because the test data, which is bibliographic data for scholarly journal articles, contains inputs for the REST services, particularly publication DOI and Serial ISSN.

Although data in the test file is in the public domain (as publications), organisation and individual characteristics have been anonymised.

In the results you will see data from the services populating the database.

## Built With
* Java SE 8
* [Hibernate ORM](http://hibernate.org/) - The peristence framework used

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

Anon, NetBeans Docs &amp; Support. Available at: https://netbeans.org/kb/ [Accessed March 21, 2017b].

Bkkbrad, 2010. Efficient way to calculate byte length of a character, depending on the encoding. Stack Overflow. Available at: https://stackoverflow.com/questions/2726071/efficient-way-to-calculate-byte-length-of-a-character-depending-on-the-encoding [Accessed June 3, 2017].

Fisher, P., 2016. Java: Testing with JUnit. Lynda.com. Available at: https://www.lynda.com/Java-tutorials/Using-JUnit-Testing-Java/520534-2.html?srchtrk=index%3A2%0Alinktypeid%3A2%0Aq%3Ajunit%0Apage%3A1%0As%3Arelevance%0Asa%3Atrue%0Aproducttypeid%3A2 [Accessed April 1, 2016].

Fontanos, C., 2015. Java – Parsing JSON Data from a URL. CarloFontanos. Available at: http://carlofontanos.com/java-parsing-json-data-from-a-url/ [Accessed July 3, 2017].

Impact story, 2016. API. OADOI.org. Available at: https://oadoi.org/api.

Isocra Consulting, 2007. UTF-8 with Hibernate 3.0 and MySQL. Isocra.com. Available at: http://isocra.com/2007/01/utf-8-with-hibernate-30-and-mysql/ [Accessed August 29, 2017].

JISC, 2017a. Reusing Sherpa REF data. Available at: https://ref.sherpa.ac.uk/reuse [Accessed April 1, 2017].
JISC, 2017b. Sherpa RoMEO Application Programmers’ Interface. Available at: http://www.sherpa.ac.uk/romeo/apimanual.php?la=en&fIDnum=%7C&mode=simple [Accessed March 1, 2017].

King, G., 2010. Session. Hibernate JavaDocs. Available at: https://docs.jboss.org/hibernate/orm/3.5/javadocs/org/hibernate/Session.html [Accessed August 3, 2017].

Netbeans, 2017. Using Git Support in NetBeans IDE. Netbeans Docs & Support. Available at: https://netbeans.org/kb/docs/ide/git.html [Accessed April 1, 2017].

NetBeans, 2017a. Connecting to a MySQL Database. NetBeans.org. Available at: https://netbeans.org/kb/docs/ide/mysql.html [Accessed June 22, 2017].

NetBeans, 2017b. Using Hibernate in a Web Application. NetBeans IDE Tutorial. Available at: https://netbeans.org/kb/docs/web/hibernate-webapp.html [Accessed June 10, 2017].

Oracle, 2017a. javax.persistence documentation. Java(TM) EE 7 Specification APIs. Available at: https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html [Accessed September 10, 2017].

Oracle, 2017b. MySQL Workbench Manual. MySQL.com. Available at: https://dev.mysql.com/doc/workbench/en/wb-installing-windows.html [Accessed June 28, 2017].

RedHat, 2017. Hibernate ORM. Hibernate.org. Available at: http://hibernate.org/orm/ [Accessed July 28, 2017].

Shah, M., 2016. Java - Json Simple. StudyTrails. Available at: http://www.studytrails.com/java/json/java-json-simple/ [Accessed September 3, 2017].

Vajjhala, S. & Fialli, J., 2006. The JavaTM Architecture for XML Binding (JAXB) 2.0. Available at: http://download.oracle.com/otndocs/jcp/jaxb-2.0-fr-eval-oth-JSpec/.

W3 Schools, 2017. XSD - The <schema> Element. W3 Schools. Available at: https://www.w3schools.com/xml/schema_schema.asp [Accessed May 1, 2017].
  
W3C, Extensible Markup Language (XML). Available at: https://www.w3.org/XML/.
