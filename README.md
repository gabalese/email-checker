# EmailChecker

A simple scala wrapper around Java's `Lookup` and `Socket`, enabling the client to force 
check the authenticity of an email address via a SMTP connection to the MX server for any domain.

## Example

```scala
import it.alese.emailchecker.EmailChecker

val result = EmailChecker.check("email@domain.com")  // result = RecipientOK
```

The list of possible query outcomes is in the `ServiceResponse.scala` enumeration.
