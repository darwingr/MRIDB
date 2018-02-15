# JSDB - Juvenile Statistical Database

_One Paragraph of project description goes here_

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software:

 - git
 - Java version 8
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
    - `DB_ADDRESS`
    - `DB_PORT`
    - `DB_USERNAME`
    - `DB_PASSWORD`
    - `DB_HIPAA_USERNAME`
    - `DB_HIPAA_PASSWORD`
    - These ones may be useful on the commmand line outside of Eclipse when
    running SQL scripts with sqlloader:
      - `SQLPLUS_PATH`
      - `SQLLOADER_PATH`

 3. ???
 4. PROFIT

End with an example of getting some data out of the system or using it for a little demo.


## Data
The data used is from 3 different data sets:
 - ABIDE
 - ???
 - ???

All data was anonymized long before it reached our hands.
We've also generated random data using [mockaroo](https://www.mockaroo.com/).

### Database Schema
The schema for the database can be found under `db/schema.sql`. This is run
sqlloader.exe.

### Data Loader
Data loader scripts are under `db/seed.ctl` and are just SQL files. The data
itself is all under `db/data/` in csv format. This is run
sqlloader.exe.



```
Give an example
```

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

## Deployment

_Add additional notes about how to deploy this on a live system_

## Built With

* Oracle-Java Database API - ojdbc8.jar
* [???]() - ORM framework used

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
