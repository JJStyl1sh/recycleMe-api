package com.aep.recycleMe.controller;

import com.aep.recycleMe.dto.CollectionPointDTO;
import com.aep.recycleMe.service.CollectionPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pontos-coleta")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS
})
public class CollectionPointController {

    @Autowired
    private CollectionPointService collectionPointService;

    @GetMapping
    public ResponseEntity<List<CollectionPointDTO>> getAllPoints() {
        List<CollectionPointDTO> points = collectionPointService.findAll();
        return ResponseEntity.ok(points);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionPointDTO> getPointById(@PathVariable Long id) {
        CollectionPointDTO point = collectionPointService.findById(id);
        return ResponseEntity.ok(point);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<CollectionPointDTO>> filterPoints(
            @RequestParam(required = false) String materiais,
            @RequestParam(required = false) String tipos) {

        List<String> materialList = materiais != null ?
                List.of(materiais.split(",")) : List.of();
        List<String> typeList = tipos != null ?
                List.of(tipos.split(",")) : List.of();

        List<CollectionPointDTO> points = collectionPointService
                .findByFilters(materialList, typeList);
        return ResponseEntity.ok(points);
    }

    @GetMapping("/proximos")
    public ResponseEntity<List<CollectionPointDTO>> getNearbyPoints(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "10") Double raio) {

        List<CollectionPointDTO> points = collectionPointService
                .findNearbyPoints(lat, lng, raio);
        return ResponseEntity.ok(points);
    }

    @PostMapping
    public ResponseEntity<CollectionPointDTO> createPoint(
            @RequestBody CollectionPointDTO pointDTO) {
        CollectionPointDTO createdPoint = collectionPointService.create(pointDTO);
        return ResponseEntity.ok(createdPoint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionPointDTO> updatePoint(
            @PathVariable Long id,
            @RequestBody CollectionPointDTO pointDTO) {
        CollectionPointDTO updatedPoint = collectionPointService.update(id, pointDTO);
        return ResponseEntity.ok(updatedPoint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoint(@PathVariable Long id) {
        collectionPointService.delete(id);
        return ResponseEntity.noContent().build();
    }
}