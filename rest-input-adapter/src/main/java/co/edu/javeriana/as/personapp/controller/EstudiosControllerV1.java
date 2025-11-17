package co.edu.javeriana.as.personapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.as.personapp.adapter.EstudiosInputAdapterRest;
import co.edu.javeriana.as.personapp.model.request.EstudiosRequest;
import co.edu.javeriana.as.personapp.model.response.EstudiosResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/estudios")
public class EstudiosControllerV1 {

    @Autowired
    private EstudiosInputAdapterRest estudiosInputAdapterRest;

    @ResponseBody
    @GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EstudiosResponse> estudios(@PathVariable String database) {
        log.info("Into estudios REST API");
        return estudiosInputAdapterRest.historial(database.toUpperCase());
    }

    @ResponseBody
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EstudiosResponse crearEstudio(@RequestBody EstudiosRequest request) {
        log.info("Into crearEstudio REST API");
        return estudiosInputAdapterRest.crearEstudio(request);
    }

    @ResponseBody
    @PutMapping(path = "/{personId}/{professionId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public EstudiosResponse actualizarEstudio(@PathVariable Integer personId, @PathVariable Integer professionId, @RequestBody EstudiosRequest request) {
        log.info("Into actualizarEstudio REST API");
        return estudiosInputAdapterRest.actualizarEstudio(personId, professionId, request);
    }

    @ResponseBody
    @DeleteMapping(path = "/{personId}/{professionId}/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean eliminarEstudio(@PathVariable Integer personId, @PathVariable Integer professionId, @PathVariable String database) {
        log.info("Into eliminarEstudio REST API");
        return estudiosInputAdapterRest.eliminarEstudio(personId, professionId, database.toUpperCase());
    }
}