package it.alese.emailchecker

import scala.util.Try

case class EmailChecker(email: String) {

  private val domain: String = email.split("@")(1)
  private val host: Try[String] = MXResolver.query(domain)

  def check: ServiceResponse = {
    validate
  }

  private def validate: ServiceResponse = {
    verificationProcess match {
      case UnableToConnect => ServerUnreachable
      case RelayingDenied => Denied
      case r: Reply => r.response
    }
  }

  private def verificationProcess: SMTPReply = {
    host.map {
      server =>
        val session = TelnetSession(server)
        if (!session.allowsConnections) return RelayingDenied
        session.send("HELO HI")
        session.send(s"mail from: <$email>")
        val rcptReply = session.send(s"rcpt to: <$email>")
        session.close()
        Reply(rcptReply)
    } getOrElse UnableToConnect
  }
}

