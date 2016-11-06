package model.domain.entidades;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_arquivo", schema = "public")
public class ArquivoLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_arquivo")
	private int idArquivo;
	@Column(name = "nome_arquivo")
	private String nomeArquivo;
	@Column(name = "data_upload")
	private java.sql.Date dataUpload;
	@Column(name = "descricao")
	private String descricao;

	public ArquivoLog() {

	}

	private ArquivoLog(int idArquivo, String nomeArquivo, java.sql.Date dataUpload, String descricao) {
		this.idArquivo = idArquivo;
		this.nomeArquivo = nomeArquivo;
		this.dataUpload = dataUpload;
		this.descricao = descricao;
	}

	public static ArquivoLog novo(int idArquivo, String nomeArquivo, java.sql.Date dataUpload, String descricao) {
		return new ArquivoLog(idArquivo, nomeArquivo, dataUpload, descricao);
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public java.sql.Date getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(java.sql.Date dataUpload) {
		this.dataUpload = dataUpload;
	}

	public int getIdArquivo() {
		return idArquivo;
	}

	public void setIdArquivo(int idArquivo) {
		this.idArquivo = idArquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
