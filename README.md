# Pokémon league manager
This repository stores project data for a course PA165 taught at Faculty of Informatics, Masaryk University.

## Running the web application
To run the web application simply execute following command.

        $ mvn clean install && cd pokemon-web && mvn tomcat7:run

This will result in deployment of web application that is accessible at http://localhost:8080/pa165/. The application requires visitor to log in. There are two users predefined.

1. username "admin" password "admin"
2. username "user" password "user"

They **do** differ in privileges and "user" **cannot** perform all of the action available in the web interface.

## REST API
The application also provides REST API for pokemon management. Deploy the application with the same command as stated above. Then you can make queries for pokemons and make changes to them as well. Because the same security rules do apply to REST API, there is a shell script located in the project's main directory to make life easier. The script really only logs in as admin and obtains CSRF token for post/delete requests. Sample command can be found in javadoc of rest controller class and below.

* Create pokemon

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons/create -H "Content-Type: application/json" --data '{"name":"SomeName","nickname":"SomeNick","type":"BUG","skillLevel":"5","trainerId":"2"}'

* Change skill

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons/changeskill/2 -H "Content-Type: application/json" --data '20'

* Change trainer

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons/changetrainer/2 -H "Content-Type: application/json" --data '3'

* Trade pokemon

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons/trade -H "Content-Type: application/json" --data '[2,3]'

* Delete pokemon

        $ ./rest_tester.sh -X DELETE http://localhost:8080/pa165/rest/pokemons/1

* Show pokemon details

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons/1

* Retrieve all pokemons

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons

* Retrieve pokemons with trainer

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons/withtrainer -H "Content-Type: application/json" --data '3'

* Retrieve pokemons with name

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons/withname -H "Content-Type: application/json" --data 'Onix'

* Retrieve pokemons with type

        $ ./rest_tester.sh http://localhost:8080/pa165/rest/pokemons/withtype -H "Content-Type: application/json" --data '"ROCK"'

## Getting started

The entire project is written in Java; therefore, you need a working Java. The project uses Maven for maintaining all the dependencies and doing the hard work during the compilations. If you have both tools up and running, you can clone this repository using the following command and start contributing.

        $ git clone https://github.com/jcechak/pokemon-league-manager.git


## Workflow

The following steps are meant for Unix shell. It is also possible to achieve some or all of them through some kind of GUI or withing your favourite IDE (Netbeans, Eclipse and Idea should have such capabilities).

* Download the most recent version of the project from github repository.

        $ git pull

* Create your own branch with some appropriate name (e.g. name of the feature you are about to implement).

        $ git checkout -b name_of_the_branch

* Implement all the features, work on issues, fix all bugs, run all tests and closes all solved issues. Don't forget to commit regularly and possibly push your branch if it takes a while to implement.

        $ git commit -m "Describe what have you done." file_to_commit1 file_to_commit2 ...
        $ git push

* When you are done with implementing, merge your branch with the master branch.

        $ git checkout master
        $ git merge name_of_the_branch

* Push your code to github repository for others to explore and enjoy.

        $ git push

* Close everything and feel proud of yourself and your accomplishment.
