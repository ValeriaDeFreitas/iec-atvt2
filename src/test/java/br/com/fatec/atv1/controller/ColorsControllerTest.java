package br.com.fatec.atv1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ColorsController.class)
public class ColorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Teste GET ALL
    @Test
    public void testGetAllColors() throws Exception {
        mockMvc.perform(get("/colors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1.name").value("Rosa"))
                .andExpect(jsonPath("$.2.name").value("Marrom"))
                .andExpect(jsonPath("$.3.name").value("Verde"));
    }

    // Teste GET BY ID - Sucesso
    @Test
    public void testGetColorById_Success() throws Exception {
        mockMvc.perform(get("/colors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Rosa"))
                .andExpect(jsonPath("$.hexCode").value("#FFC0CB"))
                .andExpect(jsonPath("$.category").value("Quente"))
                .andExpect(jsonPath("$.rgb").value("RGB(255, 192, 203)"));
    }

    // Teste GET BY ID - Não encontrado
    @Test
    public void testGetColorById_NotFound() throws Exception {
        mockMvc.perform(get("/colors/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cor não encontrada"))
                .andExpect(jsonPath("$.id").value("999"));
    }

    // Teste POST - Adicionar nova cor
    @Test
    public void testAddColor() throws Exception {
        String colorJson = """
            {
                "id": "4",
                "name": "Azul",
                "hexCode": "#0000FF",
                "category": "Fria",
                "rgb": "RGB(0, 0, 255)"
            }
        """;

        mockMvc.perform(post("/colors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(colorJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Azul"))
                .andExpect(jsonPath("$.hexCode").value("#0000FF"))
                .andExpect(jsonPath("$.category").value("Fria"))
                .andExpect(jsonPath("$.rgb").value("RGB(0, 0, 255)"))
                .andExpect(jsonPath("$.message").value("Cor adicionada com sucesso"));
    }
}