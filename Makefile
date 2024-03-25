build:
	mvn compile

unit-test:
	@echo "Executando testes unitários"
	mvn test

integration-test:
	@echo "Executando testes integrados"
	mvn test -P integration-test

system-test: start-app
	@echo "Executando testes de sistema"
	mvn test -P system-test

performance-test:
	@echo "Executando testes de performance"
	mvn gatling:test -P performance-test

test: unit-test integration-test system-test performance-test

start-app:
	@echo "Iniciando a aplicação"
	mvn spring-boot:start

package:
	@echo "Criando pacote para deploy"
	mvn package