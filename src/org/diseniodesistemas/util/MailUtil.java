package org.diseniodesistemas.util;

/*
 * Fichero: EnviarMail.java
 * Autor: Chuidiang
 * Fecha: 5/04/07 18:14
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.diseniodesistemas.modelo.Mail;
import org.diseniodesistemas.modelo.MailPredeterminado;

/**
 * Ejemplo de envio de correo simple con JavaMail
 *
 * @author Chuidiang
 *
 */
public class MailUtil {
		
	private Properties configConexion;
	
	private Properties mailsPreCargados;
	
	private static final String PATH_CONFIG_PROPERTIES = "configConexion.properties";

	private static final String PATH_MAILS_PRECARGADOS = "mailsPreCargados.properties";	
	
	
	
	public MailUtil() {
		super();
		
		configConexion = cargarConfigConexion();
		mailsPreCargados = cargarMailsPrecargados();
	}

	public Properties getConfigConexion() {
		return configConexion;
	}

	public void setConfigConexion(Properties configConexion) {
		this.configConexion = configConexion;
	}
	
	public Properties getMailsPreCargados() {
		return mailsPreCargados;
	}

	public void setMailsPreCargados(Properties mailsPreCargados) {
		this.mailsPreCargados = mailsPreCargados;
	}

	public Properties cargarConexion(String from, Properties props){
				
		Properties props1 = new Properties();
		// Propiedades de la conexión
		String servidorMails = props.getProperty("servidorMails"); 
		
		props1.setProperty("mail.smtp.host", props.getProperty("mail.smtp.host." + servidorMails));
		props1.setProperty("mail.smtp.starttls.enable", props.getProperty("mail.smtp.starttls.enable"));
		props1.setProperty("mail.smtp.port", props.getProperty("mail.smtp.port"));		
		props1.setProperty("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
		
		props1.setProperty("mail.smtp.user", from);

		return props1;
	}
		
	public void enviar(Mail mail){
		
		this.enviarMail(mail);		
	}

	public void enviar(MailPredeterminado mail){
		
		String to;
		String co;
		String bcc;		
		String subject;
		String text;
				
		/* Carga un mail pre-cargado */ 
		String mailPreCargado = "mail1";
		to = mailsPreCargados.getProperty(mailPreCargado + ".to");
		co = mailsPreCargados.getProperty(mailPreCargado + ".co");
		bcc = mailsPreCargados.getProperty(mailPreCargado + ".bcc");
		subject = mailsPreCargados.getProperty(mailPreCargado + ".subject");
		text = mailsPreCargados.getProperty(mailPreCargado + ".text");
		
		Mail m1 = new Mail(to, co, bcc, subject, text);
		this.enviarMail(m1);
	}
	
	/**
	 * 
	 * @param props
	 * @param pass
	 * @param mail
	 */
	public void enviarMail(
			Mail mail			
			){
			
		Properties prop = this.getConfigConexion();
		
		String from = prop.getProperty("mail.smtp.user");
		String pass = prop.getProperty("config.pass");
		
		Properties props = null;
		props = this.cargarConexion(from, prop);
		
		Session session = null;
		Transport t = null;
		try {

			// Preparamos la sesion
			session = Session.getDefaultInstance(props);

			// Construimos el mensaje
			MimeMessage message = new MimeMessage(session);			
			message.setFrom(new InternetAddress(from));
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getTo()));
			
			if(mail.getCo()!=null){
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(mail.getCo()));	
			}
			if(mail.geBcc()!=null){
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mail.geBcc()));	
			}			
			message.setSubject(mail.getSubject());
			message.setText(mail.getText());			
			
			String to = props.getProperty("mail.smtp.user");
			
			// Lo enviamos.
			t = session.getTransport("smtp");
			t.connect(to, pass);
			t.sendMessage(message, message.getAllRecipients());

			System.out.println("Mail enviado");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				t.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	
	/**
	 * 
	 * @param pathMailPredeterminado: Es el path donde se encuentran guardados los mails predeterminados.
	 * @param mailPredeterminado: Es el nombre del mail predeterminado.
	 */
	public Mail cargarMailPreCargado(String pathMailPredeterminado, String mailPredeterminado, String from) {

		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			entrada = new FileInputStream("c://configuracion.properties");
			// cargamos el archivo de propiedades
			propiedades.load(entrada);

			// obtenemos las propiedades			
			String to = propiedades.getProperty(mailPredeterminado + ".to");
			String co = propiedades.getProperty(mailPredeterminado + ".co");
			String bcc = propiedades.getProperty(mailPredeterminado + ".bcc");
			
			String subject = propiedades.getProperty(mailPredeterminado + ".subject");
			String text = propiedades.getProperty(mailPredeterminado + ".text");
						
			return new Mail(from, to, co, bcc, subject, text);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param pathProperties
	 */
	private Properties cargarConfigConexion(){

		Properties prop = new Properties();

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PATH_CONFIG_PROPERTIES);
		if(inputStream != null){
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return prop;
	}

	private Properties cargarMailsPrecargados(){
		Properties prop = new Properties();

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PATH_MAILS_PRECARGADOS);
		if(inputStream != null){
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return prop;
	}
	
}
