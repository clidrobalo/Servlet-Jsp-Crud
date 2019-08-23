package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;

import beans.UsuarioBean;
import dao.DaoUsuario;

/**
 * Servlet implementation class CadastroServlet
 */
@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	DaoUsuario userDao = new DaoUsuario();
	
    public UsuarioServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		String  idUser = request.getParameter("id");
		String tipoAcesso = request.getParameter("tipoAcesso");
		
		System.out.println(acao);
		
		request.setAttribute("tipoAcesso", tipoAcesso);
		
		if(acao.equals("delete")) {
			userDao.delete(Long.parseLong(idUser));
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
			request.setAttribute("usuarios", userDao.getUsers(tipoAcesso));
			view.forward(request, response);
		} else if(acao.equals("editar")) {
			UsuarioBean bean = userDao.consultar(Long.parseLong(idUser));
			RequestDispatcher view = request.getRequestDispatcher("cadastroUsuario.jsp");
			request.setAttribute("usuarios", userDao.getUsers(tipoAcesso));
			request.setAttribute("user", bean);
			view.forward(request, response);
		} else if(acao.equals("listarTodos")) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", userDao.getUsers(tipoAcesso));
			view.forward(request, response);
		} else if(acao.equals("download")) {
			UsuarioBean user = userDao.consultar(Long.parseLong(idUser));
			//teste se user existe
			if(user != null) {
				String contentType = "";
				String tipo = request.getParameter("tipo");
				byte[] fileBytes = null;
				
				if(tipo.equals("imagem")) {
					contentType = user.getContentType();
					fileBytes = new Base64().decodeBase64(user.getFotoBase64());
				} else if(tipo.equals("curriculo")) {
					contentType = user.getContentTypeCurriculo();
					fileBytes = new Base64().decodeBase64(user.getCurriculo());
				}
				
				if(!contentType.isEmpty()) {
					response.setHeader("Content-Disposition", "attachament;filename=arquivo." + contentType.split("\\/")[1]);
				} else {
					
				}
				
				
				//Colocar os bytes em um objeto de entrada para processar
				InputStream is = new ByteArrayInputStream(fileBytes);
				
				//init create response
				int read = 0;
				byte[] bytes = new byte[1024];
				OutputStream os = response.getOutputStream();
				
				//enquanto bytes tiver valor, continua a ler
				while((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				
				//Finalizar processo de carregamento
				os.flush();
				os.close();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String login = request.getParameter("login");
		String nome = request.getParameter("nome");
		String telefone = request.getParameter("telefone");
		String cep = request.getParameter("cep");
		String rua = request.getParameter("rua");
		String bairro = request.getParameter("bairro");
		String cidade = request.getParameter("cidade");
		String uf = request.getParameter("uf");
		String ibge = request.getParameter("ibge");
		String senha = request.getParameter("senha");
		String tipoUtilizador = request.getParameter("tipoUtilizador");
		String tipoAcesso = request.getParameter("tipoAcesso");
		
		int userExiste = 0;
		
		UsuarioBean user = new UsuarioBean();
		user.setUser(login);
		user.setNome(nome);
		user.setTelefone(telefone);
		user.setCep(cep);
		user.setRua(rua);
		user.setBairro(bairro);
		user.setCidade(cidade);
		user.setUf(uf);
		user.setIbge(ibge);
		user.setSenha(senha);
		user.setTipoAcesso(tipoUtilizador);
		
		/************* Begin File Upload *******************/
		
		try {
			
			if(ServletFileUpload.isMultipartContent(request)) {
				
				Part imagemFoto = request.getPart("foto");
				
				if(imagemFoto != null ) {
					byte[] imagemBytes = convertStreamToByte(imagemFoto.getInputStream());
					String fotoBase64 = Base64.encodeBase64String(imagemBytes); 
					String contentType = imagemFoto.getContentType();
					  
					  //System.out.println(fotoBase64 + "\n" + contentType);
					  if(!contentType.equals("application/octet-stream")) {
						  user.setFotoBase64(fotoBase64); 
						  user.setContentType(contentType);
						  
						  
						  /**
						   * inicio miniatura imagem
						   */
						  
						  //Transformar bytes imagem em um bufferedImage
						  byte[] imagemByteDecode = new Base64().decodeBase64(fotoBase64);
						  BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByteDecode));
						  //pegar o tipo de imagem
						  int imagemTipo = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB :  bufferedImage.getType();
						  //setar largura e altura(criar miniatura)
						  BufferedImage imagemRedemensionada = new BufferedImage(100, 100, imagemTipo);
						  Graphics2D g = imagemRedemensionada.createGraphics();
						  g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						//Finalizar processo
						  g.dispose(); 
						  
						  //Render imagem novamente
						  ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
						  ImageIO.write(imagemRedemensionada, "png", arrayOutputStream);
						  
						  
						  String imagemEmMiniaturaBase64 =  "data:image/png;base64," + DatatypeConverter.printBase64Binary(arrayOutputStream.toByteArray());
						  user.setMiniaturaBase64(imagemEmMiniaturaBase64);
					  } else {
						  user.setFotoBase64(request.getParameter("fotoHidden"));
						  user.setContentType(request.getParameter("contentTypeHidden"));
					  }
					  
				}
				
				//Processar pdf
				Part curriculoPdf = request.getPart("curriculo");
				
				if(curriculoPdf != null ) {
					String curriculo = Base64.encodeBase64String(convertStreamToByte(curriculoPdf.getInputStream())); 
					String contentTypecurriculo = curriculoPdf.getContentType();
					  
					  //System.out.println(curriculo + "\n" + contentTypecurriculo);
					if(!contentTypecurriculo.equals("application/octet-stream")) {
					  user.setCurriculo(curriculo);
					  user.setContentTypeCurriculo(contentTypecurriculo);
					} else {
						user.setCurriculo(request.getParameter("curriculoHidden"));	
						user.setContentTypeCurriculo(request.getParameter("contentTypeCurriculoHidden"));
					}
				}
				  
			} else {
				System.out.println("No Multi-Part.");
			}
			/************* End File Upload **********************/	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		
		
		if(id == null || id.isEmpty()) {
			/**
			 * Caso o usuario não existir avançar com o registo
			 */
			if(!userDao.userExiste(user.getUser())) {
				userDao.salvar(user);
				userExiste = 1;
			} else {
				request.setAttribute("user", user);
				userExiste = 2;
			}
		} else {
			user.setId(Long.valueOf(id));
			userDao.actualizar(user);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("usuarios", userDao.getUsers(tipoAcesso));
		request.setAttribute("userExiste", userExiste);
		request.setAttribute("tipoAcesso", tipoAcesso);
		view.forward(request, response);
	}
	
	private byte[] convertStreamToByte(InputStream imagem) throws IOException {
		
		ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
		int read = imagem.read();
		
		while(read != -1) {
			outPutStream.write(read);
			read = imagem.read();
		}
		
		return outPutStream.toByteArray();
	}

}
