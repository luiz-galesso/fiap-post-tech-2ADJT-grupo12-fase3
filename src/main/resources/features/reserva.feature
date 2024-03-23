#language: pt

  Funcionalidade:
    Cenario: Reservar mesa
      Dado que tenha a solicitação de reserva, com usuário válido e disponibilidade de reserva
      Então a reserva é registrada com sucesso
      E deverá ser apresentada