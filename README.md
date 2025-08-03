# Board Atividades Java

Projeto simples em Java, feito para estudo de CRUD com MySQL e menus no console.

## 🚀 Funcionalidades
- Criar, listar e excluir boards
- Criar colunas padrão automaticamente
- Criar, mover, cancelar, bloquear e desbloquear cards
- Persistência em MySQL

▶️ Como executar
 1 -   Configure o banco MySQL no util/DBConnection.java
 
 2 -   Compile:
 ```bash
    javac -d out $(find src -name "*.java")
 ```
 3 -Execute:
 ```bash
    java -cp out Main
 ```
