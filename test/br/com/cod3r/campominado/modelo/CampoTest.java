package br.com.cod3r.campominado.modelo;

import br.com.cod3r.campominado.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampoTest {
    private Campo campo;

    @BeforeEach
    void iniciarCampo() {
        campo = new Campo(3, 3);
    }

    @Test
    void vizinhoDistancia() {
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.addVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void naoVizinhoDistancia() {
        Campo vizinho = new Campo(1, 1);
        boolean resultado = campo.addVizinho(vizinho);

        assertFalse(resultado);
    }

    @Test
    void verificarValorPadraoAtributoIsMarcado() {
        assertFalse(campo.getIsMarcado());
    }

    @Test
    void alterarValorIsMarcadoQuandoChamarMetodoAlternarMarcacao() {
        campo.alternarMarcacao();
        campo.alternarMarcacao();

        assertFalse(campo.getIsMarcado());
    }

    @Test
    void abrirNaoMinadoNaoMarcado() {
        assertTrue(campo.abrir());
    }

    @Test
    void abrirNaoMinadoMarcado() {
        campo.alternarMarcacao();

        assertFalse(campo.abrir());
    }

    @Test
    void abrirMinadoNaoMarcado() {
        campo.minar();

        assertThrows(ExplosaoException.class, () -> {
            campo.abrir();
        });
    }

    @Test
    void abrirMinadoMarcado() {
        campo.alternarMarcacao();
        campo.minar();

        assertFalse(campo.abrir());
    }
}