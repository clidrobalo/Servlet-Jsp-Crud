package beans;


public class UsuarioBean {
	private Long id;
	private String user;
	private String nome;
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String uf;
	private String ibge;
	private String telefone;
	private String senha;
	private String fotoBase64;
	private String  contentType; 
	private String tempFotoUser;
	private String curriculo;
	private String contentTypeCurriculo; 
	private String miniaturaBase64;
	private String tipoAcesso;
	private boolean ativo;
	private String sexo;
	/*
	 * Metodo que reconstroi a imagem para ser apresentada na UI
	 */
	public String getTempFotoUser() {
		this.tempFotoUser = "data:" + this.contentType + ";base64," + fotoBase64;
		return this.tempFotoUser;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getIbge() {
		return ibge;
	}
	public void setIbge(String ibge) {
		this.ibge = ibge;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getFotoBase64() {
		return fotoBase64;
	}
	public void setFotoBase64(String fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(String curriculo) {
		this.curriculo = curriculo;
	}

	public String getContentTypeCurriculo() {
		return contentTypeCurriculo;
	}

	public void setContentTypeCurriculo(String contentTypeCurriculo) {
		this.contentTypeCurriculo = contentTypeCurriculo;
	}



	public String getMiniaturaBase64() {
		return miniaturaBase64;
	}



	public void setMiniaturaBase64(String miniaturaBase64) {
		this.miniaturaBase64 = miniaturaBase64;
	}



	public String getTipoAcesso() {
		return tipoAcesso;
	}



	public void setTipoAcesso(String acessoPermitido) {
		this.tipoAcesso = acessoPermitido;
	}

	public boolean isAdmin() {
		return this.tipoAcesso.equals("Admin");
	}
	
	public boolean hasFoto() {
		return  this.contentType.contains("image");
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	
}
