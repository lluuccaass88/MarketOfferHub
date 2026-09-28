# MarketOfferHub

Sistema para agregação de ofertas de supermercados em uma única aplicação.

O **MarketOfferHub** é um projeto pessoal desenvolvido inicialmente como MVP, com o objetivo de coletar e centralizar as ofertas do dia de diferentes supermercados da região.

## 🎯 Objetivo

Automatizar a coleta de ofertas de supermercados e disponibilizá-las em um único sistema.

O projeto trabalha inicialmente com duas formas de obtenção dos dados:

* **API/Endpoint:** para supermercados que disponibilizam os produtos através de endpoints utilizados pelo próprio site.
* **Encarte/Imagem:** para supermercados que não possuem uma API utilizável, utilizando OCR/IA para interpretar os encartes.

## 🚧 Escopo atual — MVP

O MVP possui como única funcionalidade:

> **Mostrar as ofertas do dia.**

A funcionalidade de histórico/evolução de preços está planejada para uma etapa futura e não faz parte do MVP atual.

## 🏗️ Arquitetura

O sistema separa a **coleta** do **processamento** dos dados.

```text
                    GET /ofertas
                         │
                         ▼
                Mercados configurados
                         │
                 Já coletado hoje?
                    ┌────┴────┐
                   SIM       NÃO
                    │         │
                    │    Forma de coleta
                    │       ┌─┴─┐
                    │      API ENCARTE
                    │       │    │
                    │       └─┬──┘
                    │         ▼
                    │    Processamento
                    │         │
                    └────┬────┘
                         ▼
                       Banco
                         │
                         ▼
                  Ofertas do dia
```

### Fluxos de coleta

#### API

```text
Endpoint do supermercado
          ↓
       Coleta
          ↓
    Processamento
          ↓
       Banco
```

#### Encarte

```text
Site do supermercado
          ↓
    Encarte/Imagem
          ↓
        OCR/IA
          ↓
    Processamento
          ↓
        Banco
```

Os dois fluxos devem gerar o mesmo modelo de dados, permitindo que as ofertas sejam tratadas de forma uniforme pelo restante da aplicação.

## 🛠️ Tecnologias

* Java
* Spring Boot
* Gradle
* PostgreSQL
* Supabase
* JPA / Hibernate

## 🗄️ Modelo de dados

### Produto

```text
Produto
├── id
└── nome
```

### Oferta

```text
Oferta
├── produto_id
├── mercado_nome
├── preco
├── preco_original
├── data_coleta
└── confianca
```

A identificação do produto é independente do mercado e da data.

Exemplo:

```text
Produto:
ArrozTioJoao5kg

Oferta:
produto_id      = ArrozTioJoao5kg
mercado_nome    = Bistek
preco           = 22.90
preco_original  = 28.90
data_coleta     = 2026-09-27
confianca       = 1.0
```

## 🔎 Normalização

Inicialmente, os nomes dos produtos serão normalizados removendo:

* Acentos
* Espaços
* Caracteres especiais

Exemplo:

```text
"Arroz Tio João 5kg"
        ↓
"ArrozTioJoao5kg"
```

A estratégia poderá ser aprimorada posteriormente.

## 📊 Confiança

Para dados obtidos diretamente através de API:

```text
confiança = 1.0
```

Para dados obtidos através de encarte/OCR/IA:

```text
0.0 ≤ confiança ≤ 1.0
```

A confiança representa a segurança do processamento na identificação das informações da oferta.

## ⚙️ Configuração dos mercados

No MVP, os mercados serão configurados através do:

```text
application.properties
```

Não haverá uma tabela de mercados no banco inicialmente.

O projeto utilizará os enums:

```text
Mercado
FormaColeta
```

Onde `FormaColeta` representa:

```text
API
ENCARTE
```

## 📋 Roadmap técnico

### 0. Descoberta das fontes de dados

* [ ] Levantar supermercados disponíveis
* [ ] Identificar quais possuem APIs/endpoints
* [ ] Identificar como obter os dados de cada API
* [ ] Identificar mercados sem API utilizável
* [ ] Identificar como obter os encartes
* [ ] Definir a forma de coleta de cada mercado

### 1. Estrutura do projeto

* [ ] Criar projeto Spring Boot
* [ ] Configurar Gradle
* [ ] Configurar `application.properties`
* [ ] Configurar dependências
* [ ] Criar estrutura inicial de pacotes
* [ ] Criar enums e configurações básicas

### 2. Banco de dados

* [ ] Criar banco no Supabase
* [ ] Criar tabela `produtos`
* [ ] Criar tabela `ofertas`
* [ ] Criar constraints e índices
* [ ] Configurar conexão com PostgreSQL
* [ ] Configurar JPA/Hibernate
* [ ] Criar entidades
* [ ] Criar repositories
* [ ] Validar persistência

### 3. Fluxo de busca por API

* [ ] Criar fluxo de coleta por API
* [ ] Implementar primeiro supermercado
* [ ] Processar dados
* [ ] Normalizar produtos
* [ ] Criar ofertas
* [ ] Persistir no banco
* [ ] Validar fluxo completo

### 4. Fluxo de busca por imagem

* [ ] Identificar primeiro supermercado
* [ ] Obter encarte
* [ ] Implementar download
* [ ] Implementar OCR
* [ ] Processar informações
* [ ] Normalizar produtos
* [ ] Definir confiança
* [ ] Persistir no banco
* [ ] Validar fluxo completo

### 5. Endpoint de ofertas

* [ ] Criar `GET /ofertas`
* [ ] Verificar mercados configurados
* [ ] Verificar se cada mercado já foi coletado no dia
* [ ] Buscar dados existentes no banco
* [ ] Executar coleta quando necessário
* [ ] Processar e salvar novas ofertas
* [ ] Retornar ofertas do dia
* [ ] Tratar falhas individuais dos mercados

## 📌 Princípios do MVP

* Manter a solução simples.
* Evitar overengineering.
* Separar coleta de processamento.
* Permitir diferentes fontes de dados.
* Normalizar todas as fontes para o mesmo modelo.
* Implementar primeiro fluxos funcionais com mercados reais.
* Adiar funcionalidades que não fazem parte do MVP.

## 📄 Status

🚧 **Em desenvolvimento**

O projeto está atualmente na fase de **descoberta das fontes de dados dos supermercados**.
