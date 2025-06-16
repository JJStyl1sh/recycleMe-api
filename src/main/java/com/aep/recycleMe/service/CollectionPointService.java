package com.aep.recycleMe.service;


import com.aep.recycleMe.dto.CollectionPointDTO;
import com.aep.recycleMe.entity.CollectionPoint;
import com.aep.recycleMe.repository.CollectionPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionPointService {

    @Autowired
    private CollectionPointRepository repository;

    public List<CollectionPointDTO> findAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CollectionPointDTO findById(Long id) {
        CollectionPoint point = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto não encontrado"));
        return convertToDTO(point);
    }

    public List<CollectionPointDTO> findByFilters(List<String> materials, List<String> types) {
        List<CollectionPoint> points;

        if (materials.isEmpty() && types.isEmpty()) {
            points = repository.findAll();
        } else if (materials.isEmpty()) {
            points = repository.findByTipoIn(types);
        } else if (types.isEmpty()) {
            points = repository.findByMateriaisIn(materials);
        } else {
            points = repository.findByMateriaisInAndTipoIn(materials, types);
        }

        return points.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CollectionPointDTO> findNearbyPoints(Double lat, Double lng, Double radius) {
        // Implementar busca por proximidade usando fórmula de Haversine
        List<CollectionPoint> allPoints = repository.findAll();

        return allPoints.stream()
                .filter(point -> calculateDistance(lat, lng, point.getLatitude(), point.getLongitude()) <= radius)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CollectionPointDTO create(CollectionPointDTO dto) {
        CollectionPoint point = convertToEntity(dto);
        CollectionPoint savedPoint = repository.save(point);
        return convertToDTO(savedPoint);
    }

    public CollectionPointDTO update(Long id, CollectionPointDTO dto) {
        CollectionPoint existingPoint = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto não encontrado"));

        updateEntityFromDTO(existingPoint, dto);
        CollectionPoint updatedPoint = repository.save(existingPoint);
        return convertToDTO(updatedPoint);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private CollectionPointDTO convertToDTO(CollectionPoint entity) {
        CollectionPointDTO dto = new CollectionPointDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setTipo(entity.getTipo());
        dto.setMateriais(entity.getMateriais());
        dto.setEndereco(entity.getEndereco());
        dto.setTelefone(entity.getTelefone());
        dto.setHorarioFuncionamento(entity.getHorarioFuncionamento());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setDescricao(entity.getDescricao());
        return dto;
    }

    private CollectionPoint convertToEntity(CollectionPointDTO dto) {
        CollectionPoint entity = new CollectionPoint();
        entity.setNome(dto.getNome());
        entity.setTipo(dto.getTipo());
        entity.setMateriais(dto.getMateriais());
        entity.setEndereco(dto.getEndereco());
        entity.setTelefone(dto.getTelefone());
        entity.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setDescricao(dto.getDescricao());
        return entity;
    }

    private void updateEntityFromDTO(CollectionPoint entity, CollectionPointDTO dto) {
        entity.setNome(dto.getNome());
        entity.setTipo(dto.getTipo());
        entity.setMateriais(dto.getMateriais());
        entity.setEndereco(dto.getEndereco());
        entity.setTelefone(dto.getTelefone());
        entity.setHorarioFuncionamento(dto.getHorarioFuncionamento());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setDescricao(dto.getDescricao());
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // Raio da Terra em km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}