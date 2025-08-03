package model;

import java.time.LocalDateTime;

public class Card {
    private int id;
    private int boardId;
    private int colunaId;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
    private boolean bloqueado;
    private String motivoBloqueio;

    public Card(int id, int boardId, int colunaId, String titulo, String descricao,
                LocalDateTime dataCriacao, boolean bloqueado, String motivoBloqueio) {
        this.id = id;
        this.boardId = boardId;
        this.colunaId = colunaId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.bloqueado = bloqueado;
        this.motivoBloqueio = motivoBloqueio;
    }

    public int getId() { return id; }
    public int getBoardId() { return boardId; }
    public int getColunaId() { return colunaId; }
    public String getTitulo() { return titulo; }
    public boolean isBloqueado() { return bloqueado; }
}
