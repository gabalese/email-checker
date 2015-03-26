package it.alese.emailchecker

sealed trait Response
case object DoesNotExist extends Response
case object BadSyntax extends Response
case object IPBlacklisted extends Response
case object RecipientOK extends Response
case object ServerUnreacheable extends Response
case object Denied extends Response
