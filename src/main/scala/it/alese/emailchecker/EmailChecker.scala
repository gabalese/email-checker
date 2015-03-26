package it.alese.emailchecker

case class EmailChecker(email: String) {

  private val domain: String = email.split("@")(1)
  private val host: Either[ServerUnreachableException, String] = MXResolver.query(domain)

  def check: ServiceResponse = {
    validate
  }

  private[this] def validate: ServiceResponse = {
    verificationProcess match {
      case UnableToConnect => ServerUnreachable
      case RelayingDenied => Denied
      case r: Reply => r.response
    }
  }

  private[this] def verificationProcess: SMTPReply = {
    host match {
      case Right(server) => {
        val session = TelnetSession(server)
        if(!session.allowsConnections) return RelayingDenied
        session.send("HELO HI")
        session.send(s"mail from: <$email>")
        val rcptReply = session.send(s"rcpt to: <$email>")
        session.close()
        Reply(rcptReply)
      }
      case Left(ex) => UnableToConnect
    }
  }
}

