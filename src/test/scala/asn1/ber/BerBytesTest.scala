package asn1.ber

import org.scalatest.FlatSpec

class BerBytesTest extends FlatSpec {

  val unknownTag: Byte = 19

  "BerBytes" should "decode bytes correctly" in {

    assert(Ber.decode(Seq(unknownTag, 0)) == (BerBytes(unknownTag, Seq[Byte]()), Seq()))

    assert(Ber.decode(Seq(unknownTag, 1, 15)) == (BerBytes(unknownTag, Seq[Byte](15)), Seq()))

    assert(Ber.decode(Seq(unknownTag, 12, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24)) ==
      (BerBytes(unknownTag, Seq[Byte](2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24)), Seq()))
  }

  it should "encode bytes correctly" in {
    assert(BerBytes(unknownTag, Seq()).toBytes == Seq(unknownTag, 0))
    assert(BerBytes(unknownTag, Seq(15)).toBytes == Seq(unknownTag, 1, 15))
    assert(BerBytes(unknownTag, Seq(1, 2, 3, 5, 8, 13)).toBytes == Seq(unknownTag, 6, 1, 2, 3, 5, 8, 13))
  }
}
