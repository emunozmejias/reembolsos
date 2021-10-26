package cl.solicitud.reembolsoservice.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cl.solicitud.reembolsoservice.model.SolicitudReembolso;
import cl.solicitud.reembolsoservice.repository.ReembolsoDao;

@Repository
public class ReembolsoDaoImpl implements ReembolsoDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<SolicitudReembolso> consultaSolicitudbyFiltro(Integer idUsuario, Integer idEstado, Date fechaDesde,
			Date fechaHasta) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SolicitudReembolso> criteriaQuery = criteriaBuilder.createQuery(SolicitudReembolso.class);
		Root<SolicitudReembolso> itemRoot = criteriaQuery.from(SolicitudReembolso.class);
		Predicate predUsuario = null;
		Predicate predFecha = null;
		Predicate predEstado = null;
		Predicate predFinal = null;		
		List<SolicitudReembolso> items = null;

		if (idUsuario > 0) {
			predUsuario = criteriaBuilder.equal(itemRoot.get("idUsuario"), idUsuario);
		}

		if (idEstado > 0) {
			predEstado = criteriaBuilder.equal(itemRoot.get("idEstadoReembolso"), idEstado);
		}

		if (fechaDesde != null && fechaHasta != null) {
			predFecha = criteriaBuilder.between(itemRoot.get("fechaSolicitud"), fechaDesde, fechaHasta);		
		}else if (fechaDesde != null) {
			predFecha = criteriaBuilder.greaterThanOrEqualTo(itemRoot.get("fechaSolicitud"), fechaDesde);
		}else if (fechaHasta != null) {
			predFecha = criteriaBuilder.lessThanOrEqualTo(itemRoot.get("fechaSolicitud"), fechaHasta);
		}
		
		if (predUsuario != null && predEstado == null && predFecha == null) { // filtro por usuario
			predFinal = criteriaBuilder.and(predUsuario);
		}else if (predUsuario != null && predEstado != null && predFecha == null) { // filtro por usuario y estado
			predFinal = criteriaBuilder.and(predUsuario, predEstado);
		}else if (predUsuario != null && predEstado == null && predFecha != null) { // filtro por usuario y fecha
			predFinal = criteriaBuilder.and(predUsuario, predFecha);
		}else if (predUsuario == null && predEstado != null && predFecha == null) { // filtro por estado
			predFinal = criteriaBuilder.and(predEstado);
		}else if (predUsuario == null && predEstado == null && predFecha != null) { // filtro por fecha
			predFinal = criteriaBuilder.and(predFecha);
		}else if (predUsuario == null && predEstado != null && predFecha != null) { // filtro por estado y fecha
			predFinal = criteriaBuilder.and(predEstado, predFecha);
		}else if (predUsuario != null && predEstado != null && predFecha != null) { // filtro por todos
			predFinal = criteriaBuilder.and(predUsuario, predFecha, predEstado);
		}
			
		
		if (predFinal != null) {
			criteriaQuery.where(predFinal).orderBy(criteriaBuilder.desc(itemRoot.get("fechaSolicitud")));
			items = entityManager.createQuery(criteriaQuery).getResultList();
		} else {
			items = entityManager.createQuery(criteriaQuery).getResultList();
		}
		 
		return items;
	}

}
