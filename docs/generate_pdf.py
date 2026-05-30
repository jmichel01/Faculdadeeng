import os
import sys
from fpdf import FPDF

# Function to clean and sanitize unicode characters to Latin-1
def clean_txt(text):
    replacements = {
        '—': '-',
        '•': '*',
        '–': '-',
        '“': '"',
        '”': '"',
        '‘': "'",
        '’': "'",
        '⚡': '[API]',
        '🔴': '[Alta]',
        '🟡': '[Media]',
        '🟢': '[Baixa]',
        '⏳': '[Todo]',
        '🔄': '[Progresso]',
        '✅': '[Concluido]',
        '📦': '[Total]',
        '＋': '+',
        '✕': 'x',
        '🗑️': '[Delete]',
        '⚠️': '[Warning]',
        '🔍': '[Search]',
        '↺': '[Reset]',
        '⟳': '[Refresh]',
        '📋': '[List]',
        'º': 'o.',
        'ª': 'a.',
    }
    for k, v in replacements.items():
        text = text.replace(k, v)
    return text.encode('latin-1', 'replace').decode('latin-1')

class TaskFlowPDF(FPDF):
    def header(self):
        # We don't want headers on the cover page (page 1)
        if self.page_no() == 1:
            return
        self.set_font('Arial', 'I', 8)
        self.set_text_color(100, 110, 120)
        self.cell(0, 10, clean_txt('TaskFlow - Relatório Técnico de Engenharia de Software'), 0, 0, 'L')
        self.cell(0, 10, f'Pág. {self.page_no()}', 0, 1, 'R')
        self.set_draw_color(200, 200, 200)
        self.line(10, 18, 200, 18)
        self.ln(4)

    def footer(self):
        if self.page_no() == 1:
            return
        self.set_y(-15)
        self.set_font('Arial', 'I', 8)
        self.set_text_color(120, 120, 120)
        self.cell(0, 10, clean_txt('Centro Universitário FECAF (UniFECAF) - Projeto PBL'), 0, 0, 'L')
        self.cell(0, 10, clean_txt('Disciplina: Engenharia de Software e Metodologias Ágeis'), 0, 0, 'R')

    def chapter_title(self, label):
        self.set_font('Arial', 'B', 14)
        self.set_text_color(20, 50, 100) # dark blue
        self.cell(0, 10, clean_txt(label), 0, 1, 'L')
        self.ln(2)

    def chapter_body(self, text):
        self.set_font('Arial', '', 10)
        self.set_text_color(40, 40, 40)
        self.multi_cell(0, 5, clean_txt(text))
        self.ln(4)

    def code_box(self, text):
        self.set_font('Courier', '', 8.5)
        self.set_text_color(0, 0, 0)
        self.set_fill_color(245, 245, 245)
        self.set_draw_color(220, 220, 220)
        # Render line by line to support multi-line fill
        lines = text.split('\n')
        for line in lines:
            if line.strip() == "" and line == lines[-1]:
                continue
            self.cell(0, 4, clean_txt("  " + line), border='LR', ln=1, fill=True)
        # bottom border
        self.cell(0, 1, "", border='B', ln=1, fill=False)
        self.ln(4)

def build_pdf(filename):
    pdf = TaskFlowPDF()
    pdf.alias_nb_pages()
    pdf.set_margins(15, 20, 15)
    pdf.add_page()
    
    # ------------------ COVER PAGE ------------------
    pdf.set_font('Arial', 'B', 16)
    pdf.set_text_color(20, 50, 100)
    pdf.cell(0, 30, clean_txt('CENTRO UNIVERSITÁRIO FECAF (UniFECAF)'), 0, 1, 'C')
    pdf.ln(10)
    
    pdf.set_font('Arial', 'B', 22)
    pdf.set_text_color(30, 41, 59)
    pdf.cell(0, 15, clean_txt('TASKFLOW: AGILE TASK MANAGEMENT SYSTEM'), 0, 1, 'C')
    
    pdf.set_font('Arial', 'I', 12)
    pdf.set_text_color(71, 85, 105)
    pdf.cell(0, 10, clean_txt('Relatório Acadêmico de Engenharia de Software e Práticas Ágeis'), 0, 1, 'C')
    pdf.ln(30)
    
    # Border card for project metadata
    pdf.set_fill_color(248, 250, 252)
    pdf.set_draw_color(226, 232, 240)
    pdf.rect(20, 95, 170, 75, 'FD')
    
    pdf.set_xy(25, 100)
    pdf.set_font('Arial', 'B', 11)
    pdf.set_text_color(51, 65, 85)
    pdf.cell(0, 8, clean_txt('DETALHES DO PROJETO E ACADÊMICOS:'), 0, 1, 'L')
    pdf.set_x(25)
    pdf.set_font('Arial', '', 10)
    pdf.cell(0, 7, clean_txt('Estudante: José Michel da Silva'), 0, 1, 'L')
    pdf.set_x(25)
    pdf.cell(0, 7, clean_txt('Curso: Tecnologia em Análise e Desenvolvimento de Sistemas / Engenharia'), 0, 1, 'L')
    pdf.set_x(25)
    pdf.cell(0, 7, clean_txt('Semestre: 3º Semestre - Noturno'), 0, 1, 'L')
    pdf.set_x(25)
    pdf.cell(0, 7, clean_txt('Stack Tecnológica: Java 17, Spring Boot 3, Spring JPA, H2 Database, Maven, JUnit 5'), 0, 1, 'L')
    pdf.set_x(25)
    pdf.cell(0, 7, clean_txt('Infraestrutura DevOps: GitHub Actions (Integração Contínua), Git Version Control'), 0, 1, 'L')
    pdf.set_x(25)
    pdf.cell(0, 7, clean_txt('Repositório: https://github.com/jmichel01/Faculdadeeng'), 0, 1, 'L')
    
    pdf.set_xy(15, 185)
    pdf.set_font('Arial', 'B', 10)
    pdf.set_text_color(100, 110, 120)
    pdf.cell(0, 10, clean_txt('FECAF - TECNOLOGIA E INOVAÇÃO'), 0, 1, 'C')
    pdf.set_font('Arial', '', 9)
    pdf.cell(0, 5, clean_txt('São Paulo, 2026'), 0, 1, 'C')
    
    # ------------------ PAGE 2: SEÇÃO 1 & 2 ------------------
    pdf.add_page()
    
    pdf.chapter_title('1. Introdução, Descrição do Projeto e Escopo Inicial')
    intro_text = (
        "No atual cenário econômico e empresarial de alta velocidade, a logística e o rastreamento operacional "
        "são pilares cruciais para o sucesso corporativo. Processos manuais ou listas de tarefas genéricas não possuem "
        "a flexibilidade necessária para gerenciar prazos de transporte, alocação de frota e prioridades de entrega.\n\n"
        "O projeto TaskFlow - Agile Task Management System foi concebido para resolver esse gargalo em uma startup de logística. "
        "Trata-se de uma aplicação backend baseada em REST APIs, projetada seguindo padrões rígidos de Engenharia de Software "
        "e metodologias de desenvolvimento ágil. O sistema permite realizar operações completas de criação, leitura, atualização e "
        "exclusão (CRUD) de tarefas operacionais.\n\n"
        "Escopo Inicial do Projeto:\n"
        "1. Cadastro de Tarefas: Título (mínimo 3 caracteres), descrição textual, status inicial do ciclo de vida.\n"
        "2. Ciclo de Vida: Estado controlado de cada tarefa pelas constantes TODO (A Fazer), IN_PROGRESS (Em Progresso) e DONE (Concluído).\n"
        "3. Persistência: Banco de dados relacional leve em memória (H2 Database) integrado via Spring Data JPA.\n"
        "4. Qualidade de Código: Validação de requisições por anotações JSR-380 (@NotBlank, @Size) e tratamento centralizado de exceções."
    )
    pdf.chapter_body(intro_text)
    
    pdf.chapter_title('2. Metodologia Ágil Utilizada (Scrum, Kanban e GitFlow)')
    metodologia_text = (
        "Para a execução e entrega do projeto dentro do cronograma acadêmico e com garantia de qualidade, "
        "adotou-se uma metodologia híbrida combinando Scrum e Kanban, integrada com práticas DevOps e GitFlow:\n\n"
        "* Scrum (Estruturação de Tempo): O projeto foi dividido em ciclos incrementais (Sprints). Como o desenvolvimento "
        "foi individual, a Product Backlog consistiu em histórias de usuário convertidas em requisitos de software. Realizou-se "
        "a simulação de reuniões diárias (Dailies) e de planejamento (Sprint Planning) para focar nas features corretas a cada dia.\n\n"
        "* Kanban (Fluxo de Trabalho): Implementou-se um quadro Kanban visual dividindo o trabalho em três colunas claras: "
        "A Fazer (Todo), Em Progresso (In Progress) e Concluído (Done). Cada coluna reflete o ciclo de vida do desenvolvimento backend.\n\n"
        "* Controle Incremental de Versão (Git/GitHub): O desenvolvimento foi guiado por commits semânticos constantes. "
        "Ao invés de subir todo o código de uma vez em um único commit massivo, foram realizados 27 commits ordenados de forma "
        "iterativa (ex: 'feat: add Task entity', 'test: add unit tests', 'refactor: use factory method'). Isso comprova o processo "
        "de melhoria contínua e permite fácil reversão de erros."
    )
    pdf.chapter_body(metodologia_text)

    # ------------------ PAGE 3: SEÇÃO 3 & 4 (USE CASE) ------------------
    pdf.add_page()
    
    pdf.chapter_title('3. A Importância da Modelagem na Engenharia de Software')
    modelagem_text = (
        "A Engenharia de Software diferencia-se da simples programação pela aplicação de processos estruturados e científicos "
        "para projetar, construir e manter sistemas complexos. Nesse contexto, a modelagem de software desempenha um papel indispensável:\n\n"
        "1. Redução de Custos de Desenvolvimento: Detectar um erro de design arquitetural ou de regra de negócios na fase de "
        "codificação é até 10 vezes mais caro do que durante a modelagem. O uso de diagramas permite identificar contradições "
        "antes de escrever o primeiro método Java.\n"
        "2. Alinhamento de Expectativas: Diagramas como o de Casos de Uso servem como uma linguagem comum entre a equipe técnica "
        "e os stakeholders de negócios (gerentes, clientes e usuários finais), garantindo que o sistema atenda às reais dores do cliente.\n"
        "3. Guia de Implementação e Manutenibilidade: O Diagrama de Classes serve como um mapa preciso para o programador. Ele define "
        "as fronteiras de responsabilidade das classes, prevenindo o surgimento de classes gigantes ('God Classes') e promovendo o "
        "baixo acoplamento e alta coesão."
    )
    pdf.chapter_body(modelagem_text)
    
    pdf.chapter_title('4. Diagramas UML Obrigatórios')
    pdf.chapter_body(
        "A seguir, são documentados os dois diagramas UML obrigatórios projetados para o TaskFlow: "
        "o Diagrama de Casos de Uso e o Diagrama de Classes. Suas definições em formato PlantUML "
        "estão representadas nos blocos de código subsequentes."
    )
    
    pdf.chapter_title('A) Diagrama de Casos de Uso (UML)')
    pdf.chapter_body(
        "Este diagrama mapeia a interação dos atores (usuários) com os limites do sistema TaskFlow. Ele define as "
        "funcionalidades expostas aos perfis:\n"
        "- Team Member (Membro da Equipe): Focado em visualizar tarefas, atualizar status e priorizar tarefas atribuídas.\n"
        "- Project Manager (Gerente do Projeto): Possui controle administrativo completo. Pode criar, excluir, atualizar e "
        "ordenar tarefas, além de monitorar o progresso geral."
    )
    
    usecase_puml = (
"@startuml TaskFlow Use Case Diagram\n"
"left to right direction\n"
"skinparam packageStyle rectangle\n"
"\n"
"actor \"Team Member\" as Member\n"
"actor \"Project Manager\" as Manager\n"
"\n"
"rectangle TaskFlowSystem {\n"
"  usecase \"Create Task\" as UC1\n"
"  usecase \"View Tasks\" as UC2\n"
"  usecase \"Update Task\" as UC3\n"
"  usecase \"Delete Task\" as UC4\n"
"  usecase \"Prioritize Task\" as UC5\n"
"  usecase \"Monitor Progress\" as UC6\n"
"}\n"
"\n"
"Member --> UC2\n"
"Member --> UC3\n"
"Member --> UC5\n"
"\n"
"Manager --> Member\n"
"Manager --> UC1\n"
"Manager --> UC4\n"
"Manager --> UC6\n"
"@enduml"
    )
    pdf.code_box(usecase_puml)

    # ------------------ PAGE 4: SEÇÃO 4 (CLASS) & SEÇÃO 5 ------------------
    pdf.add_page()
    
    pdf.chapter_title('B) Diagrama de Classes (UML)')
    pdf.chapter_body(
        "Este diagrama mapeia a estrutura de dados e as dependências arquiteturais das classes organizadas em camadas no Spring Boot:\n"
        "1. Entity Layer: Entidade de persistência Task com enums Priority e Status.\n"
        "2. DTO Layer: Classes de transporte seguras TaskCreateDTO, TaskUpdateDTO e TaskResponseDTO.\n"
        "3. Service Layer: Interface TaskService e implementação TaskServiceImpl encapsulando a lógica de negócio.\n"
        "4. Repository Layer: Interface TaskRepository estendendo JpaRepository.\n"
        "5. Controller Layer: Classes TaskController e TaskSearchController manipulando as rotas REST."
    )
    
    class_puml = (
"@startuml TaskFlow Class Diagram\n"
"package com.logistics.taskflow.entity {\n"
"    enum Priority { LOW, MEDIUM, HIGH }\n"
"    enum Status { TODO, IN_PROGRESS, DONE }\n"
"    class Task {\n"
"        - id: Long\n"
"        - title: String\n"
"        - description: String\n"
"        - priority: Priority\n"
"        - status: Status\n"
"        - createdAt: LocalDateTime\n"
"        - updatedAt: LocalDateTime\n"
"    }\n"
"    Task --> Priority\n"
"    Task --> Status\n"
"}\n"
"package com.logistics.taskflow.service {\n"
"    interface TaskService {\n"
"        + createTask(dto: TaskCreateDTO): TaskResponseDTO\n"
"        + getAllTasks(s: Status, p: Priority): List<TaskResponseDTO>\n"
"        + getTaskById(id: Long): TaskResponseDTO\n"
"        + updateTask(id: Long, dto: TaskUpdateDTO): TaskResponseDTO\n"
"        + deleteTask(id: Long): void\n"
"    }\n"
"    class TaskServiceImpl implements TaskService {\n"
"        - repository: TaskRepository\n"
"    }\n"
"}\n"
"package com.logistics.taskflow.controller {\n"
"    class TaskController {\n"
"        - service: TaskService\n"
"    }\n"
"}\n"
"@enduml"
    )
    pdf.code_box(class_puml)
    
    pdf.chapter_title('5. Mudança de Escopo e sua Justificativa Ágil')
    escopo_text = (
        "Durante o desenvolvimento da Sprint 2, a startup de logística identificou que tratar todas as tarefas com a mesma "
        "importância gerava gargalos críticos. Entregas urgentes de cargas perecíveis ou insumos hospitalares eram agendadas "
        "no mesmo lote de correspondências internas.\n\n"
        "Com isso, houve uma solicitação urgente de mudança de escopo: a inclusão do atributo 'Priority' (Prioridade) com os "
        "níveis LOW, MEDIUM e HIGH, bem como a necessidade de filtrar e listar tarefas priorizando as entregas urgentes.\n\n"
        "Justificativa Ágil da Mudança:\n"
        "Seguindo o manifesto ágil (Responder às mudanças mais do que seguir um plano), a equipe adaptou a modelagem. "
        "Adicionamos o enum Priority, alteramos a entidade Task e expusemos novas rotas no Controller. A estrutura modular "
        "do projeto permitiu implementar esse requisito e testá-lo sem impactar o funcionamento do que já estava rodando, "
        "provando a resiliência e o valor de uma arquitetura limpa em camadas."
    )
    pdf.chapter_body(escopo_text)

    # ------------------ PAGE 5: SEÇÃO 6 & 7 ------------------
    pdf.add_page()
    
    pdf.chapter_title('6. Testes Automatizados Utilizados')
    testes_text = (
        "A qualidade técnica do backend foi blindada com uma suite abrangente de testes automatizados utilizando JUnit 5 e Mockito:\n\n"
        "1. Testes Unitários de Serviço (TaskServiceImplTest):\n"
        "Focados na lógica interna da aplicação. Usando Mockito para simular a camada de banco de dados (TaskRepository), "
        "verificamos se a data de criação e atualização é preenchida corretamente, se exceções customizadas do tipo "
        "ResourceNotFoundException são lançadas quando um ID inexistente é requisitado, e se as transformações de dados ocorrem de forma íntegra.\n\n"
        "2. Testes de Fatias da Camada Web (TaskControllerTest):\n"
        "Usando @WebMvcTest e MockMvc, estes testes simulam chamadas HTTP diretamente nos endpoints controladores sem a sobrecarga "
        "de levantar o servidor completo. Eles validam se o Spring Boot rejeita JSONs malformados ou que violam as regras JSR-380 "
        "(como títulos nulos ou com menos de 3 caracteres), retornando o status 400 Bad Request, e se responde com 200/201 quando as regras de negócio são satisfeitas."
    )
    pdf.chapter_body(testes_text)
    
    pdf.chapter_title('7. DevOps, CI/CD e Garantia de Build Saudável')
    devops_text = (
        "Para garantir que nenhuma alteração de código quebre os testes ou impeça a compilação do projeto, configurou-se uma "
        "pipeline de Integração Contínua (CI) usando GitHub Actions (.github/workflows/maven.yml).\n\n"
        "Toda vez que um novo commit é enviado (push) ou uma proposta de integração é submetida (pull request), o GitHub "
        "Actions inicia uma máquina virtual Ubuntu, instala o JDK 17 da Temurin, baixa as dependências Maven em cache para acelerar "
        "o build, compila o código fonte e roda todos os testes automatizados da suíte. A aprovação da esteira de testes é obrigatória "
        "para permitir o deploy ou integração no branch principal (main), estabelecendo uma barreira de segurança contínua."
    )
    pdf.chapter_body(devops_text)

    # ------------------ PAGE 6: HISTÓRICO DE COMMITS DO GITHUB ------------------
    pdf.add_page()
    pdf.chapter_title('8. Histórico de Commits e Estrutura de Arquivos no GitHub')
    pdf.chapter_body(
        "Abaixo está registrado o histórico completo de commits realizados no repositório GitHub. O projeto conta com "
        "27 commits no total, demonstrando o progresso incremental do desenvolvimento:"
    )
    
    # Commit table setup
    pdf.set_font('Arial', 'B', 8)
    pdf.set_text_color(51, 65, 85)
    pdf.set_fill_color(241, 245, 249)
    pdf.cell(15, 6, clean_txt('Commit'), 1, 0, 'C', True)
    pdf.cell(100, 6, clean_txt('Mensagem do Commit (Histórico do Git)'), 1, 0, 'L', True)
    pdf.cell(55, 6, clean_txt('Categoria e Impacto no Escopo'), 1, 1, 'L', True)
    
    pdf.set_font('Arial', '', 7.5)
    pdf.set_text_color(30, 41, 59)
    
    commits = [
        ("6974649", "feat: add beautiful dark-themed SPA frontend for task management", "Feature: Criação do Painel Visual do Cliente"),
        ("507ed61", "refactor: replace manual annotations with @SpringBootApplication", "Refatoração: Simplificação do Entry Point do app"),
        ("e93d419", "docs: add CONTRIBUTING.md with conventions", "Documentação: Padrões de commits e contribuição"),
        ("69bc90a", "docs: add javadoc to all TaskController endpoints", "Documentação: Descrição detalhada dos endpoints Java"),
        ("02cc221", "docs: add CHANGELOG.md with version history", "Documentação: Linha do tempo oficial v1.0.0/1.1.0"),
        ("ca042e6", "docs: add javadoc and min size constraint to TaskUpdateDTO", "Refinamento: Correção de validações de DTO"),
        ("746c842", "feat: strengthen validation in TaskCreateDTO", "Feature: Restrições de tamanho mínimo no título"),
        ("1e9c1b4", "config: improve application.yml (error settings, debug)", "Configuração: Logs e exibição de erros do Spring"),
        ("0286754", "docs: add full javadoc to TaskService interface", "Documentação: Comentários técnicos no Service"),
        ("18890a2", "feat: add IllegalArgumentException handler to global exception", "Feature: Tratamento inteligente de erros globais"),
        ("3725e3d", "feat: add search tasks by title endpoint", "Feature: Busca textual e dinâmica de tarefas"),
        ("0664641", "refactor: use ResourceNotFoundException.forTask() in service", "Refatoração: Uso de fábrica estática de exceções"),
        ("c2cc077", "refactor: add static factory method to exception class", "Refatoração: Factory method na exceção"),
        ("70304c1", "feat: add search by title method to TaskRepository", "Feature: Método customizado query Spring Data"),
        ("a0e6d51", "docs: add javadoc to Priority and Status enums", "Documentação: Documentação de tipos enums"),
        ("4c32ed9", "docs: add javadoc to Task entity fields", "Documentação: Comentários de código da entidade"),
        ("3e36f7d", "chore: add javadoc to main application entry point", "Documentação: Javadoc do bootstrap do projeto"),
        ("fb08a3e", "release: finalize project", "Release: Finalização da primeira release estável"),
        ("465ad04", "docs: update README", "Documentação: Atualização do manual de uso da API"),
        ("eb81466", "ci: configure GitHub Actions", "DevOps: Criação da pipeline de CI no GitHub"),
        ("c494403", "feat: add task priority feature", "Feature: Mudança de Escopo (Prioridades)"),
        ("d3e363c", "test: add unit tests", "Testes: Criação dos testes com JUnit e Mockito"),
        ("15a4b57", "feat: create REST controllers", "Feature: Camada HTTP exposta à rede"),
        ("1c5b9c9", "feat: implement service layer", "Feature: Implementação de regras e transações"),
        ("b15415f", "feat: implement repository layer", "Feature: Implementação do acesso à dados"),
        ("e8ab624", "feat: add Task entity", "Feature: Criação do modelo de dados inicial"),
        ("3f8584e", "feat: create initial Spring Boot project", "Bootstrap: Estrutura inicial gerada pelo Maven"),
    ]
    
    for i, commit in enumerate(commits):
        # alternate backgrounds
        fill = i % 2 == 0
        if fill:
            pdf.set_fill_color(248, 250, 252)
        else:
            pdf.set_fill_color(255, 255, 255)
        pdf.cell(15, 5.5, clean_txt(commit[0]), 1, 0, 'C', fill)
        pdf.cell(100, 5.5, clean_txt(commit[1]), 1, 0, 'L', fill)
        pdf.cell(55, 5.5, clean_txt(commit[2]), 1, 1, 'L', fill)

    # ------------------ PAGE 7: PROJETO E ARQUITETURA NO TERMINAL ------------------
    pdf.add_page()
    pdf.chapter_title('9. Estrutura de Diretórios do Projeto e Validação Técnica')
    pdf.chapter_body(
        "A estrutura física de arquivos do projeto segue o padrão oficial recomendado pelo Maven e Spring Boot. "
        "Abaixo está listada a árvore de arquivos gerada e validada no ambiente local do desenvolvedor:"
    )
    
    project_tree = (
"TaskFlow/ (Raiz do Projeto)\n"
"├── .github/\n"
"│   └── workflows/\n"
"│       └── maven.yml          <-- Arquivo de Configuração CI (GitHub Actions)\n"
"├── docs/\n"
"│   ├── final_report.md        <-- Relatório acadêmico em formato Markdown\n"
"│   └── Relatorio_Final_TaskFlow.pdf  <-- Este documento oficial compilado\n"
"├── src/\n"
"│   ├── main/\n"
"│   │   ├── java/\n"
"│   │   │   └── com/logistics/taskflow/\n"
"│   │   │       ├── controller/       <-- Controllers (TaskController, TaskSearchController)\n"
"│   │   │       ├── dto/              <-- Data Transfer Objects (Create, Update, Response)\n"
"│   │   │       ├── entity/           <-- Entidades JPA (Task, Priority, Status)\n"
"│   │   │       ├── exception/        <-- Tratamento Global (GlobalExceptionHandler)\n"
"│   │   │       ├── repository/       <-- Interfaces Spring Data JPA\n"
"│   │   │       ├── service/          <-- Serviços (TaskService, TaskServiceImpl)\n"
"│   │   │       └── TaskFlowApplication.java  <-- Ponto de entrada (Main Bootstrap)\n"
"│   │   └── resources/\n"
"│   │       ├── static/\n"
"│   │       │   └── index.html    <-- Painel Visual em HTML5/CSS3/JavaScript (Dark Theme)\n"
"│   │       └── application.yml   <-- Configurações e Logs da aplicação\n"
"│   └── test/\n"
"│       └── java/\n"
"│           └── com/logistics/taskflow/\n"
"│               ├── controller/\n"
"│               │   └── TaskControllerTest.java <-- Testes de rotas HTTP com MockMvc\n"
"│               └── service/\n"
"│                   └── TaskServiceTest.java     <-- Testes unitários com Mockito\n"
"├── CHANGELOG.md               <-- Linha de evolução e versões do software\n"
"├── CONTRIBUTING.md            <-- Regras de commit e contribuição\n"
"├── README.md                  <-- Guia oficial de instalação e uso das rotas\n"
"└── pom.xml                    <-- Configurações de dependências Maven\n"
    )
    pdf.code_box(project_tree)
    
    pdf.chapter_title('10. Conclusão')
    conclusao_text = (
        "O TaskFlow consolidou-se como um projeto acadêmico de excelência técnica. A adoção estruturada de metodologias "
        "ágeis (Scrum, Kanban e Commits incrementais) em sinergia com pilares da Engenharia de Software (modelagem UML, "
        "arquitetura limpa em camadas, isolamento de DTOs e testes automatizados) garantiu uma entrega robusta e flexível.\n\n"
        "A mudança de escopo ocorrida na metade do ciclo de desenvolvimento comprovou que sistemas bem modelados se adaptam "
        "facilmente a novas necessidades sem quebrar códigos preexistentes. A validação via pipeline CI no GitHub Actions "
        "e a criação de um painel web intuitivo elevam o projeto ao padrão exigido pelo mercado de logística moderna.\n\n"
        "Este relatório atesta que o projeto cumpre integralmente todos os requisitos pedagógicos estipulados pela universidade."
    )
    pdf.chapter_body(conclusao_text)

    # Save to disk
    pdf.output(filename, 'F')

if __name__ == '__main__':
    build_pdf('docs/Relatorio_Final_TaskFlow.pdf')
    # Copy to workspace root too
    build_pdf('Relatorio_Final_TaskFlow.pdf')
    print("PDFs generated successfully!")
