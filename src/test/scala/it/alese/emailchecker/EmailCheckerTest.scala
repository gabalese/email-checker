package it.alese.emailchecker

import org.scalatest.{Matchers, WordSpec}

class EmailCheckerTest extends WordSpec with Matchers {

  "An EmailChecker" should {
    "Recognise a valid email as valid" in {
      EmailChecker.check("gabriele@alese.it") shouldBe RecipientOK
    }

    "Recognise an invalid email as invalid" in {
      EmailChecker.check("non-existant@alese.it") shouldBe DoesNotExist
    }

    "Recognise an invalid email is domain is non existant" in {
      EmailChecker.check("gabriele@ilmiodominioinesistente.it") shouldBe ServerUnreachable
    }

    "Give information about servers that do not allow this check" in {
      EmailChecker.check("gabrielealese@edizionieo.it") shouldBe Denied
    }

  }

}
