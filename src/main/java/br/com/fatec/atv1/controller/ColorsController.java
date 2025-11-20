package br.com.fatec.atv1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/colors", produces = MediaType.APPLICATION_JSON_VALUE)
public class ColorsController {

    private final Map<Integer, Map<String, String>> colors = new HashMap<>(Map.of(
        1, Map.of("name", "Rosa", "hexCode", "#FFC0CB", "category", "Quente", "rgb", "RGB(255, 192, 203)"),
        2, Map.of("name", "Marrom", "hexCode", "#A52A2A", "category", "Neutra", "rgb", "RGB(165, 42, 42)"),
        3, Map.of("name", "Verde", "hexCode", "#008000", "category", "Fria", "rgb", "RGB(0, 128, 0)")
    ));

    // GET ALL
    @GetMapping
    public ResponseEntity<Map<Integer, Map<String, String>>> getColors() {
        return ResponseEntity.ok(colors);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getColorById(@PathVariable Integer id) {

        Map<String, String> color = colors.get(id);

        if (color == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "message", "Cor não encontrada",
                    "id", id.toString()
                ));
        }

        return ResponseEntity.ok(
            Map.of(
                "id", id.toString(),
                "name", color.get("name"),
                "hexCode", color.get("hexCode"),
                "category", color.get("category"),
                "rgb", color.get("rgb")
            )
        );
    }

    // POST - ADD COLOR
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addColor(
            @RequestBody Map<String, String> requestColor) {

        Integer id = Integer.parseInt(requestColor.get("id"));
        String name = requestColor.get("name");
        String hexCode = requestColor.get("hexCode");
        String category = requestColor.get("category");
        String rgb = requestColor.get("rgb");

        colors.put(id, Map.of(
            "name", name,
            "hexCode", hexCode,
            "category", category,
            "rgb", rgb
        ));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of(
                "id", id,
                "name", name,
                "hexCode", hexCode,
                "category", category,
                "rgb", rgb,
                "message", "Cor adicionada com sucesso"
            ));
    }
}