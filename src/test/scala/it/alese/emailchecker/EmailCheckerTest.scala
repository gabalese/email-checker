package it.alese.emailchecker

import org.scalatest.{Matchers, WordSpec}

class EmailCheckerTest extends WordSpec with Matchers {

  "An EmailChecker" should {
    "Recognise a valid email as valid" in {
      EmailChecker("gabriele@alese.it").check shouldBe RecipientOK
    }

    "Recognise an invalid email as invalid" in {
      EmailChecker("non-existant@alese.it").check shouldBe DoesNotExist
    }

    "Recognise an invalid email is domain is non existant" in {
      EmailChecker("gabriele@ilmiodominioinesistente.it").check shouldBe ServerUnreachable
    }

    "Give information about servers that do not allow this check" in {
      EmailChecker("gabrielealese@edizionieo.it").check shouldBe Denied
    }

  }

}
