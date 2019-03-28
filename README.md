# Instructions

## Build

These instructions assume the use of the Maven command-line client, and that Maven is correctly installed on your machine:

* Open your preferred CLI. (Command Prompt, PowerShell, bash, etc.)
* Enter `mvn package` (will run tests and build an executable JAR)

## Test

These instructions *also* assume the use of the Maven command-line client:

* Open your preferred CLI.
* Enter `mvn test`, `mvn verify`, or `mvn -Dtest=FamilyTreeTest test`

## Run

These instruction sets all assume that the application has been built:

### Default file

* Run `target/testBE-1.0.jar`. Note that `test.txt` must be located in the JAR's working directory.
	* If you are running the JAR from the repository root (`java -jar target/testBE-1.0.jar`), no change is needed.
	* Otherwise, you must either:
		* Move `test.txt` to the directory from which you are running the JAR, *or*
		* Set the JAR's working directory by passing a `-Duser.dir=Path/To/Test/Txt` parameter in your `java` call.

### Custom file

* Create a text file in the JAR's working directory (see above), then run `target/testBE-1.0.jar`, passing the file's name as the first argument.
	* For example, running the JAR from the repository root, for the file `otherTest.txt` would be `java -jar target/testBE-1.0.jar otherTest.txt`

# Notes

* Valid line format is `[ChildName] [ParentName]`. Names cannot contain spaces. (This could be changed at a later time.)
* Invalid lines will cause the application to print a warning to the console, but will otherwise be ignored.
