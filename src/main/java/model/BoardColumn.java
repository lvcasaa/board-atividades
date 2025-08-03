package model;

public class BoardColumn {
    private int id;
    private int boardId;
    private String nome;
    private int ordem;
    private String tipo;

    public BoardColumn(int id, int boardId, String nome, int ordem, String tipo) {
        this.id = id;
        this.boardId = boardId;
        this.nome = nome;
        this.ordem = ordem;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public int getBoardId() { return boardId; }
    public String getNome() { return nome; }
    public int getOrdem() { return ordem; }
    public String getTipo() { return tipo; }
}
