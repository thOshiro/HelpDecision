package controller.backbeans;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.Part;

import model.domain.entidades.Servidor;
import model.domain.servicos.ServicoFachada;

@SessionScoped
@ManagedBean
public class UploadBean {

	private Part arquivo;
	protected static final String NOME_DO_PROJETO = "HelpDecision";
	protected static final String CAMINHO_INTERNO = "src" + File.separator + "main" + File.separator + "webapp";
	protected static final String DIR_DO_TAR_GZ = "arquivo_log";
	protected static final String CAMINHO_ABSOLUTO_DO_PROJETO_WEB_CONTENT = System.getProperty("user.dir")
			+ File.separator + NOME_DO_PROJETO + File.separator + CAMINHO_INTERNO + File.separator + DIR_DO_TAR_GZ;
	private ServicoFachada servicoFachada = new ServicoFachada();

	private List<Servidor> servidores = new ArrayList<Servidor>();
	private ArrayList<SelectItem> listItems = null;


	public UploadBean(){
		
	}
	
	public void carregarDropDownServidores() {
		try {
			this.servidores = servicoFachada.solicitarTodosServidoresDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		listItems = new ArrayList<SelectItem>();
//		try {
//			this.servidores = servicoFachada.solicitarTodosServidoresDB();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (Servidor servidor : servidores) {
//			listItems.add(new SelectItem("teste", servidor.getNomeServidor()));
//		}
	}

	public void upload() throws IOException {

		File dirUpload = new File(CAMINHO_ABSOLUTO_DO_PROJETO_WEB_CONTENT);

		if (!dirUpload.exists()) {
			dirUpload.mkdirs();
		}

		arquivo.write(CAMINHO_ABSOLUTO_DO_PROJETO_WEB_CONTENT + File.separator + buscarNomeDoArquivo(arquivo));
	}

	public String buscarNomeDoArquivo(Part part) {

		String nomeArquivo;

		for (String cd : part.getHeader("content-disposition").split(";")) {
			nomeArquivo = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			return nomeArquivo.substring(nomeArquivo.lastIndexOf('/') + 1).substring(nomeArquivo.lastIndexOf('\\') + 1);
		}
		return null;
	}

	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}

	public List<Servidor> getServidores() {
		return servidores;
	}

	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
	}


	public ArrayList<SelectItem> getListItems() {
		return listItems;
	}


	public void setListItems(ArrayList<SelectItem> listItems) {
		this.listItems = listItems;
	}

}
