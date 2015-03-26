package it.alese.emailchecker

import org.xbill.DNS.{MXRecord, Type, Lookup}

object MXServer {
  def apply(domain: String): Either[ServerUnreachable, String] = {
    try {
      val records = new Lookup(domain, Type.MX).run()
      val mxRecord = records(0).asInstanceOf[MXRecord]
      val server = mxRecord.getTarget.canonicalize.toString(true)
      Right(server)
    } catch {
      case ex: NullPointerException => Left(ServerUnreachable("LOL"))
    }
  }
}

case class ServerUnreachable(message: String) extends RuntimeException(message)
