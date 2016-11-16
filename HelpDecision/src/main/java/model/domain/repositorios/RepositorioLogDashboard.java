package model.domain.repositorios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.domain.agregadores.ChamadaMetodoArquivoLogServidor;
import model.domain.entidades.ArquivoLog;
import model.domain.entidades.ChamadaMetodo;
import model.domain.entidades.LogDashboard;
import model.domain.entidades.Servidor;
import model.domain.fabricas.FabricaChamadaMetodo;
import model.domain.fabricas.FabricaDashboard;

public class RepositorioLogDashboard {

	private List<LogDashboard> repositorioLogDashboard = new ArrayList<LogDashboard>();
	private int totalChamadas;
	static final String TABELA_CHAMADA_METODO = "tb_chamada_metodo";

	Connection conexao;

	public RepositorioLogDashboard() {
		this.conexao = new ConexaoDB().conectarDB();
	}

	public List<LogDashboard> buscarDashboardDoBanco() {

		String sql = "select nome_metodo, count(*) as quantidade_chamada, sum(duracao) as tempo_total, "
				+ "avg(duracao) as tempo_medio, max(duracao) as tempo_maior, min(duracao) as tempo_menor " + "from "
				+ TABELA_CHAMADA_METODO + " group by 1 " + "order by tempo_maior desc";

		sql = "select nome_metodo, count(*) as quantidade_chamada, sum(duracao) as tempo_total, "
				+ "avg(duracao) as tempo_medio, max(duracao) as tempo_maior, min(duracao) as tempo_menor, ser.nome_servidor "
				+ "from tb_chamada_metodo met join tb_chamada_metodo_arquivo_servidor mas on "
				+ "met.id_chamada_metodo = mas.id_chamada_metodo "
				+ "join tb_servidor ser on ser.id_servidor = mas.id_servidor "
				+ "join tb_arquivo ar on mas.id_arquivo = ar.id_arquivo " + "where (ar.arquivo_excluido != true) "
				+ "group by 1, 7 order by tempo_maior desc";

		try {
			Statement stm = (Statement) conexao.createStatement();
			ResultSet retornoSelect = stm.executeQuery(sql);
			while (retornoSelect.next()) {
				repositorioLogDashboard.add(FabricaDashboard.novoDashboard(retornoSelect.getString("nome_metodo"),
						retornoSelect.getInt("quantidade_chamada"), 0, retornoSelect.getLong("tempo_total"),
						retornoSelect.getFloat("tempo_medio"), retornoSelect.getLong("tempo_menor"),
						retornoSelect.getLong("tempo_maior"), 1, retornoSelect.getString("nome_servidor")));
				totalChamadas += retornoSelect.getInt("quantidade_chamada");
			}

			for (LogDashboard ld : repositorioLogDashboard) {
				ld.setQuantidadeChamadasTotal(totalChamadas);
				ld.setPorcentagemTotal(((ld.getQuantidadeDessaChamada() * 100.0f) / totalChamadas));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return repositorioLogDashboard;
	}

	public List<LogDashboard> filtrarPorTudo(int servidor, long duracaoInicio, long duracaoFim) throws SQLException {
		List<LogDashboard> resultado = new ArrayList<LogDashboard>();

		String sqlCodigo = "select nome_metodo, count(*) as quantidade_chamada, sum(duracao) as tempo_total, "
				+ "avg(duracao) as tempo_medio, max(duracao) as tempo_maior, min(duracao) as tempo_menor, ser.nome_servidor "
				+ "from tb_chamada_metodo met join tb_chamada_metodo_arquivo_servidor mas on "
				+ "met.id_chamada_metodo = mas.id_chamada_metodo "
				+ "join tb_servidor ser on ser.id_servidor = mas.id_servidor "
				+ "join tb_arquivo ar on mas.id_arquivo = ar.id_arquivo " + "where (ar.arquivo_excluido != true) "
				+ "and ser.id_servidor =  " + servidor + "  " + "and (met.duracao >= " + duracaoInicio
				+ " and met.duracao <= " + duracaoFim + ") " + "group by 1, 7 order by tempo_maior desc";

		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sqlCodigo);
			while (retornoSelect.next()) {
				resultado.add(FabricaDashboard.novoDashboard(retornoSelect.getString("nome_metodo"),
						retornoSelect.getInt("quantidade_chamada"), 0, retornoSelect.getLong("tempo_total"),
						retornoSelect.getFloat("tempo_medio"), retornoSelect.getLong("tempo_menor"),
						retornoSelect.getLong("tempo_maior"), 1, retornoSelect.getString("nome_servidor")));
				totalChamadas += retornoSelect.getInt("quantidade_chamada");
			}

			for (LogDashboard ld : resultado) {
				ld.setQuantidadeChamadasTotal(totalChamadas);
				ld.setPorcentagemTotal(((ld.getQuantidadeDessaChamada() * 100.0f) / totalChamadas));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultado;
	}
	
	public List<LogDashboard> filtrarPorTudo(int servidor, Timestamp dataInicio, Timestamp dataFim,
			long duracaoInicio, long duracaoFim) throws SQLException {
		List<LogDashboard> resultado = new ArrayList<LogDashboard>();

		String sqlCodigo = "select nome_metodo, count(*) as quantidade_chamada, sum(duracao) as tempo_total, "
				+ "avg(duracao) as tempo_medio, max(duracao) as tempo_maior, min(duracao) as tempo_menor, ser.nome_servidor "
				+ "from tb_chamada_metodo met join tb_chamada_metodo_arquivo_servidor mas on "
				+ "met.id_chamada_metodo = mas.id_chamada_metodo "
				+ "join tb_servidor ser on ser.id_servidor = mas.id_servidor "
				+ "join tb_arquivo ar on mas.id_arquivo = ar.id_arquivo " + "where (ar.arquivo_excluido != true) "
				+ "and ser.id_servidor =  " + servidor + "  " + "and (met.duracao >= " + duracaoInicio
				+ " and met.duracao <= " + duracaoFim + ") "
				+ "and (met.data_inicio >= ' " + dataInicio+ " ' and met.data_fim <= ' " + dataFim
				+ " ') group by 1, 7 order by tempo_maior desc";

		System.out.println("dataInicio " + dataInicio);
		System.out.println("dataFim " + dataFim);
		
		Statement stm = (Statement) conexao.createStatement();
		try {
			ResultSet retornoSelect = stm.executeQuery(sqlCodigo);
			while (retornoSelect.next()) {
				resultado.add(FabricaDashboard.novoDashboard(retornoSelect.getString("nome_metodo"),
						retornoSelect.getInt("quantidade_chamada"), 0, retornoSelect.getLong("tempo_total"),
						retornoSelect.getFloat("tempo_medio"), retornoSelect.getLong("tempo_menor"),
						retornoSelect.getLong("tempo_maior"), 1, retornoSelect.getString("nome_servidor")));
				totalChamadas += retornoSelect.getInt("quantidade_chamada");
			}

			for (LogDashboard ld : resultado) {
				ld.setQuantidadeChamadasTotal(totalChamadas);
				ld.setPorcentagemTotal(((ld.getQuantidadeDessaChamada() * 100.0f) / totalChamadas));
			}
		} catch (Exception e) {
			System.out.println("Error[psql]: " + e );
		}
		return resultado;
	}

	public int getTotalChamadas() {
		return totalChamadas;
	}

	public void setTotalChamadas(int totalChamadas) {
		this.totalChamadas = totalChamadas;
	}
}
