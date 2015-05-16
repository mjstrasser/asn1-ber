package asn1.ber

import org.scalatest.FlatSpec

class BerConstructedTest extends FlatSpec {

  "BerConstructed" should "decode a sequence correctly" in {

    assert(
      Ber.decode(Seq(48, 6,
        1, 1, 0,
        2, 1, 12)
      ) ==
        (BerConstructed(ClassAndPC(Ber.Constructed), 16, Seq(
          BerBoolean(value = false),
          BerInteger(12)
        )), Seq())
    )

    assert(
      Ber.decode(Seq(
        48, 24,
          2, 1, 1,
          96, 19,
            2, 1, 3,
            4, 9, 111, 61, 84, 101, 108, 115, 116, 114, 97,
            128, 3, 101, 115, 115).map(_.toByte)
      ) ==
        (BerConstructed(ClassAndPC(Ber.Constructed), 16, Seq(
          BerInteger(1),
          BerConstructed(ClassAndPC(Ber.AppConstructed), 0, Seq(
            BerInteger(3),
            BerOctetString("o=Telstra"),
            BerBytes(ClassAndPC(Ber.ContextSpecific), 0, List(101, 115, 115))
          ))
        )), Seq())
    )

  }

 }
