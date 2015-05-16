package asn1.ber

import org.scalatest.FlatSpec

class BerTest extends FlatSpec {

  val unknownTag: Byte = 19

  "Ber" should "decode only the number of bytes specified" in {
    assert(Ber.decode(Seq(unknownTag, 0)) == (BerBytes(unknownTag, Seq[Byte]()), Seq()))
    assert(Ber.decode(Seq(unknownTag, 4, 1, 2, 3, 4)) == (BerBytes(unknownTag, Seq[Byte](1, 2, 3, 4)), Seq()))
    assert(Ber.decode(Seq(unknownTag, 4, 1, 2, 3, 4, 5, 6, 7)) ==
      (BerBytes(unknownTag, Seq[Byte](1, 2, 3, 4)), Seq(5, 6, 7)))
  }

  "Ber" should "fail to decode if not enough bytes are specified" in {
    intercept[IllegalArgumentException] {
      Ber.decode(Seq(unknownTag, 4, 1, 2, 3))
    }
  }

 }
