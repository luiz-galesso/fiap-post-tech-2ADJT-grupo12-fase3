#language: pt
  Funcionalidade: Restaurantes

    @high
    Cenário: cadastrar um restaurante
      Quando cadastrar um restaurante
      Então o restaurante é cadastrado com sucesso
      E exibir o restaurante

    Cenário: buscar um restaurante pelo identificador
      Dado que o restaurante foi cadastrado
      Quando efetuar a busca do restaurante pelo identificador
      Então exibir o restaurante

    Cenário: buscar um restaurante pelo nome
      Quando buscar restaurantes pelo nome
      Então exibir restaurantes que contenham o nome

    Cenário: buscar um restaurante pelo tipo de culinária
      Quando buscar restaurantes pelo tipo de culinária
      Então exibir restaurantes que são do tipo de culinária buscado

    Cenário: buscar um restaurante pela localização
      Quando buscar restaurantes pela localização
      Então exibir restaurantes de uma localização

    Cenário: alterar o cadastro de um restaurante
      Dado que exista um restaurante cadastrado
      Quando realizar uma alteração no cadastro do restaurante
      Então deverá exibir o cadastro do restaurante atualizado

    Cenário: buscar um restaurante por um identificador inválido
      Quando solicitado um restaurante com identificador inválido
      Então exibir um erro de restaurante não localizado