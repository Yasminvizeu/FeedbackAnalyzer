
# Feedback Analyzer API

## Descrição

O **Feedback Analyzer API** é um serviço RESTful projetado para analisar feedbacks de usuários. Ele utiliza modelos de linguagem para classificar o sentimento do feedback e identificar funcionalidades sugeridas. A API fornece uma resposta estruturada em JSON que inclui o sentimento, um código único para cada funcionalidade e uma explicação do motivo para a implementação da funcionalidade sugerida.

## Funcionalidades

- **Análise de Sentimento**: Classifica o feedback como `POSITIVO`, `NEGATIVO` ou `INCONCLUSIVO`.
- **Identificação de Funcionalidades**: Gera um código curto e único para identificar as funcionalidades sugeridas.
- **Razão para a Implementação**: Fornece uma explicação em terceira pessoa sobre a importância da funcionalidade sugerida.

### Exemplo de Resposta

```json
{
  "id": 1,
  "sentiment": "POSITIVO",
  "requested_features": [
    {
      "code": "EDITAR_PERFIL",
      "reason": "O usuário gostaria de realizar a edição do próprio perfil"
    }
  ]
}
```
## Tecnologias Utilizadas

1. Java 11
2. Spring Boot
3. RestTemplate para realizar requisições HTTP
4. Jackson para manipulação de JSON
5. LM Studio como API de completude de linguagem
6. Maven para gerenciamento de dependências

## Pré-requisitos

1. Java 11+
2. Maven 3.6+
3. LM Studio configurado e rodando localmente na porta 1234

## Instalação e Configuração

### Clone o repositório:

```bash
git clone https://github.com/seu-usuario/feedback-analyzer-api.git
cd feedback-analyzer-api
```
### Instale as dependências do Maven:

```bash
mvn clean install
```

Configure o LM Studio para garantir que ele está rodando na porta 1234.

## Execute o projeto:

```mvn spring-boot:run```

## Endpoints

Análise de Feedback
URL: /api/feedback/analyze
Método: POST
Descrição: Analisa o feedback do usuário, classificando o sentimento e identificando as funcionalidades sugeridas.

### Exemplo de Requisição:
```
{
"feedbackText": "O sistema precisa de uma funcionalidade de edição de perfil."
}
```

### Exemplo de Resposta:

```
{
    "id": 1,
    "sentiment": "POSITIVO",
    "requested_features": [
        {
            "code": "EDITAR_PERFIL",
            "reason": "O usuário gostaria de realizar a edição do próprio perfil"
        } 
    ]
}
```

## Estrutura do Projeto:
1. src/main/java/com/alura/feedbackAnalyser/controllers/FeedbackController.java: 
Controlador que expõe os endpoints da API.
2. src/main/java/com/alura/feedbackAnalyser/services/LlmService.java: 
Serviço responsável por interagir com o modelo de linguagem e processar o feedback. 
3. .src/main/resources/application.properties: 
Arquivo de configuração do Spring Boot.

## Observações sobre o desenvolvimento:

Iniciei e estruturei o projeto básico Spring Boot, criando a entidade Feedback e garantindo que o primeiro endpoint de criação de feedback respondesse com o status 200 OK. Esse foi o ponto de partida do projeto, que incluiu desde a configuração inicial até a integração com modelos de Language Learning Models (LLMs). Após a criação do feedback, integrei o projeto com um LLM usando a ferramenta LMStudio, para servir o modelo phi 3.1 min 128l instruct-q4_K_M.gguf. Fiz o download do modelo e configurei o servidor, que foi executado na porta 1234.

O objetivo principal foi realizar uma análise sentimental, gerar um código único para uma funcionalidade proposta e explicar o motivo da sua implementação (reason). Para isso, dividi as tarefas em partes menores para facilitar o desenvolvimento e garantir a clareza no código. Testei a resposta de um prompt para realizar a análise e verificar o sentimento retornado, validei a geração do código único e o retorno do motivo da funcionalidade proposta. Por se tratar de um modelo pequeno (apenas 128k), ajustei a quantidade de tokens e a temperatura para obter uma resposta ideal, visto que a infraestrutura era limitada. Após obter as respostas desejadas, refatorei a classe LlmService, quebrando o método em responsabilidades menores, o que resultou em um código mais limpo e organizado.

O endpoint de análise final retornava as três respostas esperadas (sentimento, código e motivo) em um formato JSON, conforme especificado no exercício. 

Ainda há melhorias pendentes, como refinamento na extração do motivo (reason), ajuste das configurações do modelo para uma resposta mais eficiente e validação dos campos código e motivo para não retornar valores vazios se forem inconclusivos.

O GPT foi utilizado como apoio em todas as fases de implementação, ajudando a garantir as melhores práticas e manter um código limpo. Quando a solução gerada pelo GPT não era suficiente, adaptei-a de forma simples para o contexto do projeto e incrementei conforme necessário. O GPT auxiliou em várias áreas, incluindo a elaboração de passos claros e organizados, estruturação do código, definição dos métodos principais e auxiliares, e estratégias de integração com os LLMs.











