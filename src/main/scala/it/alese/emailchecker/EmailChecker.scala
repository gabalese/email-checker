package it.alese.emailchecker

import scala.util.Try

object EmailChecker {

  def check(email: String): ServiceResponse = {
    validate(email)
  }

  private def validate(email: String): ServiceResponse = {
    val domain: String = email.split("@")(1)
    val host: Try[String] = MXResolver.query(domain)
    verificationProcess(email, host) match {
      case UnableToConnect => ServerUnreachable
      case RelayingDenied => Denied
      case r: Reply => r.response
    }
  }

  private def verificationProcess(email: String, host: Try[String]): SMTPReply = {
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

