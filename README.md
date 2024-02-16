# OndeDoar

## Projeto Integrador do curso Análise e Desenvolvimento de Sistemas 

```mermaid
classDiagram
    class User {
        - id: string
        - name: string
        - email: string
        - login: string
        - password: string
        - role: string
        - CPF: string
        - institutions: array
        - favorites: array
    }

    class Institution {
        - id: string
        - cnpj: string
        - companyName: string
        - description: string
        - responsible: string
        - address: string
        - cep: string
        - phones: array
        - category: string
        - status: string
        - evaluations: array
        - images: array
        - user: string
    }

    class Evaluation {
        - id: string
        - comment: string
        - date: string
        - institution: string
        - user: string
    }

    class Image {
        - id: string
        - fileName: string
        - filePath: string
        - institution: string
    }

    User "1" --> "n" Institution
    User "1" --> "n" Evaluation
    Institution "1" --> "n" Evaluation
    Institution "1" --> "n" Image
