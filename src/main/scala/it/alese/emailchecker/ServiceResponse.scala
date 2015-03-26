package it.alese.emailchecker

sealed trait ServiceResponse
case object DoesNotExist extends ServiceResponse
case object BadSyntax extends ServiceResponse
case object IPBlacklisted extends ServiceResponse
case object RecipientOK extends ServiceResponse
case object ServerUnreachable extends ServiceResponse
case object Denied extends ServiceResponse
