#language: pt

Funcionalidade: Reserva de mesas

  @high
  Cenario: Reservar mesa
    Quando registrar uma reserva
    Então a reserva é registrada com sucesso
    E deverá ser apresentada

  Cenario: Buscar reserva pelo ID
    Dado que a reserva já foi efetuada
    Quando efetuar a busca da reserva pelo ID
    Então exibir reserva

  @low
  Cenario: Buscar reserva pelo restaurante e data
    Quando efetuar a busca por reservas utilizando o ID do restaurante e uma data
    Então exibir reservas encontradas

  Cenario: Buscar reservas ativas por cliente
    Quando efetuar a busca das reservas ativas, utilizando o ID do cliente
    Então exibir reservas ativas encontradas

  Cenario: Realizar checkin
    Dado que exista uma reserva válida
    Quando realizar checkin
    Então deverá ser exibido o checkin

  Cenario: Realizar checkout
    Dado que exista uma reserva válida
    Quando realizar checkin
    Quando realizar checkout
    Então deverá ser exibido o checkout

  Cenario: Realizar cancelamento
    Dado que exista uma reserva válida
    Quando solicitar o cancelamento
    Então a reserva é cancelada com sucesso
