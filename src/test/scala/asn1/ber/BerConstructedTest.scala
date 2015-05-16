package asn1.ber

import org.scalatest.FlatSpec

class BerConstructedTest extends FlatSpec {

  "BerConstructed" should "decode a sequence correctly" in {

    assert(
      Ber.decode(Seq(16, 6,
        1, 1, 0,
        2, 1, 12)
      ) ==
        (BerConstructed(ClassAndPC(Ber.Universal), 16, Seq(
          BerBoolean(false),
          BerInteger(12)
        )), Seq())
    )

  }

 }
