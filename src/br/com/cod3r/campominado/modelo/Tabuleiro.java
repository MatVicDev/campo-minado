package br.com.cod3r.campominado.modelo;

import br.com.cod3r.campominado.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
    private int qtd_linhas;
    private int qtd_colunas;
    private int qtd_minas;

    List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int qtd_linhas, int qtd_colunas, int qtd_minas) {
        this.qtd_linhas = qtd_linhas;
        this.qtd_colunas = qtd_colunas;
        this.qtd_minas = qtd_minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        try {
            campos.parallelStream()
                    .filter(c -> c.getLinha() == linha)
                    .filter(c -> c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(c -> c.abrir());
        } catch (ExplosaoException e) {
            campos.forEach(c -> c.setIsAberto(true));
            throw e;
        }
    }

    public void alternarMarcacao(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha)
                .filter(c -> c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }

    private void gerarCampos() {
        for (int i = 0; i < qtd_linhas; i++) {
            for (int j = 0; j < qtd_colunas; j++) {
                campos.add(new Campo(i, j));
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.addVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.getIsMinado();

        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < qtd_minas);
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        int i = 0;

        for (int l = 0; l < qtd_linhas; l++) {
            for (int c = 0; c < qtd_colunas; c++) {
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
