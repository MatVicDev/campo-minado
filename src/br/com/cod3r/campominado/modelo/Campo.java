package br.com.cod3r.campominado.modelo;

import br.com.cod3r.campominado.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int linha;
    private final int coluna;
    private boolean isAberto = false;
    private boolean hasMina = false;
    private boolean isMarcado = false;
    List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean addVizinho(Campo vizinho) {
        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;
        int deltaLinha = Math.abs(this.linha - vizinho.linha);
        int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    void alternarMarcacao() {
        if (!isAberto) {
            isMarcado = !isMarcado;
        }
    }

    boolean abrir() {
        if (!isAberto && !isMarcado) {
            isAberto = true;

            if (hasMina) {
                throw new ExplosaoException();
            }

            if (vizinhacaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }

            return true;
        }

        return false;
    }

    boolean vizinhacaSegura() {
        return vizinhos.stream().noneMatch(v -> v.hasMina);
    }

    public void minar() {
        hasMina = true;
    }

    public boolean getIsMinado() {
        return this.hasMina;
    }

    public boolean getIsMarcado() {
        return this.isMarcado;
    }

    void setIsAberto(boolean isAberto) {
        this.isAberto = isAberto;
    }

    public boolean getIsAberto() {
        return this.isAberto;
    }

    public int getLinha() {
        return this.linha;
    }

    public int getColuna() {
        return this.coluna;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !hasMina && isAberto;
        boolean protegido = hasMina && isMarcado;
        return desvendado || protegido;
    }

    long minasNaVizinhaca() {
        return vizinhos.stream().filter(v -> v.hasMina).count();
    }

    void reiniciar() {
        isAberto = false;
        hasMina = false;
        isMarcado = false;
    }

    public String toString() {
        if (isMarcado) {
            return "x";
        } else if (isAberto && hasMina) {
            return "*";
        } else if (isAberto && minasNaVizinhaca() > 0) {
            return Long.toString(minasNaVizinhaca());
        } else if (isAberto) {
            return " ";
        } else {
            return "?";
        }
    }
}
