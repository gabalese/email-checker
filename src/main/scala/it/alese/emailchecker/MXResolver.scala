package it.alese.emailchecker

import org.xbill.DNS.{MXRecord, Type, Lookup}
import scala.util.Try

object MXResolver {
  def query(domain: String): Try[String] = {
    Try {
      val records = new Lookup(domain, Type.MX).run()
      val mxRecord = records(0).asInstanceOf[MXRecord]
      val server = mxRecord.getTarget.canonicalize.toString(true)
      server
    }
  }
}
