build:
	mvn compile

unit-test:
	@echo "Executando testes unitários"
	mvn test

integration-test:
	mvn test -P integration-test

system-test: start-app
	mvn test -P system-test

performance-test:
	mvn gatling:test -P performance-test

test: unit-test integration-test

start-app:
	mvn spring-boot:start

package:
	mvn package