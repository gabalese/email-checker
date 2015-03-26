package it.alese.emailchecker

import org.xbill.DNS.{MXRecord, Type, Lookup}

object MXResolver {
  def query(domain: String): Either[ServerUnreachableException, String] = {
    try {
      val records = new Lookup(domain, Type.MX).run()
      val mxRecord = records(0).asInstanceOf[MXRecord]
      val server = mxRecord.getTarget.canonicalize.toString(true)
      Right(server)
    } catch {
      case ex: NullPointerException => Left(ServerUnreachableException("Unknown host"))
    }
  }
}

case class ServerUnreachableException(message: String) extends RuntimeException(message)
