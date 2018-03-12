# JSDB - Juvenile Statistical Database

_One Paragraph of project description goes here_

## Getting Started

These instructions will get you a copy of the project up and running on your
local machine for development and testing purposes. See deployment for notes on
how to deploy the project on a live system.

### Prerequisites

What things you need to install the software:

 - git
 - Java version 8
 - Java development kit (jdk)
 - Running instance of Oracle DB version 11
 - Eclipse
 - eGit plugin for Eclipse(not required but mike and Darwin like it)


### Installing

A step by step series of examples that tell you have to get a development env running

 1. Clone the project repo from GitHub
    - In Eclipse go to `File > Import...`
    - Click `Git(folder icon) > Projects from Git` then click next
    - Click Clone URI, Next then put in the github clone address (use https)
    - Continue on with default settings, whatever location you want
    - On the wizard selection window use `Import existing Eclipse projects`

 2. Install the external ojdbc8.jar to the library.
    - From the files shared on Martin's OneDrive download any of the folders
    containing the file `ojdbc8.jar`.
    - Drag `ojdbc8.jar` to the `lib/` folder in this project.
    - That's it!

 3. Setup project environment variables. These are for information we don't
 want saved as part of the project history such as the database address,
 username and password. They'll be used by both from sqlloader and the java
 app. To add them to Eclipse go to `Run > Run Configurations` then click the
 Environment tab. Add the following variables with the appropriate values:
    - `DB_HOST`
    - `DB_PORT`
    - `DB_USERNAME`
    - `DB_PASSWORD`
    - `DB_HIPAA_USERNAME`
    - `DB_HIPAA_PASSWORD`
    - These ones may be useful on the commmand line outside of Eclipse when
    running SQL scripts with sqlloader:
      - `SQLPLUS_PATH`
      - `SQLPLUS_SPOOL_PATH`
      - `SQLLDR_PATH`

 Alternatively you can add them to a launch script under `bin/` that then
 launches eclipse with the right environment. This allows you to avoid needing
 to add them again for each testing environment via eclipse.

 On mac just run `./bin/eclipse-mac.sh` from the project root.


## Data
All the data used is stored with the source code under `db/data/`.
It originally derives from 3 different data sets:
 - ABIDE
 - ???
 - ???

All data was anonymized long before it reached our hands.
We've also generated random data using [mockaroo](https://www.mockaroo.com/).

### Database Schema
The schema for the database can be found under `db/schema.sql`. This is run
sqlloader.exe.

### Data Loader
Data loader scripts are under `db/seed.sql` and are just SQL files. The data
itself is all under `db/data/` in csv format. This is run
sqlloader.exe. The resulting logs will be placed in the `tmp/` directory of
the project, git will ignore the contents of this folder.

[Oracle sqlldr cli documentation](https://docs.oracle.com/cd/B19306_01/server.102/b14215/ldr_params.htm)

Also note that you'll need to define the environment variables for sqlldr to
know how to connect to the host `ORACLE_HOME`, in our case the path to the
sqlldr and slqplus executables, and `TNS_ADMIN`, the folder where tnsnames.ora
is found. Make sure you have a tnsnames.ora file and that it contains a
definition for csci275.

From Windows command prompt:
```shell
setx ORACLE_HOME "D:\example\path\instant_client"
setx TNS_ADMIN %ORACLE_HOME%
```


#### Running SQL Loader
Example using environment variables to run sqlloader from project root folder:

On bash prompt (unix, mac, linux), although sqldr doesn't exist on this platform:
```shell
cd path/to/project/Database-Gui

export SQLLDR_PATH=/usr/bin/sqlloader
export DB_USERNAME=jsdb
export DB_PASSWORD=mypassword

$SQLLDR_PATH userid=$DB_USERNAME/$DB_PASSWORD control=db/seed.sql \
             log=tmp/sqlldr.log bad=tmp/sqlldr-bad.log
```

On Windows command prompt (use `setx` instead to permanently set variable):
```shell
cd path\to\project\Database-Gui

set SQLLDR_PATH="C:\\Users\Darwin\path\to\sqlldr.exe"
set DB_USERNAME=jsdb
set DB_PASSWORD=mypassword

%SQLLDR_PATH% userid=%DB_USERNAME%/%DB_PASSWORD%@csci275 control=db\seed.sql log=tmp\sqlldr.log bad=tmp\sqlldr-bad.log
```


## ORM
Stands for object relational mapping, is the approach we take to mapping
relations in our database to objects in our application.
Given that this is a simple project, we will be using the popular
[Active Record design pattern](https://www.martinfowler.com/eaaCatalog/activeRecord.html) 
to model this layer of the application.

From the description of an active record object in the above link:
> An object that wraps a row in a database table or view, encapsulates the
> database access, and adds domain logic on that data.

The source code for all the classes of active record objects can be found under
the `src/models/` folder and each one is named with the suffix "Model".

It is worth noting that our application does not resemble a CRUD (create, read,
update, delete) app. We are mostly reading from the database and do very little
creating, updating and deleting of records. This is the nature of our problem
as a research database for running statistics.



## Running the tests
The testing framework used is Junit5.
The documentation is available [HERE](https://junit.org/junit5/docs/current/user-guide/).

### Unit Tests
The tests are in a parallel folder to src, the `test/` folder. The classes
written here mirror those written in src and each has the same name suffixed by
"Test". For example, the PatientModel class would have a PatientModelTest
class.

To run simply click Run with a test selected in Eclipse.
For more instructions with eclipse see this WashingtonU page: 
[Unit Testing in Eclipse Using JUnit](http://faculty.washington.edu/stepp/courses/2005spring/tcss360/handouts/6-junit_eclipse_2.html)

```
Give an example
```

### Break down into end to end tests

These test the fully running program, as such take longer to run. May not be
necessary.

```
Give an example
```

## Deployment

_Add additional notes about how to deploy this on a live system_

## Built With

* Oracle-Java Database API - ojdbc8.jar
* sqlplus and sqlldr (Oracle)
* JUnit5 testing framework/library


## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/ThreeFourSeven/Database-Gui/tags).

## Teammate Credits

* **Seven Randall** - *Created GUI Rendering Engine* - [ThreeFourSeven](https://github.com/ThreeFourSeven)
* **Mike Kennedy** - [mikekenn](https://github.com/mikekenn)
* **Darwin Groskleg** - [darwingr](https://github.com/darwingr)
* **Cynthia Forgeron**

See also the list of [contributors](https://github.com/ThreeFourSeven/Database-Gui/contributors) who participated in this project.

## License

This project is licensed **NOT** under the MIT License - **DO NOT** see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Dr. Martin Van Bommel - How to DataBase (StFX - CSCI 275)
* Dr. Jacob Levman
