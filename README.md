# Pokémon league manager
This repository stores project data for a course PA165 taught at Faculty of Informatics, Masaryk University.

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
        & git push

* When you are done with implementing, merge your branch with the master branch.

        $ git checkout master
        $ git merge name_of_the_branch

* Push your code to github repository for others to explore and enjoy.

        $ git push

* Close everything and feel proud of yourself and your accomplishment.
