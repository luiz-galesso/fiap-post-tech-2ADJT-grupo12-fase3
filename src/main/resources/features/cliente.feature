#language: pt

Funcionalidade: Cliente

  Cenario: Cadastrar Cliente
    Quando registrar um cliente
    Então o cliente é registrado com sucesso
    E deverá ser apresentado

  Cenario: Buscar Cliente pelo email
    Dado que o cliente ja foi cadastrado
    Quando efetuar a busca do cliente pelo email
    Então exibir cliente

  Cenario: Atualizar Cliente
    Dado que o cliente ja foi cadastrado
    Quando efetuar a requisicao para alterar o cliente
    Então o cliente é atualizado com sucesso
    E deverá ser apresentado

  Cenário: Remover Cliente
    Dado que o cliente ja foi cadastrado
    Quando efetuar a requisicao para remover o cliente
    Então o cliente é removida com sucesso