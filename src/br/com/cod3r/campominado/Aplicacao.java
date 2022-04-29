package br.com.cod3r.campominado;

import br.com.cod3r.campominado.modelo.Tabuleiro;
import br.com.cod3r.campominado.visao.TabuleiroConsole;

public class Aplicacao {

    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
        new TabuleiroConsole(tabuleiro);
    }
}
