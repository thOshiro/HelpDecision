package model.domain.servicos;

import java.util.Date;
import java.util.List;

import model.domain.entidades.ChamadaMetodo;
import model.domain.repositorios.RepositorioChamadaMetodo;

public class ChamadaMetodoServico {

	private RepositorioChamadaMetodo chamadaMetodoRepositorio = new RepositorioChamadaMetodo();

	public ChamadaMetodoServico() {

	}

	public static ChamadaMetodoServico novo() {
		return new ChamadaMetodoServico();
	}

	public List<ChamadaMetodo> inserirDadosNaTbChamadaMetodo(List<ChamadaMetodo> listaChamadaMetodo) {
		return chamadaMetodoRepositorio.insert(listaChamadaMetodo);
	}
	
	public List<ChamadaMetodo> buscarPorDuracao(long inicio, long fim){
		return chamadaMetodoRepositorio.buscarPorDuracao(inicio, fim);
	}

	public List<ChamadaMetodo> buscarPorData(Date inicio, Date fim){
		return chamadaMetodoRepositorio.buscarPorData(inicio, fim);
	}
	
	public List<ChamadaMetodo> buscarPorServido(String nomeDoServidor){
		return chamadaMetodoRepositorio.buscaPorServidor(nomeDoServidor);
	}
	
}
