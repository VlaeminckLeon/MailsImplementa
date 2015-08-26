package org.diseniodesistemas;

import org.diseniodesistemas.modelo.Mail;
import org.diseniodesistemas.modelo.MailPredeterminado;
import org.diseniodesistemas.util.MailUtil;

public class Main {

	public static void main(String[] args) {
		
		MailUtil mailUtil = new org.diseniodesistemas.util.MailUtil();

		Mail m1 = new Mail("moreirajonatan1983@gmail.com", 
				null, null, "Prueba subject", "Mensaje con Java Mail");
		mailUtil.enviar(m1);

		MailPredeterminado mp1 = new MailPredeterminado("mail1");		
		mailUtil.enviar(mp1);

	}

}
