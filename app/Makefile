run-example-json:
	./build/install/app/bin/app -f=json src/main/resources/json_file_1.json src/main/resources/json_file_2.json

run-example-yaml:
	./build/install/app/bin/app -f=yaml src/main/resources/yaml_file_1.yaml src/main/resources/yaml_file_2.yaml

run-example-stylish:
	./build/install/app/bin/app src/main/resources/json_file_1.json src/main/resources/json_file_2.json

run-help:
	./build/install/app/bin/app -h

run-dist: clean build install

build:
	./gradlew build

clean:
	./gradlew clean

install:
	./gradlew install

test:
	./gradlew test

.PHONY: build