package it.alese.emailchecker

trait SMTPReply

case class Reply(status: String) extends SMTPReply {
  val code = status.substring(0, 3)
  val subCode = status.substring(4,9)
  def response: ServiceResponse = subCode match {
    case "5.1.1" => DoesNotExist
    case "5.1.3" => BadSyntax
    case "5.7.0" => IPBlacklisted
    case "2.1.5" => RecipientOK
    case "5.5.4" | "5.7.1" => Denied
  }
}

case object RelayingDenied extends SMTPReply
case object UnableToConnect extends SMTPReply
