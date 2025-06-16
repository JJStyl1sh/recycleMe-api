package com.aep.recycleMe.repository;

import com.aep.recycleMe.entity.CollectionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Long> {

    // Buscar por tipo de ponto
    List<CollectionPoint> findByTipo(String tipo);

    // Buscar por múltiplos tipos
    List<CollectionPoint> findByTipoIn(List<String> tipos);

    // Buscar pontos que aceitam determinados materiais
    @Query("SELECT DISTINCT cp FROM CollectionPoint cp JOIN cp.materiais m WHERE m IN :materiais")
    List<CollectionPoint> findByMateriaisIn(@Param("materiais") List<String> materiais);

    // Buscar por materiais e tipos combinados
    @Query("SELECT DISTINCT cp FROM CollectionPoint cp JOIN cp.materiais m " +
            "WHERE m IN :materiais AND cp.tipo IN :tipos")
    List<CollectionPoint> findByMateriaisInAndTipoIn(
            @Param("materiais") List<String> materiais,
            @Param("tipos") List<String> tipos);

    // Buscar pontos próximos usando coordenadas (query nativa para melhor performance)
    @Query(value = "SELECT * FROM pontos_coleta p WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:lng)) + " +
            "sin(radians(:lat)) * sin(radians(p.latitude)))) <= :radius " +
            "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(p.latitude)) * " +
            "cos(radians(p.longitude) - radians(:lng)) + " +
            "sin(radians(:lat)) * sin(radians(p.latitude))))",
            nativeQuery = true)
    List<CollectionPoint> findNearbyPoints(
            @Param("lat") Double latitude,
            @Param("lng") Double longitude,
            @Param("radius") Double radius);

    // Buscar por nome (busca parcial, case insensitive)
    @Query("SELECT cp FROM CollectionPoint cp WHERE LOWER(cp.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<CollectionPoint> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    // Buscar por endereço (busca parcial, case insensitive)
    @Query("SELECT cp FROM CollectionPoint cp WHERE LOWER(cp.endereco) LIKE LOWER(CONCAT('%', :endereco, '%'))")
    List<CollectionPoint> findByEnderecoContainingIgnoreCase(@Param("endereco") String endereco);

    // Buscar pontos ativos (assumindo que existe um campo 'ativo')
    // List<CollectionPoint> findByAtivoTrue();

    // Contar pontos por tipo
    @Query("SELECT cp.tipo, COUNT(cp) FROM CollectionPoint cp GROUP BY cp.tipo")
    List<Object[]> countByTipo();

    // Buscar pontos que aceitam um material específico
    @Query("SELECT cp FROM CollectionPoint cp JOIN cp.materiais m WHERE m = :material")
    List<CollectionPoint> findByMaterial(@Param("material") String material);

    // Buscar pontos em uma área específica (bounding box)
    @Query("SELECT cp FROM CollectionPoint cp WHERE " +
            "cp.latitude BETWEEN :minLat AND :maxLat AND " +
            "cp.longitude BETWEEN :minLng AND :maxLng")
    List<CollectionPoint> findPointsInBoundingBox(
            @Param("minLat") Double minLatitude,
            @Param("maxLat") Double maxLatitude,
            @Param("minLng") Double minLongitude,
            @Param("maxLng") Double maxLongitude);

    // Verificar se existe ponto com coordenadas exatas (para evitar duplicatas)
    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);

    // Buscar pontos ordenados por distância de um ponto específico
    @Query(value = "SELECT *, " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:lng)) + " +
            "sin(radians(:lat)) * sin(radians(latitude)))) AS distance " +
            "FROM pontos_coleta " +
            "ORDER BY distance",
            nativeQuery = true)
    List<CollectionPoint> findAllOrderedByDistance(
            @Param("lat") Double latitude,
            @Param("lng") Double longitude);
}