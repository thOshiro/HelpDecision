package model.domain.servicos;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.Servidor;

public class ServicoFachada {

	private ArquivoLogServico servicoArquivoLog;
	private ChamadaMetodoServico servicoChamadaMetodo;
	private ServidorServico servicoServidor;
	private ChamadaMetodoArquivoLogServidorServico servicoAgregador;

	public ServicoFachada() {
		setServicoArquivoLog(ArquivoLogServico.novo());
		setServicoChamadaMetodo(ChamadaMetodoServico.novo());
		setServicoServidor(ServidorServico.novo());
		setServicoAgregador(ChamadaMetodoArquivoLogServidorServico.novo());
	}

	public Boolean inserirNovoArquivo(List<ChamadaMetodo> listaChamadaMetodo, ArquivoLog arquivoLog, Servidor servidor)
			throws SQLException {
		// Faz insert na tb_chamada_metodo
		// Faz insert na tb_arquivo
		// Recuperar o id_servidor a partir do seu nome
		// Agrega listChamadaMetodo, ArquivoLog e Servidor
		servicoAgregador.agregar(servicoChamadaMetodo.inserirDadosNaTbChamadaMetodo(listaChamadaMetodo),
				servicoArquivoLog.inserirDadosNaTbArquivo(arquivoLog), servicoServidor.recuperarIdServidor(servidor));
		return null;
	}

	// M�TODOS REFERENTE AO SERVICO CHAMADA METODO

	// M�TODOS REFERENTE AO SERVICO ARQUIVO LOG
	public ArquivoLog solicitarCriacaoArquivoLog(int idArquivo, String nomeArquivo, Date dataUpload, String descricao) {
		return servicoArquivoLog.criarArquivoLog(idArquivo, nomeArquivo, dataUpload, descricao);
	}

	public List<ArquivoLog> solicitarTodosArquivoLogDB() throws SQLException {
		return servicoArquivoLog.solicitarListaDeArquivoLogCadastradoDB();
	}

	// M�TODOS REFERENTE AO SERVICO SERVIDOR
	public Boolean cadastrarServidor(int idServidor, String nomeServidor) {
		return servicoServidor.cadastrarServidorDB(solicitarNovoServidor(idServidor, nomeServidor));
	}

	public Servidor solicitarNovoServidor(int idServidor, String nomeServidor) {
		return servicoServidor.criarServidor(idServidor, nomeServidor);
	}

	public List<Servidor> solicitarTodosServidoresDB() throws SQLException {
		return servicoServidor.solicitarListaDeServidoresCadastradosDB();
	}

	// Getters e setters
	public void setServicoArquivoLog(ArquivoLogServico servicoArquivoLog) {
		this.servicoArquivoLog = servicoArquivoLog;
	}

	public void setServicoChamadaMetodo(ChamadaMetodoServico servicoChamadaMetodo) {
		this.servicoChamadaMetodo = servicoChamadaMetodo;
	}

	public void setServicoServidor(ServidorServico servicoServidor) {
		this.servicoServidor = servicoServidor;
	}

	public void setServicoAgregador(ChamadaMetodoArquivoLogServidorServico servicoAgregador) {
		this.servicoAgregador = servicoAgregador;
	}

}
