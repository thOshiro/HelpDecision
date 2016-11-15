package model.domain.servicos;

import java.security.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.domain.agregadores.ChamadaMetodoArquivoLogServidor;
import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaChamadaMetodoArquivoLogServidor;
import model.domain.repositorios.RepositorioChamadaMetodoArquivoLogServidor;

public class ChamadaMetodoArquivoLogServidorServico {

	RepositorioChamadaMetodoArquivoLogServidor repositorioAgregador = new RepositorioChamadaMetodoArquivoLogServidor();
	
	public ChamadaMetodoArquivoLogServidorServico() {
	}

	public static ChamadaMetodoArquivoLogServidorServico novo() {
		return new ChamadaMetodoArquivoLogServidorServico();
	}

	public ChamadaMetodoArquivoLogServidor agregar(List<ChamadaMetodo> listaChamadaMetodo, ArquivoLog arquivoLog,
			Servidor servidor) {
		return FabricaChamadaMetodoArquivoLogServidor.nova().novaChamadaMetodoArquivoLogServidor(listaChamadaMetodo,
				arquivoLog, servidor);
	}
	
	public Boolean inserirAgregador(ChamadaMetodoArquivoLogServidor agregador){
  		return repositorioAgregador.insert(agregador);
	}
	
//	public List<ChamadaMetodo> filtrarPorTudo(String nomeServidor, long duracaoInicio, long duracaoFim, Date dataInicio,
//			Date dataFim){
//		try {
//			return repositorioAgregador.filtrarPorTudo(nomeServidor, duracaoInicio, duracaoFim, dataInicio, dataFim);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return new ArrayList<ChamadaMetodo>();
//	}
	public void removerAgregadorEmCascataByArquivoLogId(int idArquivoLog) throws SQLException {
		repositorioAgregador.removeAgregadorThreeByIdArquivoLog(idArquivoLog);
	}
	
	public List<ChamadaMetodoArquivoLogServidor> solicitarListaDeArquivoLogEServidorCadastradoDB() throws SQLException {
		return repositorioAgregador.findArquivoLogAndServidor();
	}

	public void solicitarDetalhesFromMetodoAndServidor(String nomeMetodo,
			String nomeServidor) {
		// TODO Auto-generated method stub
		//return repositorioAgregador.findDetailsFromArquivoLogAndServidor(nomeMetodo, nomeServidor);
	}

}
