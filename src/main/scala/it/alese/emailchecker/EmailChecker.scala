package it.alese.emailchecker

case class EmailChecker(email: String) {

  private val domain: String = email.split("@")(1)
  private val host = MXServer(domain)

  val isValid: Boolean = {
    validate match {
      case RecipientOK => true
      case _ => false
    }
  }

  def validate: Response = {
    verificationProcess match {
      case UnableToConnect => ServerUnreacheable
      case r: Reply => r.response
    }
  }

  def verificationProcess: SMTPReply = {
    host match {
      case Right(server) => {
        val connection = TelnetSession(server).right.get
        if (!connection.test)
          return UnableToConnect
        connection.send("helo hi") // be polite
        connection.send(s"mail from: <$email>")
        val rcptReply = connection.send(s"rcpt to: <$email>")
        connection.close()
        Reply(rcptReply)
      }
      case Left(ex) => UnableToConnect
    }
  }
}

