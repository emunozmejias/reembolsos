package com.tecnova.reembolso.converter;

import java.util.List;

import com.tecnova.reembolso.entity.PrivilegioUsuario;

public class ConverterEmail {

	private static final String REMITENTE = "reembolso-no-reply@tecnova.cl";
	private static final String TITULO_REEMBOLSO = "[REEMBOLSO]: Revisar Ingreso de Reembolso";
	private static final String TITULO_REVISAR = "[REEMBOLSO]: Revisar Solicitud Reembolso";
	private static final String TXT_REVISAR_REEMBOLSO = "Tiene una solicitud de reembolso pendiente de Autorizar o Rechazar de: ";
	private static final String TITULO_AUTORIZADO = "[REEMBOLSO]: Reembolso Autorizado";
	private static final String TXT_SOLICITUD = "La solicitud de reembolso ";
	private static final String TXT_AUTORIZADO_REEMBOLSO = " esta autorizada de pago para: ";
	private static final String TITULO_RECHAZAR = "[REEMBOLSO]: Reembolso Rechazado";
	private static final String TXT_RECHAZADO_REEMBOLSO = "Su solicitud ha sido rechazada";
	private static final String TITULO_PAGADO_REEMBOLSO = "[REEMBOLSO]: Reembolso pagado";
	private static final String TXT_PAGADO_REEMBOLSO = "Su solicitud ha sido pagada";

	public static String[] getDestino(List<PrivilegioUsuario> usuarios) {
		String[] remitentes = new String[usuarios.size()];
		for (int i = 0; i < usuarios.size(); i++) {
			remitentes[i] = usuarios.get(i).getEmail();
		}
		return remitentes;
	}

	public static String getRemitente() {
		return REMITENTE;
	}

	public static String getTituloIngreso() {
		return TITULO_REEMBOLSO;
	}

	public static String getTextoReembolso(String usuario) {

		return "Existe un reembolso pendiente ingresado por el usuario " + usuario;
	}

	public static String getTituloRevisar() {
		return TITULO_REVISAR;
	}

	public static String getTextoRevisarReembolso() {
		return TXT_REVISAR_REEMBOLSO;
	}

	public static String getTituloAutorizado() {
		return TITULO_AUTORIZADO;
	}

	public static String getTextoAutorizadoReembolso() {
		return TXT_AUTORIZADO_REEMBOLSO;
	}

	public static String getTituloRechazar() {
		return TITULO_RECHAZAR;
	}

	public static String getTextoRechazarReembolso() {
		return TXT_RECHAZADO_REEMBOLSO;
	}

	public static String getTituloPagado() {
		return TITULO_PAGADO_REEMBOLSO;
	}

	public static String getTextoPagadoReembolso() {
		return TXT_PAGADO_REEMBOLSO;
	}

	public static String getTxtSolicitud() {
		return TXT_SOLICITUD;
	}

	
}
