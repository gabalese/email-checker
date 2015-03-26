package it.alese.emailchecker

import org.scalatest.{Matchers, WordSpec}

class EmailCheckerTest extends WordSpec with Matchers {

  "An EmailChecker" should {
    "Recognise a valid email as valid" in {
      EmailChecker("gabriele@alese.it").isValid shouldBe true
    }

    "Recognise an invalid email as invalid" in {
      EmailChecker("non-existant@alese.it").isValid shouldBe false
    }

    "Recognise an invalid email is domain is non existant" in {
      EmailChecker("gabriele@ilmiodominioinesistente.it").isValid shouldBe false
    }

    "Give information about server that do not allow this check" in {
      EmailChecker("gabrielealese@edizionieo.it").isValid shouldBe false
    }

  }

}
