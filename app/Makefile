run-example-json:
	./build/install/app/bin/app -f=json src/main/resources/json_file_1.json src/main/resources/json_file_2.json

run-example-yaml:
	./build/install/app/bin/app -f=yaml src/main/resources/yaml_file_1.yaml src/main/resources/yaml_file_2.yaml

run-help:
	./build/install/app/bin/app -h

build:
	./gradlew clean build

clean:
	./gradlew clean

install:
	./gradlew clean install

test:
	./gradlew test

.PHONY: build