package br.eti.claudiney.curso.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.eti.claudiney.curso.web.jpa.entities.Ocorrencia;
import br.eti.claudiney.curso.web.jpa.factory.DBFactory;

public final class OcorrenciaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public OcorrenciaServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * Recebe o nome da ocorrência
		 */

		String id = request.getParameter("id");
		String nomeOcorrencia = request.getParameter("nomeOcorrencia");

		if (nomeOcorrencia == null || nomeOcorrencia.trim().isEmpty()) {
			response.sendRedirect("formOcorrencia.jsp?mensagem="
					+ URLEncoder.encode("Nome da Ocorrência não informado", "ISO-8859-1"));
			return;
		}

		/*
		 * Grava a ocorrência no banco de dados
		 */
		EntityManager entityManager = null;
		
		try {
			
			Ocorrencia ocorrencia = null;
			
			entityManager = DBFactory.INSTANCE.getEntityManager();
			
			entityManager.getTransaction().begin();
			
			if(id == null) {
				
				ocorrencia = new Ocorrencia();
				ocorrencia.setNomeOcorrencia(nomeOcorrencia);
				entityManager.persist(ocorrencia);
				
			} else {
				
				ocorrencia = entityManager.find(Ocorrencia.class, Integer.valueOf(id));
				ocorrencia.setNomeOcorrencia(nomeOcorrencia);
				entityManager.merge(ocorrencia);
				
			}
			
			entityManager.getTransaction().commit();
			
		} catch (Exception e) {
			response.sendRedirect("formOcorrencia.jsp?mensagem=" + URLEncoder
					.encode("Erro ao gravar ocorrência no banco de dados: " + e.getMessage(), "ISO-8859-1"));
			return;
		} finally {
			if(entityManager != null) {
				entityManager.close();
			}
		}
		
		/*
		 * Retorna para lista de ocorrencias
		 */
		response.sendRedirect("listaOcorrencia.jsp");

	}

	@Override
	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if( "editar".equals(acao) ) {
			buscar(request, response);
		} else if("remover".equals(acao)) {
			excluir(request, response);
		} else {
			response.sendRedirect("listaOcorrencia.jsp?mensagem="+ URLEncoder.encode(
					"Ação inválida", "ISO-8859-1"));
			return;
		}
		
	}
	
	private void buscar(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		/*
		 * Grava a ocorrência no banco de dados
		 */
		EntityManager entityManager = null;
		
		Ocorrencia ocorrencia = null;
		
		try {
			entityManager = DBFactory.INSTANCE.getEntityManager();
			ocorrencia = entityManager.find(Ocorrencia.class, Integer.valueOf(id));
		} catch (Exception e) {
			response.sendRedirect("formOcorrencia.jsp?mensagem=" + URLEncoder.encode(
					"Erro ao gravar ocorrência no banco de dados: " + e.getMessage(), "ISO-8859-1"));
			return;
		} finally {
			if(entityManager != null) {
				entityManager.close();
			}
		}
		
		String url = "formOcorrencia.jsp";
		url += "?id="+ocorrencia.getId();
		url += "&nomeOcorrencia="+URLEncoder.encode(ocorrencia.getNomeOcorrencia().trim(), "ISO-8859-1");
		
		response.sendRedirect(url);
		
	}
	
	private void excluir(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		/*
		 * Grava a ocorrência no banco de dados
		 */
		EntityManager entityManager = null;
		
		try {
			
			entityManager = DBFactory.INSTANCE.getEntityManager();
			
			Ocorrencia ocorrencia = entityManager.find(Ocorrencia.class, Integer.valueOf(id));
			
			entityManager.getTransaction().begin();
			entityManager.remove(ocorrencia);
			entityManager.getTransaction().commit();
			
		} catch (Exception e) {
			response.sendRedirect("formOcorrencia.jsp?mensagem=" + URLEncoder.encode(
					"Erro ao gravar ocorrência no banco de dados: " + e.getMessage(), "ISO-8859-1"));
			return;
		} finally {
			if(entityManager != null) {
				entityManager.close();
			}
		}
		
		response.sendRedirect("listaOcorrencia.jsp");
		
	}

}
