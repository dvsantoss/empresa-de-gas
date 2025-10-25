# Projeto Empresa de Gás

Este projeto demonstra conceitos fundamentais de Programação Orientada a Objetos em Java, incluindo encapsulamento, herança, polimorfismo, composição e gerenciamento de estado através de um sistema de pedidos para empresa de gás.

## Estrutura do Projeto

```
empresa-de-gas/
├── src/                    # Código fonte Java
│   ├── App.java           # Classe principal com menu interativo
│   └── Pedido.java        # Classe que representa um pedido
├── bin/                    # Arquivos compilados (.class)
│   ├── App.class
│   └── Pedido.class
└── lib/                    # Bibliotecas externas
```

## Hierarquia de Classes

```
App (Classe Principal)
├── Método main()
├── Menu interativo
├── Gestão de lista de pedidos
└── Métodos auxiliares

Pedido (Classe de Domínio)
├── Atributos privados
├── Construtor
├── Métodos de negócio
├── Getters e Setters
└── toString() sobrescrito
```

## Conceitos Demonstrados

- **Encapsulamento**: Atributos privados com acesso controlado via getters/setters
- **Herança**: Uso de `@Override` para sobrescrita do método `toString()`
- **Polimorfismo**: Método `toString()` sobrescrito com comportamento específico
- **Composição**: App utiliza `List<Pedido>` para gerenciar pedidos
- **Atributos estáticos**: `proximoId` para geração automática de IDs
- **Atributos final**: `idPedido` e `dataHoraCompra` imutáveis

## Como Executar

1. Compile os arquivos Java:
```bash
javac -d bin src/*.java
```

2. Execute a classe principal:
```bash
java -cp bin App
```

## Funcionalidades Testadas

- Criação e gestão de pedidos
- Confirmação e alteração de endereços
- Cálculo de valores e previsão de entrega
- Registro de pagamento com mascaramento de dados
- Controle de status dos pedidos (Em Aberto → Confirmado → Entregue)
- Consulta de pedidos por status
- Interface de usuário interativa
